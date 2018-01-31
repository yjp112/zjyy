// ReportCommonFun.js  add by wl 20110609

function updateReportPrintCounts(prtRef,flag,userid,orgid) {
	
	if( prtRef == null || prtRef == "" ){
		return;
	}
	$.ajax({
		url: '/UtanJasperReport/ReportCommonServlet',
		dataType:'text',
		type:'POST',
		data:'OPERTYPE=UPDATE_PRT_TIMES&PRT_REF='+prtRef+'&userid='+userid+'&orgid='+orgid,
		async:false,
		error:function(){
//			alert('GET UPDATE_PRT_TIMES INFORMATION ERROR!');
			return ;
		},
		success: function(){
//			alert("Update Print Times Success!");
			if( $('#DO_PRINT_LIST_GRID',window.parent.document).get(0) != null && flag ){
				if( typeof(parent.refrashGrid) == 'function' ){
					parent.refrashGrid();
				}
			}
		}
	});
}

// FINWARE_V3.5_TFS_2013120016 重复打印授权 begin wj 2013-5-15
function beforeReportPrint(prtRef, rePrtAuth, reviewAuth)
{
	
	var flag = true;
	//FINWARE_V3.5_TFS_20140220【鞍山银行】预览时，传票应不允许打印begin yangcl 2014-2-20
	if( (prtRef == null || prtRef == "")){
        alert("预览不允许打印");
		return false;
	}

	$.ajax({
		url: '/UtanJasperReport/ReportCommonServlet',
		dataType:'text',
		type:'POST',
		data:'OPERTYPE=QUERY_PRT_TIMES&PRT_REF='+prtRef+'&REPRT_AUTH='+rePrtAuth+'&REVIEW_AUTH='+reviewAuth,
		async:false,
		success:function(data){
			if ("yes" == data)
				{
					alert("重复打印，请先授权");
					flag = false;
				}
			if ("forbid" == data)
				{
				    alert("交易未结束，不允许打印");
				    flag = false;
				}
		}
		
	});
	return flag;
}

// FINWARE_V3.5_TFS_2013120016 重复打印授权 end wj 2013-5-15


// for create window without document.UTFORM.target = ........;can set width and height
function createSingleWindow(win,width,height,title){
	var winT;
	if( !dhxWins.isWindow(win) ){
	
			winT = dhxWins.createWindow(win,20,30,600,300);// ???????,???????,???????????
			winT.progressOn();
			winT.setText("Do "+win);
			if( title != "" && title !=null ){
				winT.setText(title);
			}		
			$("body").append('<iframe name="'+win+'IFrame" id="'+win+'IFrame" style="padding:5px;background: #fafafa;width:' + width + 'px;height:'+height+'px;"></iframe>');
			
			var obj = document.getElementById(win+'IFrame');
			winT.attachObject(obj,true);
			winT.center();
			winT.button("park").hide();
			winT.button("minmax1").hide();
	
	}else{
		dhxWins.window(win).show();
		dhxWins.window(win).bringToTop();
	}
}

function cjkEncode(text){
	if(text == null) return ''; 
	var newText ='';
	for (var i = 0; i < text.length; i++) { 
		var code = text.charCodeAt(i); 
		if (code >= 128 || code == 91 || code == 93)
			newText += "[" + code.toString(16) + "]"; 
		else
			newText += text.charAt(i); 
	} 
	return newText; 
}

function getCurrDate(){
	var date = new Date();
	var dateStr = "";
	var y = date.getFullYear() + "";
	var m = Number(date.getMonth()) + 1 + "";
	if (m.length == 1) m = "0" + m;
	var d = date.getDate();
	//if (d.length == 1) d = "0" + d;
	var reg = /^\d$/;
	if(reg.test(d)){
		d = "0" + d;
	}
	dateStr = y + "-" + m + "-" + d;
	return dateStr;
}
