// Initialize your app
var myApp = new Framework7({
    modalTitle:'中控集成大厦',
    modalButtonOk:'确认',
    modalButtonCancel:'离开',
    animatePages:true,
    swipeout:false,
    sortable:false,
    swipeBackPage:false,
    swipeBackPageAnimateShadow:false,
    swipeBackPageAnimateOpacity:false
});

// Export selectors engine
var $$ = Dom7;

// Add main View
var mainView = myApp.addView('#view', {
});
//var view2 = myApp.addView('#view-2');
//var view3 = myApp.addView('#view-3');
//var view4 = myApp.addView('#view-4');

// Now we need to run the code that will be executed only for About page.
// For this case we need to add event listener for "pageInit" event
initBpm();
//toolClick('tool-1');

var task_total,task_lastIndex,history_tab,history_total,history_lastIndex,bpm_message,isTaskInfinite,isTaskLoading,isHistoryInfinite,isHistoryLoading;
var device_total,device_lastIndex,isDeviceLoading,isDeviceInfinite;
var spare_total,spare_lastIndex,isSpareLoading,isSpareInfinite;
var bpmAlert;
var historySearchbar,taskSearchbar;
var lastTaskSearch,lastHistorySearch,lastDeviceSearch,lastSpareSearch;
var deviceSearchbar,spareSearchbar;
$$(document).on('pageInit', function (e) {
    // Do something here when page loaded and initialized

    var page = e.detail.page;

    if (page.name == 'todo') {
        initBpm();
    }
    else if(page.name == 'bpm_history'){
        var status=$$("#status").val();
        touchHistory(status);
        initHistory(status);
        initHandle();
    }
    else if(page.name == 'bpm_task'){
        var status=$$("#status").val();
        touchStart(status);
        initTask(status);
        initHandle(status);
    }
    else if(page.name== 'device')
    {
        initDevice();
    }
    else if(page.name=='spare')
    {
        initSpare();
    }
});

/** 判断是否隐藏toolbar **/
$$(document).on('pageBeforeAnimation', function (e) {
    // Do something here when page loaded and initialized
    var page = e.detail.page;

    if(page.name == 'todo'){
        //显示工具栏
        showToolBar();
    }
    else {
        //隐藏工具栏
        //myApp.hideToolbar('.toolbar');
        hiddenToolBar();
        //$$(".view").css('padding-bottom',0);
    }
});

/**===========初始化方法开始============**/

/** 初始化流程详细页面高度 **/
function touchStart(status)
{
    //accordion
    $$("#accordion-ul .handle").on('touchstart',function(){
        var curLi = $$(this).parent('li');
        var curIcon = $$(this).find('i');

        if (curLi.hasClass("current")) {
            curLi.removeClass("current");
        }else {
            $$("#accordion-ul li").removeClass("current");
            curLi.addClass("current");
        }
    });

    //设置panel高度
    var BodyH = document.body.clientHeight;
    var accordionLi = parseInt(status)+2;
    var panelH = BodyH - 62 - accordionLi*46;

    $$('.panel').css('height', panelH+'px');
    $$("#li-"+status).addClass("current");
}

/** 初始化已办流程详细页面高度 **/
function touchHistory(status)
{
    //accordion
    $$("#accordion-ul .handle").on('touchstart',function(){
        var curLi = $$(this).parent('li');
        var curIcon = $$(this).find('i');

        if (curLi.hasClass("current")) {
            curLi.removeClass("current");
        }else {
            $$("#accordion-ul li").removeClass("current");
            curLi.addClass("current");
        }
    });

    //设置panel高度
    var BodyH = document.body.clientHeight;
    var accordionLi = parseInt(status)+1;
    var panelH = BodyH - 62 - accordionLi*46;
    $$('.panel').css('height', panelH+'px');
    $$("#li-0").addClass("current");
}

/** 初始化待办流程页面 **/
function initBpm()
{
    history_tab=true;
    isTaskInfinite=false;
    isTaskLoading=false;
    isHistoryInfinite=false;
    isHistoryLoading=false;
    lastTaskSearch=undefined;
    lastHistorySearch=undefined;
    setTodoHeight();
    fillTaskData(false);

    taskSearchbar=myApp.searchbar('#task_form',{
        customSearch:true
    });

    $$('#task_form').on('search',function(e)
    {
        var taskSearch=$$("#taskSearch").val();
        if(taskSearchbar.active&&(taskSearch==undefined||taskSearch.trim()==""))
        {
            if(!$$(".searchbar-overlay").hasClass("searchbar-overlay-active")){
                $$(".searchbar-overlay").addClass("searchbar-overlay-active");
            }
        }
        else
        {
            if($$(".searchbar-overlay").hasClass("searchbar-overlay-active")){
                $$(".searchbar-overlay").removeClass("searchbar-overlay-active");
            }
        }
        fillTaskData(true);
    });

    historySearchbar=myApp.searchbar('#history_form',{
        customSearch:true
    });

    $$('#history_form').on('search',function(e)
    {
        var historySearch=$$("#historySearch").val();
        if(historySearchbar.active&&(historySearch==undefined||historySearch.trim()==""))
        {
            if(!$$(".searchbar-overlay").hasClass("searchbar-overlay-active")){
                $$(".searchbar-overlay").addClass("searchbar-overlay-active");
            }
        }
        else
        {
            if($$(".searchbar-overlay").hasClass("searchbar-overlay-active")){
                $$(".searchbar-overlay").removeClass("searchbar-overlay-active");
            }
        }
        fillHistoryData(true);
    });
    if(bpm_message!=undefined&&bpm_message!=null&&bpm_message!="")
    {
        alertModal(bpm_message);
        bpm_message="";
    }
    $$("#taskContent").on('refresh',function(e)
    {
        $$("#taskContent").removeClass("content-refresh");
        setTimeout(function (e) {
            //if (taskSearchbar.active){
            //    taskSearchbar.disable();
            //}
            //else
            //{
            //    fillTaskData();
            //}
            fillTaskData(false);
            myApp.pullToRefreshDone();
            $$("#taskContent").addClass("content-refresh");
        },2000);
    });
    $$("#historyContent").on('refresh',function(e)
    {
        $$("#historyContent").removeClass("content-refresh");
        setTimeout(function (e) {
            //if (historySearchbar.active){
            //    historySearchbar.disable();
            //}
            //else
            //{
            //    fillHistoryData();
            //}
            fillHistoryData(false);
            myApp.pullToRefreshDone();
            $$("#historyContent").addClass("content-refresh");
        },2000);
    });
}

/** 流程TAB点击事件 **/
function bpmClick(tab){
    if(tab=="tab1")
    {
        if (historySearchbar.active){
            setTimeout(function (e) {
                historySearchbar.active=false;
                historySearchbar.disable();
            },400)
        }
    }
    else if(tab=="tab2")
    {
        if (taskSearchbar.active){
            setTimeout(function (e) {
                taskSearchbar.active=false;
                taskSearchbar.disable();
            },400)
        }
        if(history_tab)
        {
            fillHistoryData(false);
            history_tab=false;
        }
    }
}

/** 待办流程详情组件初始化 **/
function initTask(status) {
    if (status == 1)
    {
        var personId=$$('#groupEmpId').val();
        if(personId!=null&&personId!="")
        {
            searchDutyGroupPersons(personId,"");
        }
        existRepairCode();
    }
    else if(status==2)
    {
        searchRepairMode("");
        searchActualFinishTime();
        //searchAllDevice();
        $$("#worker").on("change", "input[name$='.money']", function () {
            workerMoney();
        });
        $$("#spare").on("change", "input[name$='.money']", function () {
            spareMoney();
        });
    }
    else if(status==3)
    {
        workerMoney();
        spareMoney();
    }
}


/** 已办流程详情组件初始化 **/
function initHistory(status) {
    if(status>=3)
    {
        workerMoney();
        spareMoney();
    }
    if (status == 4)
    {
        var grade1=$$('#grade1').val();
        var grade2=$$('#grade2').val();
        var grade3=$$('#grade3').val();
        if(grade1!=undefined&&grade1!=null&&grade1!="")
        {
            var gradeLength=parseInt(grade1);
            for(var i=1;i<=gradeLength;i++)
            {
                $$("#grade1-li"+i).addClass("on");
            }
        }
        if(grade2!=undefined&&grade2!=null&&grade2!="")
        {
            var gradeLength=parseInt(grade2);
            for(var i=1;i<=gradeLength;i++)
            {
                $$("#grade2-li"+i).addClass("on");
            }
        }
        if(grade3!=undefined&&grade3!=null&&grade3!="")
        {
            var gradeLength=parseInt(grade3);
            for(var i=1;i<=gradeLength;i++)
            {
                $$("#grade3-li"+i).addClass("on");
            }
        }
    }
}

/** 初始化设备搜索页面 **/
function initDevice(){
    lastDeviceSearch=undefined;
    setDeviceHeight();
    fillDeviceData(false);
    deviceSearchbar=myApp.searchbar('#device_form',{
        customSearch:true
    });

    $$('#device_form').on('click',function(e)
    {
        if($$(".modal-in").hasClass("modal-in")){
            $$(".active").removeClass("active");
            myApp.closeModal(".modal-in");
        }
    });

    $$('#device_form').on('search',function(e)
    {
        var deviceSearch=$$("#deviceSearch").val();
        if(deviceSearchbar.active&&(deviceSearch==undefined||deviceSearch.trim()==""))
        {
            if(!$$(".searchbar-overlay").hasClass("searchbar-overlay-active")){
                $$(".searchbar-overlay").addClass("searchbar-overlay-active");
            }
        }
        else
        {
            if($$(".searchbar-overlay").hasClass("searchbar-overlay-active")){
                $$(".searchbar-overlay").removeClass("searchbar-overlay-active");
            }
        }
        fillDeviceData(true);
    });
}

/** 初始化设备配件页面 **/
function initSpare(){
    lastSpareSearch=undefined;
    setSpareHeight();
    fillSpareData(false);
    spareSearchbar=myApp.searchbar('#spare_form',{
        customSearch:true
    });

    $$('#spare_form').on('search',function(e)
    {
        var spareSearch=$$("#spareSearch").val();
        if(spareSearchbar.active&&(spareSearch==undefined||spareSearch.trim()==""))
        {
            if(!$$(".searchbar-overlay").hasClass("searchbar-overlay-active")){
                $$(".searchbar-overlay").addClass("searchbar-overlay-active");
            }
        }
        else
        {
            if($$(".searchbar-overlay").hasClass("searchbar-overlay-active")){
                $$(".searchbar-overlay").removeClass("searchbar-overlay-active");
            }
        }
        fillSpareData(true);
    });
}

/**===========初始化方法结束============**/

/**===========待办流程开始============**/
/** 待办流程高度 **/
function setTodoHeight()
{
    var bodyH= document.body.clientHeight-49;
    var tabHeight=bodyH-44-38;
    var tabContentHeight=tabHeight-44;
    $$('.tabs-content').css('height', tabHeight+'px');
    $$('.tabs-content .content-block').css('height', tabContentHeight+'px');
    $$('.searchbar-overlay').css('top', '44px');
}

/** 待办流程第一次取值 **/
function fillTaskData(isSearch) {
    if(!isSearch||lastTaskSearch!=$$("#taskSearch").val())
    {
        if(isSearch)
        {
            lastTaskSearch=$$("#taskSearch").val();
        }
        $$('#task_total').val("");
        $$('#bpmTaskId').val(0);
        $$('#task_div > ul').html("");
        $$.ajax({
            type: "post",
            url: "/jcds/mobile/todo/list_task",
            dataType: "json",
            data:myApp.formToJSON('#task_form'),
            success: function(data,textStatus) {
                var length=data.length;
                if(length!=0)
                {
                    var innerHtml = '';
                    for (var i = 0; i < length; i++) {
                        var description=data[i].description;
                        if(description!=undefined&&(!description.startsWith("诉求")))
                        {
                            description=description.substring(2,description.length);
                        }

                        innerHtml += '<li>'+
                            '<a href="" onclick="to_task(\''+data[i].id+'\')" class="item-link item-content">'+
                            '<i class="icon icon-c-message '+data[i].typeColor+'">'+data[i].typeName+'</i>'+
                            '<div class="item-inner">'+
                            '<div class="item-title-row">'+
                            '<div class="item-title">'+
                            '<span>'+data[i].name+'</span>'+
                            '</div>'+
                            '<div class="item-after">'+description+'</div>'+
                            '</div>'+
                            '<div class="item-text">'+data[i].showTime+'</div>'+
                            '</div>'+
                            '</a>'+
                            '</li>';
                    }
                    $$('#task_div > ul').append(innerHtml);
                    $$('#bpmTaskId').val(data[length-1].id);
                    $$('#task_total').val(data[length-1].taskTotal);
                    isTaskLoading=false;
                    if(data[length-1].taskTotal<=20)
                    {
                        myApp.detachInfiniteScroll($$('#taskContent'));
                        $$('#taskPreloader').hide();
                    }
                    else
                    {
                        isTaskInfinite=true;
                        initTaskPreload();
                    }
                }
                else
                {
                    myApp.detachInfiniteScroll($$('#taskContent'));
                    $$('#taskPreloader').hide();
                }
            }
        });
    }
};

/** 判断是否出现加载更多按钮 **/
function initTaskPreload()
{
    task_lastIndex = $$('#task_div > ul > li').length;
    task_total=$$('#task_total').val();

    if(isTaskInfinite)
    {
        myApp.attachInfiniteScroll($$('#taskContent'));
        $$('#taskPreloader').show();
        isTaskInfinite=false
    }

    $$('#taskContent').on('infinite',function(){
        if(isTaskLoading){
            return;
        }
        isTaskLoading=true;
        setTimeout(function () {
            if(task_lastIndex>=task_total)
            {
                myApp.detachInfiniteScroll($$('#taskContent'));
                $$('#taskPreloader').hide();
            }
            else
            {
                $$.ajax({
                    type: "post",
                    url: "/jcds/mobile/todo/list_task",
                    dataType: "json",
                    data:myApp.formToJSON('#task_form'),
                    success: function (data, textStatus) {
                        //生成新条目的HTML
                        var length = data.length;
                        var innerHtml = '';
                        for (var i = 0; i < length; i++) {
                            var description=data[i].description;
                            if(description!=undefined&&(!description.startsWith("诉求")))
                            {
                                description=description.substring(2,description.length);
                            }

                            innerHtml += '<li>'+
                                '<a href="" onclick="to_task(\''+data[i].id+'\')" class="item-link item-content">'+
                                '<i class="icon icon-c-message '+data[i].typeColor+'">'+data[i].typeName+'</i>'+
                                '<div class="item-inner">'+
                                '<div class="item-title-row">'+
                                '<div class="item-title">'+
                                '<span>'+data[i].name+'</span>'+
                                '</div>'+
                                '<div class="item-after">'+description+'</div>'+
                                '</div>'+
                                '<div class="item-text">'+data[i].showTime+'</div>'+
                                '</div>'+
                                '</a>'+
                                '</li>';
                        }
                        $$('#bpmTaskId').val(data[length - 1].id);
                        // 添加新条目
                        $$('#task_div > ul').append(innerHtml);
                        // 更新最后加载的序号
                        task_lastIndex =$$('#task_div > ul > li').length;
                        isTaskLoading=false;
                    }

                });
            }
        },1000)
    })
}

/**===========待办流程结束============**/

/**===========已办流程开始============**/

/** 已办流程第一次取值 **/
function fillHistoryData(isSearch) {
    if(!isSearch||lastHistorySearch!=$$("#historySearch").val())
    {
        if(isSearch)
        {
            lastHistorySearch=$$("#historySearch").val();
        }
        $$('#history_total').val("");
        $$('#bpmHistoryId').val(0);
        $$('#history_div > ul').html("");
        $$.ajax({
            type: "post",
            url: "/jcds/mobile/todo/list_history",
            dataType: "json",
            data:myApp.formToJSON('#history_form'),
            success: function(data,textStatus) {
                var length=data.length;
                if(length!=0)
                {
                    var innerHtml = '';
                    for (var i = 0; i < length; i++) {
                        var description=data[i].description;
                        if(description!=undefined&&(!description.startsWith("诉求")))
                        {
                            description=description.substring(2,description.length);
                        }
                        innerHtml += '<li>'+
                            '<a href="" onclick="to_history(\''+data[i].formUrl+'\')" class="item-link item-content">'+
                            '<i class="icon icon-c-message '+data[i].typeColor+'">'+data[i].typeName+'</i>'+
                            '<div class="item-inner">'+
                            '<div class="item-title-row">'+
                            '<div class="item-title">'+
                            '<span>'+data[i].activityName+'</span>'+
                            '</div>'+
                            '<div class="item-after">'+description+'</div>'+
                            '</div>'+
                            '<div class="item-text">'+data[i].showTime+'</div>'+
                            '</div>'+
                            '</a>'+
                            '</li>';
                    }
                    $$('#history_div > ul').append(innerHtml);
                    $$('#bpmHistoryId').val(data[length-1].id);
                    $$('#history_total').val(data[length-1].historyTotal);
                    isHistoryLoading=false;
                    if(data[length-1].historyTotal<=20)
                    {
                        myApp.detachInfiniteScroll($$('#historyContent'));
                        $$('#historyPreloader').hide();
                    }
                    else
                    {
                        isHistoryInfinite=true;
                        initHistoryPreload();
                    }
                }
                else {
                    myApp.detachInfiniteScroll($$('#historyContent'));
                    $$('#historyPreloader').hide();
                }
            }
        });
    }
};

/** 已办判断是否出现加载更多按钮 **/
function initHistoryPreload()
{
    history_lastIndex = $$('#history_div > ul > li').length;
    history_total=$$('#history_total').val();

    if(isHistoryInfinite)
    {
        myApp.attachInfiniteScroll($$('#historyContent'));
        $$('#historyPreloader').show();
        isHistoryInfinite=false;
    }

    $$('#historyContent').on('infinite',function(){
        if(isHistoryLoading){
            return;
        }
        isHistoryLoading=true;
        setTimeout(function () {
            if(history_lastIndex>=history_total)
            {
                myApp.detachInfiniteScroll($$('#historyContent'));
                $$('#historyPreloader').hide();
            }
            else
            {
                $$.ajax({
                    type: "post",
                    url: "/jcds/mobile/todo/list_history",
                    dataType: "json",
                    data:myApp.formToJSON('#history_form'),
                    success: function (data, textStatus) {
                        //生成新条目的HTML
                        var length = data.length;
                        var innerHtml = '';
                        for (var i = 0; i < length; i++) {
                            var description=data[i].description;
                            if(description!=undefined&&(!description.startsWith("诉求")))
                            {
                                description=description.substring(2,description.length);
                            }
                            innerHtml += '<li>'+
                                '<a href="" onclick="to_history(\''+data[i].formUrl+'\')" class="item-link item-content">'+
                                '<i class="icon icon-c-message '+data[i].typeColor+'">'+data[i].typeName+'</i>'+
                                '<div class="item-inner">'+
                                '<div class="item-title-row">'+
                                '<div class="item-title">'+
                                '<span>'+data[i].activityName+'</span>'+
                                '</div>'+
                                '<div class="item-after">'+description+'</div>'+
                                '</div>'+
                                '<div class="item-text">'+data[i].showTime+'</div>'+
                                '</div>'+
                                '</a>'+
                                '</li>';
                        }
                        $$('#bpmHistoryId').val(data[length - 1].id);
                        // 添加新条目
                        $$('#history_div > ul').append(innerHtml);
                        // 更新最后加载的序号
                        history_lastIndex =$$('#history_div > ul > li').length;

                        isHistoryLoading=false;
                    }

                });
            }
        },1000)
    })
}
/**===========已办流程结束============**/

/**===========流程详细内容开始============**/
/** 流程详情最下面流转信息 **/
function initHandle(status){
    var processInstanceId=$$('#_process_instance_id').val();
    $$.ajax({
        type: "get",
        url: "/jcds/mobile/bpm/approval/fetch?condition.processInstanceId="+processInstanceId,
        dataType: "json",
        success: function (data, textStatus) {
            var innerHtml = '';
            var length=data.length;
            if(length>0)
            {
                var j=1;
                for(var i=length-1;i>=0;i--)
                {
                    innerHtml='';
                    var item=data[i];
                    var hand = item.handleComment||'';
                    if(hand.length>10){
                        hand = hand.substring(0,10)+"...";
                    }
                    var activityName=item.activityName;
                    var actLength=activityName.length;
                    var showTime=item.showTime;
                    if(actLength>2)
                    {
                        activityName=activityName.substring(actLength-2,actLength);
                    }

                    $$("#wipe-"+j).removeClass("status-undo");
                    if(showTime!=undefined&&showTime!=null&&showTime!="")
                    {
                        $$("#wipe-"+j).addClass("status-completed");
                        innerHtml += '<div class="left-status"><i class="icon"></i><span class="line"></span><span class="arrow"></span></div>'+
                            '<div class="right-content"><div class="title-img"><i class="icon icon-'+j+'">'+activityName+ '</i></div>'+
                            '<div class="content"><div class="text"><span class="name">'+item.personName+'</span>'+
                            '<span class="sts">已'+activityName+'</span></div>'+
                            '<div class="subtext"><span class="dec">流转意见:'+hand+'</span></div>'+
                            '<div class="subtime"><span>'+item.showTime+'</span></div></div></div>';
                    }
                    else
                    {
                        $$("#wipe-"+j).addClass("status-process");
                        innerHtml += '<div class="left-status"><i class="icon">...</i><span class="line"></span><span class="arrow"></span></div>'+
                            '<div class="right-content"><div class="title-img"><i class="icon icon-'+j+'">'+activityName+ '</i></div>'+
                            '<div class="content"><div class="text"><span class="name">'+item.personName+'</span>'+
                            '<span class="sts">'+activityName+'中</span></div>';
                    }
                    $$("#wipe-"+j).html("");
                    $$("#wipe-"+j).append(innerHtml);
                    j++;
                }

            }
        }
    });
}

function existRepairCode(){
    var repairCode =$$("#repairCode").val();
    $$.ajax({
        url :"/jcds/choose/serialNumber?tableName=REPAIR",
        type:"get",
        async:false,
        success : function(data) {
            if(data!=undefined&&data!="")
            {
                data=data.replace(/\"/g,"");
                if(repairCode != data){
                    $$("#repairCode").val(data);
                }
            }
        }
    });
}

/** 待办流程Picker查询维修负责人 **/
function searchDutyGroupPersons(personId,workerIndex)
{
    var repairPersonIdValue=$$("#repairPersonIdValue").val();
    var repairPersonNameValue=$$("#repairPersonNameValue").val();
    if(repairPersonIdValue!=undefined&&repairPersonIdValue!=null&&repairPersonIdValue!=""&&repairPersonNameValue!=undefined&&repairPersonNameValue!=null&&repairPersonNameValue!="")
    {
        myApp.picker({
            input: '#repairPersonName'+workerIndex,
            toolbarCloseText:'完成',
            cols: [
                {
                    textAlign: 'center',
                    values: eval(repairPersonIdValue),
                    displayValues:eval(repairPersonNameValue)
                }
            ],
            formatValue: function (p, values, displayValues) {
                $$("#repairPersonId"+workerIndex).val(values[0]);
                $$("#repairPersonName"+workerIndex).val(displayValues[0]);
                return displayValues[0];
            }
        });
    }
    else {
        $$.ajax({
            type: "get",
            url: "/jcds/mobile/pick/searchDutyGroupPersons?condition.personId="+personId,
            dataType: "json",
            success: function (data, textStatus) {
                $$("#repairPersonIdValue").val(data.personIdJson);
                $$("#repairPersonNameValue").val(data.personNameJson);
                var repairPersonName = myApp.picker({
                    input: '#repairPersonName'+workerIndex,
                    toolbarCloseText:'完成',
                    cols: [
                        {
                            textAlign: 'center',
                            values: eval(data.personIdJson),
                            displayValues:eval(data.personNameJson)
                        }
                    ],
                    formatValue: function (p, values, displayValues) {
                        $$("#repairPersonId"+workerIndex).val(values[0]);
                        $$("#repairPersonName"+workerIndex).val(displayValues[0]);
                        return displayValues[0];
                    }
                });
            }

        });
    }
}

/** 取得维修方式 **/
function searchRepairMode(workerIndex)
{
    var repairModeValue=$$("#repairModeValue").val();
    var repairModeNameValue=$$("#repairModeNameValue").val();
    if(repairModeValue!=undefined&&repairModeValue!=null&&repairModeValue!=""&&repairModeNameValue!=undefined&&repairModeNameValue!=null&&repairModeNameValue!="")
    {
        myApp.picker({
            input: '#repairModeName'+workerIndex,
            toolbarCloseText:'完成',
            cols: [
                {
                    textAlign: 'center',
                    values: eval(repairModeValue),
                    displayValues:eval(repairModeNameValue)
                }
            ],
            formatValue: function (p, values, displayValues) {
                $$("#repairMode"+workerIndex).val(values[0]);
                $$("#repairModeName"+workerIndex).val(displayValues[0]);
                return displayValues[0];
            }
        });
    }
    else {
        $$.ajax({
            type: "get",
            url: "/jcds/mobile/pick/searchRepairMode",
            dataType: "json",
            success: function (data, textStatus) {
                $$("#repairModeValue").val(data.value);
                $$("#repairModeNameValue").val(data.displayValue);
                var repairModeName = myApp.picker({
                    input: '#repairModeName'+workerIndex,
                    toolbarCloseText:'完成',
                    cols: [
                        {
                            textAlign: 'center',
                            values: eval(data.value),
                            displayValues:eval(data.displayValue)
                        }
                    ],
                    formatValue: function (p, values, displayValues) {
                        $$("#repairMode"+workerIndex).val(values[0]);
                        $$("#repairModeName"+workerIndex).val(displayValues[0]);
                        return displayValues[0];
                    }
                });
            }
        });
    }
}

/** 取得维修方式 **/
function searchWorkerType(workerIndex)
{
    var workerTypeValue=$$("#workerTypeValue").val();
    var workerTypeNameValue=$$("#workerTypeNameValue").val();
    if(workerTypeValue!=undefined&&workerTypeValue!=null&&workerTypeValue!=""&&workerTypeNameValue!=undefined&&workerTypeNameValue!=null&&workerTypeNameValue!="")
    {
        myApp.picker({
            input: '#workerTypeName'+workerIndex,
            toolbarCloseText:'完成',
            cols: [
                {
                    textAlign: 'center',
                    values: eval(workerTypeValue),
                    displayValues:eval(workerTypeNameValue)
                }
            ],
            formatValue: function (p, values, displayValues) {
                $$("#workerType"+workerIndex).val(values[0]);
                $$("#workerTypeName"+workerIndex).val(displayValues[0]);
                return displayValues[0];
            }
        });
    }
    else {
        $$.ajax({
            type: "get",
            url: "/jcds/mobile/pick/searchWorkerType",
            dataType: "json",
            success: function (data, textStatus) {
                $$("#workerTypeValue").val(data.value);
                $$("#workerTypeNameValue").val(data.displayValue);
                var workerTypeName = myApp.picker({
                    input: '#workerTypeName'+workerIndex,
                    toolbarCloseText:'完成',
                    cols: [
                        {
                            textAlign: 'center',
                            values: eval(data.value),
                            displayValues:eval(data.displayValue)
                        }
                    ],
                    formatValue: function (p, values, displayValues) {
                        $$("#workerType"+workerIndex).val(values[0]);
                        $$("#workerTypeName"+workerIndex).val(displayValues[0]);
                        return displayValues[0];
                    }
                });
            }
        });
    }
}

/** 流程详细实际维修时间 **/
function searchActualFinishTime(){
    var today=new Date();

    var actualFinishTime = myApp.picker({
        input: '#actualFinishTime',
        rotateEffect:true,
        toolbarCloseText:'完成',
        value: [today.getFullYear(), ((today.getMonth()+1) < 10 ? '0' + (today.getMonth()+1) : (today.getMonth()+1)),
            (today.getDate() < 10 ? '0' + today.getDate() : today.getDate()),(today.getHours() < 10 ? '0' + today.getHours() : today.getHours()),
            (today.getMinutes() < 10 ? '0' + today.getMinutes() : today.getMinutes()),(today.getSeconds() < 10 ? '0' + today.getSeconds() : today.getSeconds())],
        onChange:function(picker,values,displayValues){
            var daysInMonth=new Date(picker.value[0],picker.value[1]*1,0).getDate();
            if(values[2]>daysInMonth)
            {
                picker.cols[4].setValue(daysInMonth);
            }
        },
        formatValue: function (p, values, displayValues) {
            return values[0]+'-'+values[1]+'-'+values[2]+' '+values[3]+':'+values[4]+':'+values[5];
        },
        cols: [
            // Years
            {
                values: (function () {
                    var arr = [];
                    for (var i = 1950; i <= 2030; i++) { arr.push(i); }
                    return arr;
                })()
            },
            // Divider
            {
                divider: true,
                content: '-'
            },
            // Months
            {
                values: ('01 02 03 04 05 06 07 08 09 10 11 12').split(' '),
                textAlign: 'left'
            },
            // Divider
            {
                divider: true,
                content: '-'
            },
            {
                values: ('01 02 03 04 05 06 07 08 09 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31').split(' '),
                textAlign: 'left'
            },
            {
                divider: true,
                content: '　'
            },
            {
                values: (function () {
                    var arr = [];
                    for (var i = 0; i <= 23; i++) { arr.push(i<10?'0'+i:i); }
                    return arr;
                })()
            },
            {
                divider: true,
                content: ':'
            },
            {
                values: (function () {
                    var arr = [];
                    for (var i = 0; i <= 59; i++) { arr.push(i<10?'0'+i:i); }
                    return arr;
                })()
            },
            {
                divider: true,
                content: ':'
            },
            {
                values: (function () {
                    var arr = [];
                    for (var i = 0; i <= 59; i++) { arr.push(i<10?'0'+i:i); }
                    return arr;
                })()
            }
        ]
    });
}

/** 设备Content高度 **/
function setDeviceHeight()
{
    var bodyH= document.body.clientHeight;
    var contentHeight=bodyH-44*2;;
    $$('#deviceContent').css('height', contentHeight+'px');
}

/** 搜索具体设备 **/
function searchDevice(){
    var searchDeviceUrl="/jcds/mobile/search/searchDevice";
    mainView.router.loadPage(searchDeviceUrl);
}

/** 设备Modal打开 **/
function controllDeviceTree(accordionId,pickerId)
{
    if(accordionId!=undefined&&accordionId!=null&&accordionId!=""&&pickerId!=undefined&&pickerId!=null&&pickerId!=""){
        if($$("#"+accordionId).hasClass("active"))
        {
            myApp.closeModal("#"+pickerId);
            $$("#"+accordionId).removeClass("active")
        }
        else
        {
            $$(".active").removeClass("active");
            $$("#"+accordionId).addClass("active");
            myApp.closeModal(".modal-in");
            myApp.pickerModal("#"+pickerId);
        }
    }
}

function removePickerClass()
{
    $$(".active").removeClass("active");
}

/** 设备三个下拉菜单 **/
function searchDeviceType(deviceSearchType,parentId,treeLevel,searchName){
    if(treeLevel!=undefined&&treeLevel!=null&&parentId!=undefined&&parentId!=null&&deviceSearchType!=undefined&&deviceSearchType!=null)
    {
        if(treeLevel==0)
        {
            if($$("#item-content"+deviceSearchType+parentId).hasClass("selected0"))
            {
                myApp.closeModal("#picker0");
            }
            else {
                var showUl="type1";
                searchDeviceTree(deviceSearchType,parentId,treeLevel,showUl);
                $$("#picker-item1").removeAttr("disabled");
                $$("#picker-item2").attr("disabled","disabled");
                myApp.closeModal("#picker0");
                $$("#deviceSearchType").val(deviceSearchType);
                $$("#searchId").val(0);
                $$("#parentId").val(0);
                fillDeviceData(false);
                $$("#accordion-content0").html(searchName);
                $$(".active").removeClass("active");
                $$(".selected0").removeClass("selected0");
                $$("#item-content"+deviceSearchType+parentId).addClass("selected0");
                $$("#accordion-content1").html("类别二");
                $$("#accordion-content2").html("类别三");
            }
        }
        else if(treeLevel==1)
        {
            if($$("#item-content"+deviceSearchType+parentId).hasClass("selected1"))
            {
                myApp.closeModal("#picker1");
            }
            else {
                var showUl="type2";
                searchDeviceTree(deviceSearchType,parentId,treeLevel,showUl);
                $$("#picker-item2").removeAttr("disabled");
                myApp.closeModal("#picker1");
                $$("#searchId").val(0);
                $$("#parentId").val(parentId);
                fillDeviceData(false);
                $$(".active").removeClass("active");
                $$(".selected1").removeClass("selected1");
                $$("#item-content"+deviceSearchType+parentId).addClass("selected1");
                $$("#accordion-content1").html(searchName);
                $$("#accordion-content2").html("类别三");
            }
        }
        else if(treeLevel==2)
        {
            if($$("#item-content"+deviceSearchType+parentId).hasClass("selected2"))
            {
                myApp.closeModal("#picker2");
            }
            else{
                myApp.closeModal("#picker2");
                $$("#searchId").val(parentId);
                $$("#parentId").val(0);
                fillDeviceData(false);
                $$("#accordion-content2").html(searchName);
                $$(".active").removeClass("active");
                $$(".selected2").removeClass("selected2");
                $$("#item-content"+deviceSearchType+parentId).addClass("selected2");
            }
        }
    }
}

/** 设备级联下拉 **/
function searchDeviceTree(deviceSearchType,parentId,treeLevel,showUl){
    $$('#'+showUl).html("");
    $$.ajax({
        type: "post",
        url: "/jcds/mobile/search/searchDeviceTree?condition.deviceSearchType="+deviceSearchType+"&condition.parentId="+parentId,
        dataType: "json",
        success: function (data, textStatus) {
            var length = data.length;
            var innerHtml = '';
            treeLevel=parseInt(treeLevel)+1;
            if(length>0)
            {
                for (var i = 0; i < length; i++) {
                    innerHtml += '<li class="item-content" id="item-content'+deviceSearchType+data[i].searchId+'" onclick="searchDeviceType('+deviceSearchType+','+data[i].searchId+','+treeLevel+',\''+data[i].searchName+'\')">'+
                        '<div class="item-inner">'+
                        '<div class="item-title">'+data[i].searchName+'</div>'+
                        '</div>'+
                        '</li>';
                }
                $$('#'+showUl).append(innerHtml);
            }
        }

    });
}


/** 所有设备第一次取值 **/
function fillDeviceData(isSearch) {
    if(!isSearch||lastDeviceSearch!=$$("#deviceSearch").val()) {
        if(isSearch)
        {
            lastDeviceSearch = $$("#deviceSearch").val();
        }
        $$('#device_total').val("");
        $$('#deviceCodeId').val(0);
        var searchId=$$("#searchId").val();
        if(searchId==undefined||searchId=="")
        {
            $$("#searchId").val(0);
        }
        var parentId=$$("#parentId").val();
        if(parentId==undefined||parentId=="")
        {
            $$("#parentId").val(0);
        }
        $$('#device_div > ul').html("");
        $$.ajax({
            type: "post",
            url: "/jcds/mobile/search/list_device",
            dataType: "json",
            data:myApp.formToJSON('#device_form'),
            success: function(data,textStatus) {
                var length=data.length;
                if(length!=0)
                {
                    var innerHtml = '';
                    for (var i = 0; i < length; i++) {
                        innerHtml += '<li>'+
                            '<a href="" onclick="chooseDevice(\''+data[i].id+'\',\''+data[i].deviceName+'\')" class="item-link item-content">'+
                            '<div class="item-inner">'+
                            '<div class="item-title-row">'+
                            '<div class="item-title">'+
                            '<span>'+data[i].deviceName+'</span>'+
                            '</div>'+
                            '<div class="item-after">'+data[i].deviceCode+'</div>'+
                            '</div>'+
                            '<div class="item-text">'+data[i].locationName+'</div>'+
                            '</div>'+
                            '</a>'+
                            '</li>';
                    }
                    $$('#device_div > ul').append(innerHtml);
                    $$('#deviceCodeId').val(data[length-1].id);
                    $$('#device_total').val(data[length-1].deviceTotal);
                    isDeviceLoading=false;
                    if(data[length-1].deviceTotal<=25)
                    {
                        myApp.detachInfiniteScroll($$('#deviceContent'));
                        $$('#devicePreloader').hide();
                    }
                    else
                    {
                        isDeviceInfinite=true;
                        initDevicePreload();
                    }
                }
                else {
                    myApp.detachInfiniteScroll($$('#deviceContent'));
                    $$('#devicePreloader').hide();
                }
            }
        });
    }
};

/** 已办判断是否出现加载更多按钮 **/
function initDevicePreload()
{
    device_lastIndex = $$('#device_div > ul > li').length;
    device_total=$$('#device_total').val();
    if(isDeviceInfinite)
    {
        myApp.attachInfiniteScroll($$('#deviceContent'));
        $$('#devicePreloader').show();
        isDeviceInfinite=false
    }
    $$('#deviceContent').on('infinite',function(){
        if(isDeviceLoading){
            return;
        }
        isDeviceLoading=true;
        setTimeout(function () {
            if(device_lastIndex>=device_total)
            {
                myApp.detachInfiniteScroll($$('#deviceContent'));
                $$('#devicePreloader').hide();
            }
            else
            {
                $$.ajax({
                    type: "post",
                    url: "/jcds/mobile/search/list_device",
                    dataType: "json",
                    data:myApp.formToJSON('#device_form'),
                    success: function (data, textStatus) {
                        //生成新条目的HTML
                        var length = data.length;
                        var innerHtml = '';
                        for (var i = 0; i < length; i++) {
                            innerHtml += '<li>'+
                                '<a href="" onclick="chooseDevice(\''+data[i].id+'\',\''+data[i].deviceName+'\')" class="item-link item-content">'+
                                '<div class="item-inner">'+
                                '<div class="item-title-row">'+
                                '<div class="item-title">'+
                                '<span>'+data[i].deviceName+'</span>'+
                                '</div>'+
                                '<div class="item-after">'+data[i].deviceCode+'</div>'+
                                '</div>'+
                                '<div class="item-text">'+data[i].locationName+'</div>'+
                                '</div>'+
                                '</a>'+
                                '</li>';
                        }
                        $$('#deviceCodeId').val(data[length - 1].id);
                        // 添加新条目
                        $$('#device_div > ul').append(innerHtml);
                        // 更新最后加载的序号
                        device_lastIndex =$$('#device_div > ul > li').length;

                        isDeviceLoading=false;
                    }

                });
            }
        },1000)
    })
}


/** 选择具体设备 **/
function chooseDevice(deviceId,deviceName)
{
    mainView.router.back({
        pageName:"bpm_task"
    });
    $$("#deviceId").val(deviceId);
    $$("#deviceName").val(deviceName);
}

/** 设备Content高度 **/
function setSpareHeight()
{
    var bodyH= document.body.clientHeight;
    var contentHeight=bodyH-44*2;
    $$('#spareContent').css('height', contentHeight+'px');
}

/** 搜索具体配件 **/
function searchSpare(spareIndex){
    var searchDeviceUrl="/jcds/mobile/search/searchSpare?spareIndex="+spareIndex;
    mainView.router.loadPage(searchDeviceUrl);
}

/** 配件点击按钮 **/
function searchSpareType(parentId){
    if(parentId!=undefined&&parentId!=null)
    {
        if($$("#spare_button"+parentId).hasClass("active"))
        {

        }
        else {
            $$("#searchId").val(parentId);
            fillSpareData(false);
            $$(".active").removeClass("active");
            $$("#spare_button"+parentId).addClass("active");
        }
    }
}

/** 选择配件第一次取值 **/
function fillSpareData(isSearch) {
    if(!isSearch||lastSpareSearch!=$$("#spareSearch").val()) {
        if(isSearch)
        {
            lastSpareSearch = $$("#spareSearch").val();
        }
        $$('#spare_total').val("");
        $$('#spareCodeId').val(0);
        var searchId=$$("#searchId").val();
        if(searchId==undefined||searchId=="")
        {
            $$("#searchId").val(0);
        }
        $$('#spare_div > ul').html("");
        $$.ajax({
            type: "post",
            url: "/jcds/mobile/search/list_spare",
            dataType: "json",
            data:myApp.formToJSON('#spare_form'),
            success: function(data,textStatus) {
                var length=data.length;
                if(length!=0)
                {
                    var innerHtml = '';
                    for (var i = 0; i < length; i++) {
                        innerHtml += '<li>'+
                            '<a href="" onclick="chooseSpare(\''+data[i].id+'\',\''+data[i].spareCode+'\',\''+data[i].spareName+'\')" class="item-link item-content">'+
                            '<div class="item-inner">'+
                            '<div class="item-title-row">'+
                            '<div class="item-title">'+
                            '<span>'+data[i].spareName+'</span>'+
                            '</div>'+
                            '<div class="item-after">'+data[i].spareCode+'</div>'+
                            '</div>'+
                            '<div class="item-text">'+data[i].spareCategoryName+'</div>'+
                            '</div>'+
                            '</a>'+
                            '</li>';
                    }
                    $$('#spare_div > ul').append(innerHtml);
                    $$('#spareCodeId').val(data[length-1].id);
                    $$('#spare_total').val(data[length-1].spareTotal);
                    isSpareLoading=false;
                    if(data[length-1].spareTotal<=25)
                    {
                        myApp.detachInfiniteScroll($$('#spareContent'));
                        $$('#sparePreloader').hide();
                    }
                    else
                    {
                        isSpareInfinite=true;
                        initSparePreload();
                    }
                }
                else {
                    myApp.detachInfiniteScroll($$('#spareContent'));
                    $$('#sparePreloader').hide();
                }
            }
        });
    }
};

/** 已办判断是否出现加载更多按钮 **/
function initSparePreload()
{
    spare_lastIndex = $$('#spare_div > ul > li').length;
    spare_total=$$('#spare_total').val();
    if(isSpareInfinite)
    {
        myApp.attachInfiniteScroll($$('#spareContent'));
        $$('#sparePreloader').show();
        isSpareInfinite=false
    }
    $$('#spareContent').on('infinite',function(){
        if(isSpareLoading){
            return;
        }
        isSpareLoading=true;
        setTimeout(function () {
            if(spare_lastIndex>=spare_total)
            {
                myApp.detachInfiniteScroll($$('#spareContent'));
                $$('#sparePreloader').hide();
            }
            else
            {
                $$.ajax({
                    type: "post",
                    url: "/jcds/mobile/search/list_spare",
                    dataType: "json",
                    data:myApp.formToJSON('#spare_form'),
                    success: function (data, textStatus) {
                        //生成新条目的HTML
                        var length = data.length;
                        var innerHtml = '';
                        for (var i = 0; i < length; i++) {
                            innerHtml += '<li>'+
                                '<a href="" onclick="chooseSpare(\''+data[i].id+'\',\''+data[i].spareCode+'\',\''+data[i].spareName+'\')" class="item-link item-content">'+
                                '<div class="item-inner">'+
                                '<div class="item-title-row">'+
                                '<div class="item-title">'+
                                '<span>'+data[i].spareName+'</span>'+
                                '</div>'+
                                '<div class="item-after">'+data[i].spareCode+'</div>'+
                                '</div>'+
                                '<div class="item-text">'+data[i].spareCategoryName+'</div>'+
                                '</div>'+
                                '</a>'+
                                '</li>';
                        }
                        $$('#spareCodeId').val(data[length - 1].id);
                        // 添加新条目
                        $$('#spare_div > ul').append(innerHtml);
                        // 更新最后加载的序号
                        spare_lastIndex =$$('#spare_div > ul > li').length;

                        isSpareLoading=false;
                    }

                });
            }
        },1000)
    })
}

/** 选择具体配件 **/
function chooseSpare(spareId,spareCode,spareName)
{
    var spareTypeIndex=$$("#spareTypeIndex").val();
    mainView.router.back({
        pageName:"bpm_task"
    });
    if(spareTypeIndex!=undefined&&spareTypeIndex!=null&&spareTypeIndex!="")
    {
        $$("#spareId"+spareTypeIndex).val(spareId);
        $$("#spareCode"+spareTypeIndex).val(spareCode);
        $$("#spareName"+spareTypeIndex).val(spareName);
    }
}

/** 评价星级 **/
function star(showId,gradeId,gradeValue) {
    if(gradeValue!=undefined&&gradeValue!=null&&gradeValue!="")
    {
        var value=parseInt(gradeValue);
        $$('#'+showId+' >li').removeClass('on');
        for (var i = 1; i <= value; i++) {
            $$('#'+gradeId+'-li'+i).addClass('on');
        }
        $$('#'+gradeId).val(value);
    }
}

/**删除人工费用明细**/
function removeWorkerList(num)
{
    myApp.confirm('确定删除人工明细('+num+')？',
        function () {
            var workerNum=$$("#workerNum").val();
            for(var i=num;i<workerNum;i++)
            {
                editWorkerList(i);
            }
            workerNum=parseInt(workerNum)-1;
            $$("#workerNum").val(workerNum);
            $$("#workerList"+num).remove();
            workerMoney();
        },
        function () {

        }
    );
}

/**增加明细**/
function addWorkerList()
{
    var workerIndex=$$("#workerIndex").val();
    var workerNum=$$("#workerNum").val();
    workerNum=parseInt(workerNum)+1;
    $$("#workerNum").val(workerNum);
    var innerHtml = '';
    innerHtml +='<div class="item-block" id="workerList'+workerNum+'"><div class="repair-title row"><span class="left-text col-50">人工信息（<span class="num" id="workerNum'+workerNum+'">'+workerNum+'</span>）</span>'+
        '<span class="right-button col-50"><a href="" class="icon-delete" id="removeWorker'+workerNum+'" onclick="removeWorkerList('+workerNum+')"></a></span></div>'+
        '<div class="repair-content"><ul><li><div class="item-content"><div class="item-inner">'+
        '<div class="item-title"><span class="required">*</span>人员类别</div><div class="item-input">'+
        '<input type="text" id="repairModeName'+workerIndex+'" placeholder="请选择(必填)">'+
        '<input type="hidden" id="repairMode'+workerIndex+'" name="workerList['+workerIndex+'].workerMode">'+
        '</div></div></div></li><li>'+
        '<div class="item-content"><div class="item-inner"><div class="item-title"><span class="required">*</span>维修人员</div><div class="item-input">'+
        '<input type="text" id="repairPersonName'+workerIndex+'" name="workerList['+workerIndex+'].workerName" placeholder="请选择(必填)">'+
        '<input type="hidden" id="repairPersonId'+workerIndex+'" name="workerList['+workerIndex+'].workerId">'+
        '</div></div></div></li><li>'+
        '<div class="item-content"><div class="item-inner"><div class="item-title"><span class="required">*</span>工种</div><div class="item-input">'+
        '<input type="text" id="workerTypeName'+workerIndex+'" placeholder="请选择(必填)">'+
        '<input type="hidden" id="workerType'+workerIndex+'" name="workerList['+workerIndex+'].workerType">'+
        '</div></div></div></li><li>'+
        '<div class="item-content"><div class="item-inner"><div class="item-title">项目描述</div><div class="item-input">'+
        '<input type="text" id="descripton'+workerIndex+'" name="workerList['+workerIndex+'].descripton" maxlength="16">'+
        '</div></div></div></li><li>'+
        '<div class="item-content"><div class="item-inner"><div class="item-title"><span class="required">*</span>工时(小时)</div><div class="item-input">'+
        '<input type="number" id="workHours'+workerIndex+'" name="workerList['+workerIndex+'].workHours"  onkeyup="isNum(this);" placeholder="请输入(必填)"/>'+
        '</div></div></div></li><li>'+
        '<div class="item-content"><div class="item-inner"><div class="item-title"><span class="required">*</span>费用(元)</div><div class="item-input">'+
        '<input type="number" id="workerMoney'+workerIndex+'" name="workerList['+workerIndex+'].money"  onkeyup="isNumDouble(this);" placeholder="请输入(必填)"/>'+
        '</div></div></div></li></ul></div></div>';
    $$("#worker").append(innerHtml);
    var personId=$$('#groupEmpId').val();
    if(personId!=null&&personId!="")
    {
        searchDutyGroupPersons(personId,workerIndex);
    }
    searchRepairMode(workerIndex);
    searchWorkerType(workerIndex);
    workerIndex=parseInt(workerIndex)+1;
    $$("#workerIndex").val(workerIndex);
}

function editWorkerList(num)
{
    var workerNum=parseInt(num)+1;
    $$("#workerList"+workerNum).attr("id","workerList"+num);
    $$("#workerNum"+workerNum).html(num);
    $$("#workerNum"+workerNum).attr("id","workerNum"+num);
    $$("#removeWorker"+workerNum).attr("onclick","removeWorkerList("+num+")");
    $$("#removeWorker"+workerNum).attr("id","removeWorker"+num);
}

/** 统计人员费用 **/
function workerMoney() {
    var t = 0.00;
    $$.each($$('input[id^="workerMoney"]'), function (index,value) {
        var a = $$(value).val();
        if(a==undefined||a==NaN||a=="")
        {
            $$(value).val("");
            a=parseFloat(0);
        }
        t = t + a * 1.0;
    });
    $$("#workerMoneyTotal").html(formatMoneyMobile(t));
}

/** 应填人员信息是否完整 **/
function workerValidate()
{
    var validate=true;
    if(validate)
    {
        $$.each($$('input[name$="workerMode"]'), function (index,value) {
            var a = $$(value).val();
            if(a==undefined||a==null||a=="")
            {
                validate=false;
            }
        });
        if(!validate)
        {
            bpmAlert="请选择人员类别或删除该人工信息";
        }
    }
    if(validate)
    {
        $$.each($$('input[name$="workerName"]'), function (index,value) {
            var a = $$(value).val();
            if(a==undefined||a==null||a=="")
            {
                validate=false;
            }
        });
        if(!validate)
        {
            bpmAlert="请选择维修人员或删除该人工信息";
        }
    }
    if(validate)
    {
        $$.each($$('input[name$="workerType"]'), function (index,value) {
            var a = $$(value).val();
            if(a==undefined||a==null||a=="")
            {
                validate=false;
            }
        });
        if(!validate)
        {
            bpmAlert="请选择人员工种或删除该人工信息";
        }
    }
    if(validate)
    {
        $$.each($$('input[id^="workHours"]'), function (index,value) {
            var a = $$(value).val();
            if(a==undefined||a==null||a=="")
            {
                validate=false;
            }
        });
        if(!validate)
        {
            bpmAlert="请填写人员工时或删除该人工信息";
        }
    }
    if(validate)
    {
        $$.each($$('input[id^="workerMoney"]'), function (index,value) {
            var a = $$(value).val();
            if(a==undefined||a==null||a=="")
            {
                validate=false;
            }
        });
        if(!validate)
        {
            bpmAlert="请填写人工费用或删除该人工信息";
        }
    }
    return validate;
}

/**删除耗材费用明细**/
function removeSpareList(num)
{
    myApp.confirm('确定删除耗材明细('+num+')？',
        function () {
            var spareNum=$$("#spareNum").val();
            for(var i=num;i<spareNum;i++)
            {
                editSpareList(i);
            }
            spareNum=parseInt(spareNum)-1;
            $$("#spareNum").val(spareNum);
            $$("#spareList"+num).remove();
            spareMoney();
        },
        function () {

        }
    );
}

/**增加明细**/
function addSpareList()
{
    var spareIndex=$$("#spareIndex").val();
    var spareNum=$$("#spareNum").val();
    spareNum=parseInt(spareNum)+1;
    $$("#spareNum").val(spareNum);
    var innerHtml = '';
    innerHtml +='<div class="item-block" id="spareList'+spareNum+'"><div class="repair-title row"><span class="left-text col-50">耗材信息（<span class="num" id="spareNum'+spareNum+'">'+spareNum+'</span>）</span>'+
    '<span class="right-button col-50"><a href="" class="icon-delete" id="removeSpare'+spareNum+'" onclick="removeSpareList('+spareNum+')"></a></span></div>'+
        '<div class="repair-content"><ul><li><div class="item-content"><div class="item-inner">'+
        '<div class="item-title"><span class="required">*</span>配件类型</div><div class="item-input">'+
        '<input type="text" value="耗材" id="spareTypeName'+spareIndex+'" disabled>'+
        '<input type="hidden" value="0" id="spareType'+spareIndex+'" name="spareList['+spareIndex+'].spareType">'+
        '</div></div></div></li><li>'+
        '<div class="item-content"><div class="item-inner"><div class="item-title"><span class="required">*</span>配件编号</div><div class="item-input">'+
        '<a href="" onclick="searchSpare('+spareIndex+')">'+
        '<input type="text" disabled id="spareCode'+spareIndex+'" name="spareList['+spareIndex+'].spareCode" placeholder="请选择(必填)"></a>'+
        '<input type="hidden" id="spareId'+spareIndex+'" name="spareList['+spareIndex+'].spareId">'+
        '</div></div></div></li><li>'+
        '<div class="item-content"><div class="item-inner"><div class="item-title">配件名称</div><div class="item-input">'+
        '<input type="text" disabled id="spareName'+spareIndex+'" name="spareList['+spareIndex+'].spareName">'+
        '</div></div></div></li><li>'+
        '<div class="item-content"><div class="item-inner"><div class="item-title">规格型号</div><div class="item-input">'+
        '<input type="text" disabled id="spareSpec'+spareIndex+'" name="spareList['+spareIndex+'].spareSpec">'+
        '</div></div></div></li><li>'+
        '<div class="item-content"><div class="item-inner"><div class="item-title"><span class="required">*</span>数量(个)</div><div class="item-input">'+
        '<input type="number" id="qty'+spareIndex+'" name="spareList['+spareIndex+'].qty" onkeyup="isNum(this);" placeholder="请输入(必填)"/>'+
        '</div></div></div></li><li>'+
        '<div class="item-content"><div class="item-inner"><div class="item-title"><span class="required">*</span>金额(元)</div><div class="item-input">'+
        '<input type="number" id="spareMoney'+spareIndex+'" name="spareList['+spareIndex+'].money" onkeyup="isNumDouble(this);" placeholder="请输入(必填)"/>'+
        '</div></div></div></li></ul></div></div>';
    $$("#spare").append(innerHtml);
    spareIndex=parseInt(spareIndex)+1;
    $$("#spareIndex").val(spareIndex);
}

/** 编辑耗材信息 **/
function editSpareList(num)
{
    var spareNum=parseInt(num)+1;
    $$("#spareList"+spareNum).attr("id","spareList"+num);
    $$("#spareNum"+spareNum).html(num);
    $$("#spareNum"+spareNum).attr("id","spareNum"+num);
    $$("#removeSpare"+spareNum).attr("onclick","removeSpareList("+num+")");
    $$("#removeSpare"+spareNum).attr("id","spareWorker"+num);
}

/** 统计耗材费用 **/
function spareMoney() {
    var t = 0.00;
    $$.each($$('input[id^="spareMoney"]'), function (index,value) {
        var a = $$(value).val();
        if(a==undefined||a==NaN||a=="")
        {
            $$(value).val("");
            a=parseFloat(0);
        }
        t = t + a * 1.0;
    });
    $$("#spareMoneyTotal").html(formatMoneyMobile(t));
}

/** 应填配件信息是否完整 **/
function spareValidate()
{
    var validate=true;
    if(validate)
    {
        $$.each($$('input[id^="spareId"]'), function (index,value) {
            var a = $$(value).val();
            if(a==undefined||a==null||a=="")
            {
                validate=false;
            }
        });
        if(!validate)
        {
            bpmAlert="请选择配件名称或删除该耗材明细";
        }
    }
    if(validate)
    {
        $$.each($$('input[id^="spareMoney"]'), function (index,value) {
            var a = $$(value).val();
            if(a==undefined||a==null||a=="")
            {
                validate=false;
            }
        });
        if(!validate)
        {
            bpmAlert="请填写配件金额或删除该耗材明细";
        }
    }
    if(validate)
    {
        $$.each($$('input[id^="qty"]'), function (index,value) {
            var a = $$(value).val();
            if(a==undefined||a==null||a=="")
            {
                validate=false;
            }
        });
        if(!validate)
        {
            bpmAlert="请填写配件数量或删除该耗材明细";
        }
    }
    return validate;
}

/** 待办流程提交 **/
function bpmSubmit(bpmId){
    if(!$$("#bpm_submit").hasClass("disabled")){
        $$("#bpm_submit").addClass("disabled");
    }
    var status=$$("#status").val();
    var canSubmit=false;
    if(status==1)
    {
        var repairPersonName=$$("#repairPersonName").val();
        if(repairPersonName==undefined||repairPersonName==null||repairPersonName=="")
        {
            $$("#bpm_submit").removeClass("disabled");
            alertModal("请选择现场负责人","repairPersonName");
            if(!$$("#li-1").hasClass("current"))
            {
                $$("#accordion-ul li").removeClass("current");
                $$("#li-1").addClass("current");
            }
        }
        else
        {
            canSubmit=true;
        }
    }
    else if(status==2)
    {
        var repairModeName=$$("#repairModeName").val();
        var actualFinishTime=$$("#actualFinishTime").val();
        bpmAlert="";
        if(repairModeName==undefined||repairModeName==null||repairModeName=="")
        {
            $$("#bpm_submit").removeClass("disabled");
            alertModal("请选择维修模式","repairModeName");
            if(!$$("#li-2").hasClass("current"))
            {
                $$("#accordion-ul li").removeClass("current");
                $$("#li-2").addClass("current");
            }
        }else if(actualFinishTime==undefined||actualFinishTime==null||actualFinishTime=="")
        {
            $$("#bpm_submit").removeClass("disabled");
            alertModal("请选择实际完成时间","actualFinishTime");
            if(!$$("#li-2").hasClass("current"))
            {
                $$("#accordion-ul li").removeClass("current");
                $$("#li-2").addClass("current");
            }
        }else if(actualFinishTime<$$("#repairDate").val())
        {
            $$("#bpm_submit").removeClass("disabled");
            alertModal("实际完成时间不应该在报修时间之前!","actualFinishTime");
            if(!$$("#li-2").hasClass("current"))
            {
                $$("#accordion-ul li").removeClass("current");
                $$("#li-2").addClass("current");
            }
        }else if(!workerValidate())
        {
            $$("#bpm_submit").removeClass("disabled");
            if(bpmAlert!=undefined&&bpmAlert!=null&&bpmAlert!="")
            {
                alertModal(bpmAlert);
            }
            $$("#accordion-ul li").removeClass("current");
            $$("#li-2").addClass("current");
        }else if(!spareValidate())
        {
            $$("#bpm_submit").removeClass("disabled");
            if(bpmAlert!=undefined&&bpmAlert!=null&&bpmAlert!="")
            {
                alertModal(bpmAlert);
            }
            $$("#accordion-ul li").removeClass("current");
            $$("#li-2").addClass("current");
        }
        else
        {
            canSubmit=true;
        }
    }
    else if(status==3)
    {
        var grade1=$$("#grade1").val();
        var grade2=$$("#grade2").val();
        var grade3=$$("#grade3").val();

        if(grade1==undefined||grade1==null||grade1=="")
        {
            $$("#bpm_submit").removeClass("disabled");
            alertModal("请选择服务质量评价");
            if(!$$("#li-3").hasClass("current"))
            {
                $$("#accordion-ul li").removeClass("current");
                $$("#li-3").addClass("current");
            }
        }else if(grade2==undefined||grade2==null||grade2=="")
        {
            $$("#bpm_submit").removeClass("disabled");
            alertModal("请选择服务效率评价");
            if(!$$("#li-3").hasClass("current"))
            {
                $$("#accordion-ul li").removeClass("current");
                $$("#li-3").addClass("current");
            }
        }else if(grade3==undefined||grade3==null||grade3=="")
        {
            $$("#bpm_submit").removeClass("disabled");
            alertModal("请选择服务态度评价");
            if(!$$("#li-3").hasClass("current"))
            {
                $$("#accordion-ul li").removeClass("current");
                $$("#li-3").addClass("current");
            }
        }
        else
        {
            canSubmit=true;
        }
    }
    if(canSubmit)
    {
        $$("#subType").val("2");
        $$("#_bpm_ts_id").val(bpmId);
        $$("#_bpm_enabled").val(true);
        $$("#bpm_ts_id").val(bpmId);
        $$.ajax({
            type: "post",
            url: "/jcds/mobile/repair/submit",
            dataType: "json",
            data:myApp.formToJSON('#bpm_form'),
            success: function (data, textStatus) {
                if(data.status=="success")
                {
                    var message=data.data;
                    if(message!=undefined&&message!=null&&message!="")
                    {
                        bpm_message=message;
                    }
                    mainView.router.back({
                        url:"/jcds/mobile/todo/list",
                        force:true,
                        pushState:false
                    });
                    mainView.router.refreshPage();
                }
                else {
                    var message=data.message;
                    $$("#bpm_submit").removeClass("disabled");
                    if(message!=undefined&&message!=null&&message!="")
                    {
                        alertModal(message);
                    }
                }
            }

        });
    }
}

/** 查看待办流程 **/
function to_task(id)
{
    if(id=="")
    {
        alertModal("待办流程错误！");
    }
    else
    {
        var formUrl='/jcds/mobile/bpm/task?_tid='+id;
        mainView.router.loadPage(formUrl);
    }
}
/** 查看已办流程 **/
function to_history(formUrl)
{
    if(formUrl=="error")
    {
        alertModal("已办流程错误！");
    }
    else
    {
        //hiddenToolBar()
        mainView.router.loadPage(formUrl);
    }
}

/**===========流程详细内容结束============**/

/** 冒泡信息 **/
function alertModal(text,focusId){

    var modal=myApp.modal({
        text:text
    });

    $$(".modal-overlay").on("click", function (e) {
        myApp.closeModal(modal);
    });

    setTimeout(function () {
        myApp.closeModal(modal);
    },2500);

    if(focusId!=undefined&&focusId!=null&&focusId!="")
    {
        $$(".modal").on('closed', function (e) {
            $$("#"+focusId).focus();
        });
    }
}

//function loadToolBarUrl(url)
//{
//    alertModal(url);
//    mainView.router.loadPage(url);
//}
