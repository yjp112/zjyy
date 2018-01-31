
(function($){
	GlobalVars={};
	/*此处的ajax为同步,把global-vars-**.properties里的配置信息装载到全局变量GlobalVars里.
	 * 除此之外新增属性webCtx*/
	$.ajax({
		   async:false,
		   type: "POST",
		   url: $.CONTEXT_PATH+"/base/loadGlobalVars",
		   data: "t="+new Date().getTime(),
		   success: function(responseText){
	            if (responseText && (responseText.status == 'success')) {
	                //$.message(responseText.message, $.message.TYPE_OK, 4, true, true);
	                GlobalVars=responseText.data;
	                //console.dir(GlobalVars)
	            } else if (responseText && (responseText.status == 'fail' || responseText.status == 'error')) {
	                $.message(responseText.message, $.message.TYPE_ERROR, 4, false, true);
	                if (errorCallback) {
	                    errorCallback(responseText, form);
	                }
	            } else {
	                $.message("发生异常，请重试！", $.message.TYPE_ERROR, 4, false, true);
	                if (errorCallback) {
	                    errorCallback(responseText, form);
	                }
	            }
		   }
		});
})(jQuery)
/**
 *
 * @param url
 * @param title
 * @param isLock
 * @param width
 * @param height
 * @param callback
 */
function newDialog(id, url, title, isLock, width, height, okCallback, closeCallBack, max) {
    if (dialog.list[id ? id : "dialog_id"]) {
        dialog.list[id ? id : "dialog_id"].show();
        return;
    }
    if (okCallback) {
        dialog({
            id: id ? id : "dialog_id",
            content: url,
            title: title ? title : "标题",
            lock: isLock,
            width: width ? width : 300,
            height: height ? height : 500,
            close: closeCallBack ? closeCallBack : null,
            ok: okCallback,
            min: false,
            max: max ? max : false,
            okVal: '确定'

        });
    } else {
        dialog({
            id: id ? id : "dialog_id",
            content: url,
            title: title ? title : "标题",
            lock: isLock,
            width: width ? width : 300,
            height: height ? height : 500,
            min: false,
            max: max ? max : false,
            close: closeCallBack ? closeCallBack : null

        });
    }

}
function newIframeDialog(id, url, title, width, height) {
    //穿越iframe
    var api = frameElement.api, W = api.opener;
    W.dialog({
        id: id ? id : "dialog_id",
        content: 'url:' + url,
        title: title ? title : "标题",
        parent: api,
        padding: 0,
        width: width ? width : 500,
        height: height ? height : 300,
        min: false,
        max: false
    });
}
function showFlowChart(processInstanceId, width, height) {
    width = width || 800;
    height = height || 600;
    newDialog("FLOW_CHART", "url:base/process/chart?processInstanceId=" + processInstanceId, "流程图", true, 800, 600);
    //window.open(contextPath+"/base/process/chart?processInstanceId="+processInstanceId);
}
function getContext(){
	return $.CONTEXT_PATH+'/';
}
/**
 *
 * @param id datagrid的ID
 * @param url 删除数据的URL地址，默认为del
 */
function singleDelete(obj, url,btValue) {
    if(!btValue||btValue==""){
        btValue ="删除"
    }
    var data = obj.getSelectedRows();
    if (data.length == 0) {
        alert("请选择要"+btValue+"的项");
        return;
    } else if (data.length > 1) {
        alert("要"+btValue+"的记录数多于1条");
        return;
    } else {
        dialog.confirm("确定要"+btValue+"吗？", function () {
            $.ajax({
                type: "post",
                cache: false,
                url: url ? url : "delete",
                data: "ids=" + data[0].id,
                success: function (response) {
                    if (response.status == "error") {
                        //$.message(response.message, $.message.TYPE_ERROR);
                        showErrorMsg(response.message);
                    } else if (response.status == "success") {
                        //$.message(response.data.message, $.message.TYPE_OK);
                        if (response.data.next) {
                            if (response.data.next == ".") {//刷新datagrid
                                obj.flexReload();
                            } else if (response.data.next == "x") {
                                return;
                            } else {//刷新maincontent  例子：response.data.next：  相对路径
                                loadMain(response.data.next);
                            }
                        }
                        if (response.data.message) {
                            //$.message(response.data.message, $.message.TYPE_OK);
                            showSuccessMsg(response.data.message);
                        }
                    }
                }, error: function (xhr, status, error) {
                    var response = $.parseJSON(xhr.responseText);
                    if (response.error) {
                        response = response.error;
                    }
                    //$.message(response.message, $.message.TYPE_ERROR);
                    showErrorMsg(response.message);
                }
            });
        }, function () {
        });
    }
}


function formatMessage(message, status) {
    if (status == 'ok') {
        return '<div class="ui-message-content"><p class="ui-tiptext ui-tiptext-success"><i class="iconfont icon-ok-sign"></i>成功</p><p class="ui-message-text">' + message + '</p></div>';
    } else {
        return '<div class="ui-message-content"><p class="ui-tiptext ui-tiptext-error"><i class="iconfont icon-remove-sign"></i>失败</p><p class="ui-message-text">' + message + '</p></div><div class="ui-message-btn"><button class="btn" type="button">确定</button></div>';
    }
}
function showSuccessMsg(msg) {
    $.message(formatMessage(msg, 'ok'), $.message.TYPE_OK, 4, true, true);
}

function showErrorMsg(msg) {
    $.message(formatMessage(msg, 'error'), $.message.TYPE_ERROR, 4, false, true);
}
function validateCallback(response, dataGridId) {
    if (response.status == "success") {
        if (response.data.message) {
            //$.message(response.data.message, $.message.TYPE_OK);
            showSuccessMsg(response.data.message);
        }
        if (response.data.next) {
            if (response.data.next == ".") {//刷新datagrid
                if ($("#" + dataGridId)) {//操作datagrid
                    $("#" + dataGridId).flexReload();
                }

            } else if (response.data.next == "x") {//关闭dialog并刷新datagrid
                if (parent) {//dialog中有iframe
                    if (parent.$("#" + dataGridId)) {
                        parent.$("#" + dataGridId).flexReload();
                    }
                } else if ($("#" + dataGridId)) {
                    $("#" + dataGridId).flexReload();
                }
                closeDialog();//关闭dialog
            } else if (response.data.next == 'r') {

            } else if (response.data.next == 'c') {
                closeDialog();//关闭dialog
            } else {//关闭dialog并刷新页面
                if (frameElement) {//dialog中有iframe
                    if (parent.$(".mainContent")) {
                        parent.$(".mainContent").load(response.data.next);
                    }
                } else if ($(".mainContent")) {
                    loadMain(response.data.next);
                }
                closeDialog();//关闭dialog
            }
        }
    }
}

function closeDialog() {
    destroyFileUpload("dialog");
    if (frameElement) {
        try {
            var api = frameElement.api, W = api.opener;
            W.dialog.focus.close();
        } catch (err) {
        }
    } else {
        dialog.focus && dialog.focus.close();
    }
}
function choosePerson(txtId, txtName, type, deptId, deptName) {
    if (deptId == null)deptId = "";
    if (deptName == null)deptName = "";
    dialog({
        id: "choose_person",
        title: '人员选择',
        content: "load:"+getContext()+"choose/person/page/" + (type ? type : 1) + "/" + txtId + "/" + txtName + "?deptId=" + deptId + "&deptName=" + deptName,
        lock: true,
        min: false,
        max: false,
        close: function () {
            inputChooseClear()
        },
        width: 950,
        height: 450
    });

}
//分项选择
function getSubCode() {
    dialog({
        id: "get_subCode",
        title: '分项选择',
        content: "load:"+getContext()+"basic/deviceMeter/getSubCodeTree",
        lock: true,
        min: false,
        max: false,
        close: function () {
            inputChooseClear()
        },
        width: 350,
        height: 450
    });

}
//设备选择
function getDevice(txtId,txtName,flag,electricName,locationName,useDepartMent,manageDepartMent){
	dialog({
        id: "choose_Device",
        title: '电表选择',
        content: "load:"+getContext()+"choose/device?txtId="+txtId+"&txtName="+txtName+"&flag="+flag+"&electricName="+electricName+"&locationName="+locationName+"&useDepartMent="+useDepartMent+"&manageDepartMent="+manageDepartMent,
        lock: true,
        min: false,
        max: false,
        close: function () {
            inputChooseClear()
        },
        width: 950,
        height: 450
    });
}
//水表设备选择
function getDeviceWater(txtId,txtName){
	dialog({
        id: "choose_Device",
        title: '水表选择',
        content: "load:"+getContext()+"choose/deviceWater?txtId="+txtId+"&txtName="+txtName,
        lock: true,
        min: false,
        max: false,
        close: function () {
            inputChooseClear()
        },
        width: 950,
        height: 450
    });
}
//电表设备选择
function getDeviceElectric(txtId,txtName){
	dialog({
        id: "choose_Device",
        title: '电表选择',
        content: "load:"+getContext()+"choose/deviceElectric?txtId="+txtId+"&txtName="+txtName,
        lock: true,
        min: false,
        max: false,
        close: function () {
            inputChooseClear()
        },
        width: 950,
        height: 450
    });
}

//能量表设备选择
function getDeviceEnergy(txtId,txtName){
	dialog({
        id: "choose_Device",
        title: '能量表选择',
        content: "load:"+getContext()+"choose/deviceEnergy?txtId="+txtId+"&txtName="+txtName,
        lock: true,
        min: false,
        max: false,
        close: function () {
            inputChooseClear()
        },
        width: 950,
        height: 450
    });
}
//气表设备选择
function getDeviceGas(txtId,txtName){
	dialog({
        id: "choose_Device",
        title: '气表选择',
        content: "load:"+getContext()+"choose/deviceGas?txtId="+txtId+"&txtName="+txtName,
        lock: true,
        min: false,
        max: false,
        close: function () {
            inputChooseClear()
        },
        width: 950,
        height: 450
    });
}
//选择委外合同
function chooseContract(txtId, txtName, contractStatus) {
    dialog({
        id: "choose_person",
        title: '委外合同选择',
        content: "load:"+getContext()+"choose/contract/" + txtId + "/" + txtName + "?contractStatus=" + contractStatus,
        lock: true,
        min: false,
        max: false,
        close: function () {
            inputChooseClear()
        },
        width: 950,
        height: 450
    });

}

//选设备
function chooseDevice(txtId, txtName, type, status) {
    if (status == null) {
        status = "";
    }
    dialog({
        id: "choose_device",
        title: '选择设备',
        content: "load:"+getContext()+"choose/devicePage?txtId=" + txtId + "&txtName=" + txtName + "&type=" + type + "&status=" + status,
        min: false,
        max: false,
        lock: true,
		close: function () {
            inputChooseClear()
        },
        width: 820,
        height: 495
    });
}
function chooseDevices(txtId, flag,rule) {
    dialog({
        id: "choose_device",
        title: '选择设备',
        content: "load:"+getContext()+"nhgl/choose/devices?txtId=" + txtId + "&flag=" + flag+ "&rule=" + rule ,
        min: false,
        max: false,
        lock: true,
		close: function () {
            inputChooseClear()
        },
        width: 1280,
        height: 500
    });
}

function chooseOtherCategoryDevices(txtId, flag,rule) {
	dialog({
		id: "choose_device",
		title: '选择设备',
		content: "load:"+getContext()+"nhgl/choose/otherCategoryDevices?txtId=" + txtId + "&flag=" + flag+ "&rule=" + rule ,
		min: false,
		max: false,
		lock: true,
		close: function () {
			inputChooseClear()
		},
		width: 880,
		height: 495
	});
}
//选择电表
function chooseElectricWatch(txtId, txtName, deviceCategory, type) {
    if (status == null) {
        status = "";
    }
    if (deviceCategory == null){
        deviceCategory = "";
    }
    dialog({
        id: "choose_device",
        title: '选择设备',
        content: "load:"+getContext()+"choose/device?txtId=" + txtId + "&txtName=" + txtName + "&type=" + type + "&status=0&categoryId=" + deviceCategory,
        min: false,
        max: false,
        lock: true,
		close: function () {
            inputChooseClear()
        },
        width: 820,
        height: 495
    });
}
//根据特种设备状态查询设备
function chooseDeviceBySpecStatus(txtId, txtName, type, specStatus) {
    dialog({
        id: "choose_device",
        title: '选择设备',
        content: "load:"+getContext()+"choose/device?txtId=" + txtId + "&txtName=" + txtName + "&type=" + type + "&status=0&specStatus=" + specStatus,
        min: false,
        max: false,
        lock: true,
		close: function () {
            inputChooseClear()
        },
        width: 820,
        height: 495
    });
}
//选区域
function chooseGeoarea(txtId, txtName) {
    dialog({
        id: 'selectDiloag',
        content: "load:"+getContext()+"choose/geoarea?txtId=" + txtId + "&txtName=" + txtName,
        title: '选择安装位置',
        min: false,
        max: false,
        lock: true,
        width: 300,
        height: 500,
        close: function () {
            inputChooseClear()
        }
    });
}
//能耗中选择地理区域
/*
 * txtId:选择框id
 * txtName:选择框name
 * check:用于点击关闭时的回调函数
 * dialogName:弹出框的姓名
 * id:需要在弹出框中屏蔽的树列
 * nhType:能耗类型(可选)
 */
function chooseNgArea(txtId, txtName,dialogName,check,id,nhType) {
	if(id==null)
		 id="";
	if(nhType==null){
		nhType="";
	}
    dialog({
        id: 'selectDiloag',
        content: "load:"+getContext()+"nhgl/choose/chooseArea?txtId=" + txtId + "&dialogId="+dialogName+"&txtName=" + txtName+"&id="+id+"&nhType="+nhType,
        title: '选择安装位置',
        min: false,
        max: false,
        lock: true,
        width: 300,
        height: 500,
        close: function () {
        	if(check!=null)
       			check();
            inputChooseClear()
        }
       
    });
}
//能耗中选部门
/*
 * txtId:选择框id
 * txtName:选择框name
 * check:用于点击关闭时的回调函数
 * dialogName:弹出框的姓名
 * id:需要在弹出框中屏蔽的树列
 * nhType:能耗类型(可选)
 */
function chooseDept(txtId, txtName,dialogName,check,id,nhType) {
	if(id==null)
		 id="";
	if(nhType==null){
		nhType="";
	}
    dialog({
        id: "choose_dept",
        title: '选择部门',
        content: "load:"+getContext()+"nhgl/choose/dept?txtId=" + txtId + "&dialogId="+dialogName+"&txtName=" + txtName+"&id="+id+"&nhType="+nhType,
        min: false,
        max: false,
        lock: true,
		close: function () {
			if(check!=null)
				check();
            inputChooseClear()
        },
        width: 300,
        height: 500
        
    });
}
//能耗中选择其他分类
function chooseOtherCategory(txtId, txtName,check) {
	dialog({
		id: 'selectDiloag',
		content: "load:"+getContext()+"nhgl/basic/otherCategory/lookup?txtId=" + txtId + "&txtName=" + txtName,
		title: '选择安装位置',
		min: false,
		max: false,
		lock: true,
		width: 300,
		height: 500,
		close: function () {
			if(check!=null)
				check();
			inputChooseClear()
		}
	
	});
}
//选设备类别
function chooseDeviceCategory(txtId, txtName, type) {
    if (type == 2) {
        dialog({
            id: "choose_deviceCategory",
            title: '选择设备类别',
            content: "load:"+getContext()+"choose/deviceCategory?txtId=" + txtId + "&txtName=" + txtName + "&type=" + type,
            lock: true,
            min: false,
            max: false,
            width: 300,
            height: 600,
            ok: function () {
                multiChoose();
            },
            okVal: "确定"
        });
    } else {
        dialog({
            id: "choose_deviceCategory",
            title: '选择设备类别',
            content: "load:"+getContext()+"choose/deviceCategory?txtId=" + txtId + "&txtName=" + txtName + "&type=" + type,
            lock: true,
            min: false,
            max: false,
            width: 300,
            height: 600,
            close: function () {
                inputChooseClear()
            }
        });
    }
}
function chooseSupplier(txtId, txtName) {
    dialog({
        id: 'chooseSupplier',
        title: '厂商选择',
        content: "load:"+getContext()+"base/supplier/lookup/" + txtId + "/" + txtName,
        width: 950,
        height: 400,
        min: false,
        max: false,
        lock: false,
        close: function () {
            inputChooseClear()
        }/*,
         /*ok : function(){
         return okClick()
         //return this.content.okClick()
         },
         okVal :'确定'*/
    });
}
//选分项
function chooseSubSystem(txtId, txtName, type, nhType,id) {
	if(id==null)
		id="";
    dialog({
        id:'selectDiloagSubSystem',
        content: "load:"+getContext()+"nhgl/choose/subSystem?txtId=" + txtId + "&txtName=" + txtName + "&type=" + type+"&nhType="+nhType+"&id="+id,
        title: '选择分项',
        lock: true,
        width: 300,
        height: 550
    });
}
//选分项
function chooseWaterSubSystem(txtId, txtName, type) {
    dialog({
        id:'selectDiloagWaterSubSystem',
        content: "load:"+getContext()+"choose/waterSubSystem?txtId=" + txtId + "&txtName=" + txtName + "&type=" + type,
        title: '选择分项',
        lock: true,
        width: 300,
        height: 550
    });
}
function chooseEnergySubSystem(txtId, txtName, type) {
	dialog({
		id:'selectDiloagEnergySubSystem',
		content: "load:"+getContext()+"choose/energySubSystem?txtId=" + txtId + "&txtName=" + txtName + "&type=" + type,
		title: '选择分项',
		lock: true,
		width: 300,
		height: 550
	});
}
function chooseGasSubSystem(txtId, txtName, type) {
	dialog({
		id:'selectDiloagGasSubSystem',
		content: "load:"+getContext()+"choose/gasSubSystem?txtId=" + txtId + "&txtName=" + txtName + "&type=" + type,
		title: '选择分项',
		lock: true,
		width: 300,
		height: 350
	});
}
function chooseAlarmType(txtId, txtName, type) {
    dialog({
        id:'selectDiloagAlarmType',
        content: "load:"+getContext()+"choose/alarmType?txtId=" + txtId + "&txtName=" + txtName + "&type=" + type,
        title: '选择设备类别',
        lock: true,
        width: 300,
        height: 550
    });
}
function chooseTask(txtId, txtName, type) {
    dialog({
        id:'selectDiloagTask',
        content: "load:"+getContext()+"choose/task?txtId=" + txtId + "&txtName=" + txtName + "&type=" + type,
        title: '选择任务',
        lock: true,
        width: 300,
        height: 550
    });
}
/*判断选择输入框是否能清空*/
function inputChooseClear() {
    $(".ui-input-choose").each(function () {
        var emptyFunc = $(this).attr("emptyFunc");
        if (emptyFunc) {
            emptyFunc = eval(emptyFunc);
            var clearId = $(this).attr('clearId');
            if (!clearId) {
                clearId = new Date().getTime();
                var _clear = $("<a id='" + clearId + "' class='ui-input-remove' href='javascript:void(0);' title='清空'>×</a>");
                $(this).after(_clear);
                _clear.click(function () {
                    emptyFunc();
                    $(this).hide();
                });
                $(this).attr('clearId', clearId);
            }

            if ($(this).attr("value") != "") {
                $('#' + clearId).show();
            } else {
                $('#' + clearId).hide();
            }
        }
    });

}
function formatMoney(num) {
    try {
        num = $.trim(num);
        if (num == '') {
            return '';
        }
        num = num * 1;
        return num.toFixed(2);
    } catch (ex) {
    }
    return '0.00';
}
function formatNumber2(num) {
    try {
        num = $.trim(num);
        if (num == '') {
            return '';
        }
        num = num * 1;
        return num.toFixed(2);
    } catch (ex) {
    }
    return '0.00';
}
function subtract(num1, num2) {
    try {
        num1 = parseFloat(num1);
        num2 = parseFloat(num2);
        return num1 - num2;
    } catch (err) {
    }

}

function greaterThanOrEqual(num1, num2) {
    if (subtract(num1, num2) >= 0) {
        return true;
    }
    return false;
}
function lessThan(num1, num2) {
    if (subtract(num1, num2) < 0) {
        return true;
    }
    return false;
}

$(document).ready(function () {
    $.ajaxSetup({
        // Disable caching of AJAX responses
        cache: false
    });
    $('.money').each(function (i, v) {
        $(v).change(function () {
            var value = $.trim($(this).val());
            $(this).val(formatMoney(value));
        });
        var value2 = $.trim($(v).val());
        $(v).val(formatMoney(value2));
    });
    $('.number2').each(function (i, v) {
        $(v).change(function () {
            var value = $.trim($(this).val());
            $(this).val(formatNumber2(value));
        });
        var value2 = $.trim($(v).val());
        $(v).val(formatNumber2(value2));
    });
    /*$(".money,.number2").keydown(function(event) {
     var keyCode = event.which;
     if (keyCode == 46 || (keyCode >= 48 && keyCode <=57))
     return true;
     else
     return false;
     }).focus(function() {
     this.style.imeMode='disabled';
     });*/

	/*禁用backspace键的后退功能，但是可以删除文本内容*/
	document.onkeydown = function(e){
	    var code;
	    if (!e) var e = window.event;
	    if (e.keyCode) code = e.keyCode;
	    else if (e.which) code = e.which;
	    if ((code == 8) &&                                                    
	         ((event.srcElement.type != "text" && 
	         event.srcElement.type != "textarea" && 
	         event.srcElement.type != "password")||(event.srcElement.readOnly == true))) {
	        event.keyCode = 0; 
	        event.returnValue = false; 
	    }
		return true;
	}
});
//----------
function setMainContentH() {
    var rootEl = document.compatMode == "CSS1Compat" ? document.documentElement : document.body;
    var rootHd = parseInt(rootEl.clientHeight);
    $(".mainContent").height(rootHd - 114);
    $(window).resize(function () {
        var rootEl = document.compatMode == "CSS1Compat" ? document.documentElement : document.body;
        var rootHd = parseInt(rootEl.clientHeight);
        $(".mainContent").height(rootHd - 114);
    });
}
//从login2登录系统
function setMainContentH_login2() {
    var rootEl = document.compatMode == "CSS1Compat" ? document.documentElement : document.body;
    var rootHd = parseInt(rootEl.clientHeight);
    $(".mainContent").height(rootHd - 114+32);
    $(window).resize(function () {
        var rootEl = document.compatMode == "CSS1Compat" ? document.documentElement : document.body;
        var rootHd = parseInt(rootEl.clientHeight);
        $(".mainContent").height(rootHd - 114+32);
    });
}
function setHospitalHomeH_login2() {
    var rootEl = document.compatMode == "CSS1Compat" ? document.documentElement : document.body;
    var rootHd = parseInt(rootEl.clientHeight);
    $(".hospitalHome").height(rootHd - 114+32);
    $(window).resize(function () {
        var rootEl = document.compatMode == "CSS1Compat" ? document.documentElement : document.body;
        var rootHd = parseInt(rootEl.clientHeight);
        $(".hospitalHome").height(rootHd - 114+32);
    });
}
function createXmlhttp(func,func2) {
    var xmlhttp;
    if (window.XMLHttpRequest) {
        //code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    } else {
        //code for IE6, IE5
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    
    xmlhttp.onreadystatechange=function(){
        if (xmlhttp.readyState==4 ){
        	if(xmlhttp.status==200){
        		var data = xmlhttp.responseText;
        		if(data!=null && data!=""){
        			data = eval("("+data+")");
        		}
        		func(data);
            }else{
            	
            }
        	if(func2!=null){func2();}
        }
    }
    return xmlhttp;
}
function sendXmlhttp(_xmlhttp,method,url,dataIn) {
	if(method.toUpperCase()=="GET"){
		_xmlhttp.open("GET",url,true);
		_xmlhttp.send();
	}else if(method.toUpperCase()=="POST"){
		_xmlhttp.open("POST",url,true);
		_xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		_xmlhttp.send(dataIn);
	}
}

//写点位
function writeBlock(hpids, bits, val, projectName) {
    var dataIn = {"hpids": hpids, "bits": bits, "val": val};
    $.ajax({
        'url': projectName + "/gis/writeBit?r=" + Math.random(),
        'data': dataIn,
        'traditional': true,
        'dataType': 'json',
        'type': 'get',
        'error': function (data) {
        },
        'success': function (data) {
        }
    });
}
function getMiddleCode(hpid) {
    var hpids = hpid.split("_");
    if (hpids != null && hpids.length > 3) {
        return hpids[2] + "_" + hpids[3];
    } else {
        alert("无效的设备编码");
        return "";
    }
}
//判断一个数组是否包含某个对象
function isInArray(strs, str) {
    var bol = false;
    if (strs != null && strs.length > 0) {
        for (var i = 0; i < strs.length; i++) {
            if (strs[i] == str) {
                bol = true;
                return bol;
            }
        }
    }
    return bol;
}

function loadMain(url) {
    $.ajaxSetup({
        cache: false
    });
    destroyFileUpload();
    if (url == null || url == "") {
        $('#centerMain').empty();
    } else {
        $('#centerMain').load(url);
    }
}

function destroyFileUpload(type) {
    try {
        var parent = type == "dialog" ? "div#" + dialog.focusId + " " : "";
        if ($(parent + "div.uploadify").length > 0) {
            $(parent + "div.uploadify").uploadify('destroy');
        }
    } catch (e) {
    }
}
function setAllReadonly(type) {
    var parent = type == "dialog" ? ".ui_dialog " : "";
    $(parent + '[type=button]').hide();
    $(parent + '[type=submit]').hide();
    if ($(parent + '.ui-box-close a').length == 0) {
        $(parent + '.ui-box-close').hide();
    }
    $(parent + ':input').attr('readonly', true);
    $(parent + '[type=radio]').attr('disabled', true);
    $(parent + '[type=checkbox]').attr('disabled', true);
    $(parent + 'select').attr('disabled', true);
    $(parent + ":input").attr('readonly', true).unbind("click").removeAttr("onclick");
}
function windowOpen(url,winName,pata) {
    var win="_blank";
    if(winName!=null && winName!="")win=winName;   	
	var theWindow = window.open(url,win, pata);
	if (theWindow.opener == null) theWindow.opener = window;
	theWindow.focus();
}
function autoLogin(projectName){
    $.ajax({
        url:projectName+"/isLogin",
        cache:false,
        type:"get",
        data:'',
        success:function(msg){
            if(msg=="false"){
                $.ajax({
                    url:projectName+"/platform/login",
                    cache:false,
                    type:"POST",
                    data:'username=admin&password=1111&rtype=json',
                    success:function(msg){
                        if(msg.success){
                        	//alert(msg.success);
                        }else{
                            //alert(msg.exception);
                        }
                    }
                });
            }
        }
    });
}
//--------
function isExist(list, obj) {
    if (list == null || list == "" || list.length < 1 || obj == null || obj == "") {
        return false;
    }
    for (var i = 0; i < list.length; i++) {
        if (list[i] == obj) {
            return true;
        }
    }
    return false;
}
function stringToArray(idstr) {
    if (idstr == null || idstr == "")return new Array();
    var ids = idstr.split(",");
    return ids;
}

/*动态校验*/
function dynamicValid(formId) {
    var methods = {
        not_empty: function (field, value) {
            return value !== null && $.trim(value).length > 0;
        },
        min_length: function (field, value, min_len, all_rules) {
            var regex1 = /[\u4E00-\u9FA5\uf900-\ufa2d]/ig;//中文字符
            var regex2 = /[^\u4E00-\u9FA5\uf900-\ufa2d]/g;//非中文字符
            var temp1 = value.replace(regex2, '');//纯中文字符串
            var temp2 = value.replace(regex1, '');//纯非中文文字符串

            var length = 2 * temp1.length + temp2.length, result = (length >= min_len);

            if (!all_rules['not_empty']) {
                result = result || length === 0;
            }
            return result;
        },
        max_length: function (field, value, max_len) {
            var regex1 = /[\u4E00-\u9FA5\uf900-\ufa2d]/ig;//中文字符
            var regex2 = /[^\u4E00-\u9FA5\uf900-\ufa2d]/g;//非中文字符
            var temp1 = value.replace(regex2, '');//纯中文字符串
            var temp2 = value.replace(regex1, '');//纯非中文文字符串
            return 2 * temp1.length + temp2.length <= max_len;
        },
        plusNumeric: function (field, value) {

            var regex = /^([\+]?[0-9]+(\.[0-9]+)?)?$/;
            return regex.test(value);
        }
    };
    var messages = {
        not_empty: '必填字段。',
        min_length: '请至少输入 :value 字符。',
        max_length: '请最多输入 :value 字符。',
        regex: '',
        email: '请填写正确的 email 地址。',
        url: '请填写正确的 url。',
        exact_length: '请输入 :value 字符。',
        equals: '请输入指定字符串。',
        ip: '请输入正确的 IP 地址。',
        credit_card: '请填写正确的卡号。',
        alpha: '必须输入字母。',
        alpha_numeric: '必须输入字母或数字。',
        alpha_dash: '必须输入字母、数字、下划线或连字符。',
        digit: '请输入数字。',
        numeric: '请输入十进制数字。',
        plusNumeric: '请输入正数',
        matches: '必须与前一个值相同。',
        money: '请输入金额',
        bigger: '必须比前一个值大',
        smaller: '必须比后一个值小',
        chinese: '请输入中文'
    };
    var allowed_rules = [];
    $.each(methods,
        function (k, v) {
            allowed_rules.push(k);
        });

    function format(field_name, rule, params) {
        var message;
        if (typeof messages[field_name] !== 'undefined' && typeof messages[field_name][rule] !== 'undefined') {
            message = messages[field_name][rule];
        } else {
            message = messages[rule];
        }

        if ($.type(params) !== 'undefined' && params !== null) {
            if ($.type(params) === 'boolean' || $.type(params) === 'string' || $.type(params) === 'number') {
                params = {
                    value: params
                };
            }
            $.each(params,
                function (k, v) {
                    message = message.replace(new RegExp(':' + k, 'ig'), v);
                });
        }
        return message;
    }


    var errors = {};
    $('#' + formId + ' :input[name][valid]').each(function () {
        var field = {
            name: $(this).attr('name'),
            value: $.trim($(this).val())
        };
        var validAttr = $(this).attr('valid');
        var rules = eval(validAttr);//['plusNumeric',{max_length: 20}]
        normalized_rules = {};
        $.each(rules,
            function (rule_idx, rule_value) {
                if ($.type(rule_value) === 'string') {
                    if ($.inArray(rule_value, allowed_rules) !== -1) {
                        normalized_rules[rule_value] = null;
                    }
                } else {
                    $.each(rule_value,
                        function (k, v) {
                            if ($.inArray(k, allowed_rules) !== -1) {
                                normalized_rules[k] = v;
                                return false;
                            }
                        });
                }
            });

        //console.dir(normalized_rules)
        $.each(normalized_rules,
            function (fn_name, fn_args) {
                if (methods[fn_name].call(self, field, field.value, fn_args, normalized_rules) !== true) {
                    errors[field.name] = format.call(self, field.name, fn_name, fn_args);
                }
            });

    });
    return errors;
}
function loadIframe(url) {
    if ($("#centerMain iframe[name=honeycomb]").length == 0) {
        loadMain();
		setMainContentH();
        $("#centerMain").append('<iframe name="honeycomb" src="' + url + '" frameborder="0" height="100%" width="100%" allowtransparency="true"></iframe>');
        
    } else {
        $("#centerMain iframe").attr("src", url);
    }
}   
function showProgress(name,width,time){
	if(!width)width=75;
	if(!time)time=1500;
	var nums= new Array();
	$("span[name="+name+"]").each(function(){
		nums.push(parseInt($(this).attr("num")));
	});
	var max = Math.max.apply(this, nums);
	var w = 1;
	$("span[name="+name+"]").each(function(){
		if(max > 0)
			w = width*parseInt($(this).attr("num"))/max;
		$(this).animate({'width':w},time);
	});
}

/* 
将数值四舍五入后格式化. 
@param num 数值(Number或者string) 
@param cent 要保留的小数位(Number) 
@param isThousand 是否需要千分位 0:不需要,1:需要(数值类型); 
@return 格式的字符串,如'1,234,567.45' 
@type String 
*/ 
function formatNumber(num, cent, isThousand) {
	num = num.toString().replace(/\$|\,/g, '');
	if (isNaN(num))//检查传入数值为数值类型. 
		num = "0";
	if (isNaN(cent))//确保传入小数位为数值型数值. 
		cent = 0;
	cent = parseInt(cent);
	cent = Math.abs(cent);//求出小数位数,确保为正整数. 
	if (isNaN(isThousand))//确保传入是否需要千分位为数值类型. 
		isThousand = 0;
	isThousand = parseInt(isThousand);
	if (isThousand < 0)
		isThousand = 0;
	if (isThousand >= 1) //确保传入的数值只为0或1 
		isThousand = 1;
	sign = (num == (num = Math.abs(num)));//获取符号(正/负数) 
	//Math.floor:返回小于等于其数值参数的最大整数 
	num = Math.floor(num * Math.pow(10, cent) + 0.50000000001);//把指定的小数位先转换成整数.多余的小数位四舍五入. 
	cents = num % Math.pow(10, cent); //求出小数位数值. 
	num = Math.floor(num / Math.pow(10, cent)).toString();//求出整数位数值. 
	cents = cents.toString();//把小数位转换成字符串,以便求小数位长度. 
	while (cents.length < cent) {//补足小数位到指定的位数. 
		cents = "0" + cents;
	}
	if (isThousand == 0) //不需要千分位符. 
		return (((sign) ? '' : '-') + num +((cent==0)?'': ('.' + cents)));
	//对整数部分进行千分位格式化. 
	for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
		num = num.substring(0, num.length - (4 * i + 3)) + ','
				+ num.substring(num.length - (4 * i + 3));
	return (((sign) ? '' : '-') + num+((cent==0)?'': ('.' + cents)));
}
//千分位以","隔开，并四舍五入保留2为小数
function fNum2(num) {
	return formatNumber(num, 2, 1);
}
//千分位以","隔开，并四舍五入取整
function fInt(num) {
	return formatNumber(num, 0, 1);
}
//页面里数字的格式化
function fNumPage(){
	$('.fNum2').each(function(){
		var num=$.trim($(this).text());
		if(num!=''){
			$(this).text(fNum2(num));
		}
	});
	$('.fInt').each(function(){
		var num=$.trim($(this).text());
		if(num!=''){
			$(this).text(fInt(num));
		}
	});
}
(function($){
	fNumPage();
})(jQuery)