// Initialize your app
var myApp = new Framework7({
    modalTitle:'中控集成大厦',
    animatePages:false
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
var monthChanges=[];
var normalAttendances=[];
var unusualAttendances=[];
var totalAttendances=[];
var monthChange,normalAttendance,unusualAttendance,totalAttendance;
var showUnusualMonth,showUnusualYear;
var isVisitorInfinite,isVisitorLoading,visitor_message,visitor_lastIndex,visitor_total;
var patternZzs=/^[0-9]*[1-9][0-9]*$/;
var patternTel=/((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/;

/** 页面加载初始化 **/
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
    else if(page.name=="attendance")
    {
        initAttendance();
    }
    else if(page.name=="visitor")
    {
        initVisitor();
    }
    else if(page.name=="about")
    {

    }
    else if(page.name=="device"){
        initDevice();
    }
    else if(page.name=="spare"){
        initSpare();
    }
    else if(page.name=="visitor_add")
    {
        stop("visitDays");
        stop("expectVisitors");
        stop("visitCars");
        searchVisitTime();
        searchVisitReason();
    }
    else if(page.name=="visitor_edit")
    {
        stop("visitDays");
        stop("expectVisitors");
        stop("visitCars");
        searchVisitTime($$("#visitTime").val());
        searchVisitReason($$("#visitReason").val());
    }
});

/** 判断是否隐藏toolbar **/
$$(document).on('pageBeforeAnimation', function (e) {
    // Do something here when page loaded and initialized
    var page = e.detail.page;

    if(page.name == 'todo'||page.name == 'attendance'||page.name == 'visitor'||page.name == 'about'){
        //显示工具栏
        showToolBar();
    }else {
        //隐藏工具栏
        //myApp.hideToolbar('.toolbar');
        hiddenToolBar();
        //$$(".view").css('padding-bottom',0);
    }
});

/**===========初始化方法开始============**/

/** toolbar点击方法 **/
function toolClick(toolId)
{
    $$(".tabbar-link").removeClass("active");
    $$("#"+toolId).addClass("active");
}

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
    alertModal("浏览器高度"+BodyH);
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
    setTodoHeight();
    fillTaskData();
    var BodyH = document.body.clientHeight;
    alertModal("浏览器高度初始化"+BodyH);
    var taskSearchbar=myApp.searchbar('#task_form',{
        customSearch:true
    });

    $$('#task_form').on('search',function(e)
    {
        fillTaskData();
    });

    var historySearchbar=myApp.searchbar('#history_form',{
        customSearch:true
    });

    $$('#history_form').on('search',function(e)
    {
        fillHistoryData();
    });
    if(bpm_message!=null&&bpm_message!=""&&bpm_message!=undefined)
    {
        alertModal(bpm_message);
        bpm_message="";
    }
}

/** 流程TAB点击事件 **/
function bpmClick(tab){
    if(tab=="tab1")
    {

    }
    else if(tab=="tab2")
    {
        if(history_tab)
        {
            fillHistoryData();
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
    }
    else if(status==2)
    {
        searchRepairMode("");
        searchActualFinishTime();
        //searchAllDevice();
        var personId=$$('#groupEmpId').val();
        if(personId!=null&&personId!="")
        {
            searchDutyGroupPersons(personId,0);
        }
        searchRepairMode(0);
        searchWorkerType(0);
        $$("#worker").on("change", "input[name$='.money']", function () {
            workerMoney();
        });
        $$("#spare").on("change", "input[name$='.money']", function () {
            spareMoney();
        });
        $$("#worker").on("change", function () {
            canAddWorker();
        });
    }
}


/** 已办流程详情组件初始化 **/
function initHistory(status) {
    if (status == 4)
    {
        var grade1=$$('#grade1').val();
        var grade2=$$('#grade2').val();
        var grade3=$$('#grade3').val();
        if(grade1!=null&&grade1!=""&&grade1!=undefined)
        {
            var gradeLength=parseInt(grade1);
            for(var i=1;i<=gradeLength;i++)
            {
                $$("#grade1-li"+i).addClass("on");
            }
        }
        if(grade2!=null&&grade2!=""&&grade2!=undefined)
        {
            var gradeLength=parseInt(grade2);
            for(var i=1;i<=gradeLength;i++)
            {
                $$("#grade2-li"+i).addClass("on");
            }
        }
        if(grade3!=null&&grade3!=""&&grade3!=undefined)
        {
            var gradeLength=parseInt(grade3);
            for(var i=1;i<=gradeLength;i++)
            {
                $$("#grade3-li"+i).addClass("on");
            }
        }
    }
}

/** 初始化访客预约页面 **/
function initVisitor()
{
    isVisitorInfinite=false;
    isVisitorLoading=false;
    $$("#visitSelectStatus").val('0');
    setVisitorHeight();
    fillVisitorData();
    var visitorSearchbar=myApp.searchbar('#visitor_form',{
        customSearch:true
    });

    $$('#visitor_form').on('search',function(e)
    {
        fillVisitorData();
    });
    if(visitor_message!=null&&visitor_message!=""&&visitor_message!=undefined)
    {
        alertModal(visitor_message);
        visitor_message="";
    }

    $$("#visitorSelect").on("click",function(e){
        //阻止事件冒泡
        e.stopPropagation();

        if($$("#visitorSelectOption").hasClass('show')&&$$("body").hasClass('dropdown-show')){
            $$("#visitorSelectOption").hide().removeClass('show');
        }else {
            $$("body").addClass('dropdown-show');
            $$("#visitorSelectOption").show().addClass('show');

            $$('body').append("")
        }
    });

    $$("body").on("click", function (){
        if($$("body").hasClass('dropdown-show')) {
            $$("body").removeClass('dropdown-show');
            $$("#visitorSelectOption").hide();
        }
    });
}

/** 初始化设备搜索页面 **/
function initDevice(){
    var deviceSearchbar=myApp.searchbar('#device_form',{
        customSearch:true
    });

    $$('#device_form').on('search',function(e)
    {

    });
}

/** 初始化设备配件页面 **/
function initSpare(){
    var deviceSearchbar=myApp.searchbar('#spare_form',{
        customSearch:true
    });

    $$('#spare_form').on('search',function(e)
    {

    });
}

/**===========初始化方法结束============**/

/**===========待办流程开始============**/
/** 待办流程高度 **/
function setTodoHeight()
{
    var bodyH= document.body.clientHeight;;
    var tabHeight=bodyH-44-38;
    var tabContentHeight=tabHeight-44;
    $$('.tabs-content').css('height', tabHeight+'px');
    $$('.tabs-content .content-block').css('height', tabContentHeight+'px');
}

/** 待办流程第一次取值 **/
function fillTaskData() {
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
                var taskSearch=$$("#taskSearch").val();
                var show=true;
                if(taskSearch!=null&&taskSearch!="")
                {
                    if(taskSearch!=data[0].taskSearch)
                    {
                        show=false;
                    }
                }
                if(show)
                {
                    for (var i = 0; i < length; i++) {
                        innerHtml += '<li>'+
                            '<a href="/jcds/mobile/bpm/task?_tid='+data[i].id+'" class="item-link item-content">'+
                            '<i class="icon icon-c-message '+data[i].typeColor+'">'+data[i].typeName+'</i>'+
                            '<div class="item-inner">'+
                            '<div class="item-title-row">'+
                            '<div class="item-title">'+
                            '<span>'+data[i].name+'</span>'+
                            '</div>'+
                            '<div class="item-after">'+data[i].description+'</div>'+
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
                    if(data[length-1].taskTotal<=10)
                    {
                        myApp.detachInfiniteScroll($$('#taskPreloader'));
                        $$('#taskPreloader').hide();
                        isTaskInfinite=true;
                    }
                    else
                    {
                        initTaskPreload();
                    }
                }
            }
            else
            {
                myApp.detachInfiniteScroll($$('#taskPreloader'));
                $$('#taskPreloader').hide();
                isTaskInfinite=true;
            }
        }
    });
};

/** 判断是否出现加载更多按钮 **/
function initTaskPreload()
{
    task_lastIndex = $$('#task_div > ul > li').length;
    task_total=$$('#task_total').val();

    $$('#taskContent').on('infinite',function(){
        if(isTaskLoading){
            return;
        }
        isTaskLoading=true;
        if(isTaskInfinite)
        {
            myApp.attachInfiniteScroll($$('#taskPreloader'));
            $$('#taskPreloader').show();
            isTaskInfinite=false
        }
        setTimeout(function () {
            if(task_lastIndex>=task_total)
            {
                myApp.detachInfiniteScroll($$('#taskPreloader'));
                $$('#taskPreloader').hide();
                isTaskInfinite=true;
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
                            innerHtml += '<li>'+
                                '<a href="/jcds/mobile/bpm/task?_tid='+data[i].id+'" class="item-link item-content">'+
                                '<i class="icon icon-c-message '+data[i].typeColor+'">'+data[i].typeName+'</i>'+
                                '<div class="item-inner">'+
                                '<div class="item-title-row">'+
                                '<div class="item-title">'+
                                '<span>'+data[i].name+'</span>'+
                                '</div>'+
                                '<div class="item-after">'+data[i].description+'</div>'+
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
function fillHistoryData() {
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
                var historySearch=$$("#historySearch").val();
                var showHistory=true;
                if(historySearch!=null&&historySearch!="")
                {
                    if(historySearch!=data[0].historySearch)
                    {
                        showHistory=false;
                    }
                }
                if(showHistory)
                {
                    for (var i = 0; i < length; i++) {
                        innerHtml += '<li>'+
                            '<a href="" onclick="to_history(\''+data[i].formUrl+'\')" class="item-link item-content">'+
                            '<i class="icon icon-c-message '+data[i].typeColor+'">'+data[i].typeName+'</i>'+
                            '<div class="item-inner">'+
                            '<div class="item-title-row">'+
                            '<div class="item-title">'+
                            '<span>'+data[i].activityName+'</span>'+
                            '</div>'+
                            '<div class="item-after">'+data[i].description+'</div>'+
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
                    if(data[length-1].historyTotal<10)
                    {
                        myApp.detachInfiniteScroll($$('#historyPreloader'));
                        $$('#historyPreloader').hide();
                        isHistoryInfinite=true;
                    }
                    else
                    {
                        initHistoryPreload();
                    }
                }
            }
            else {
                myApp.detachInfiniteScroll($$('#historyPreloader'));
                $$('#historyPreloader').hide();
                isHistoryInfinite=true;
            }
        }
    });
};

/** 已办判断是否出现加载更多按钮 **/
function initHistoryPreload()
{
    history_lastIndex = $$('#history_div > ul > li').length;
    history_total=$$('#history_total').val();

    $$('#historyContent').on('infinite',function(){
        if(isHistoryLoading){
            return;
        }
        isHistoryLoading=true;
        if(isHistoryInfinite)
        {
            myApp.attachInfiniteScroll($$('#historyPreloader'));
            $$('#historyPreloader').show();
            isHistoryInfinite=false
        }
        setTimeout(function () {
            if(history_lastIndex>=history_total)
            {
                myApp.detachInfiniteScroll($$('#historyPreloader'));
                $$('#historyPreloader').hide();
                isHistoryInfinite=true;
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
                            innerHtml += '<li>'+
                                '<a href="" onclick="to_history(\''+data[i].formUrl+'\')" class="item-link item-content">'+
                                '<i class="icon icon-c-message '+data[i].typeColor+'">'+data[i].typeName+'</i>'+
                                '<div class="item-inner">'+
                                '<div class="item-title-row">'+
                                '<div class="item-title">'+
                                '<span>'+data[i].activityName+'</span>'+
                                '</div>'+
                                '<div class="item-after">'+data[i].description+'</div>'+
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
                    if(showTime!=null&&showTime!=undefined&&showTime!="")
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

/** 待办流程Picker查询维修负责人 **/
function searchDutyGroupPersons(personId,workerIndex)
{
    var repairPersonIdValue=$$("#repairPersonIdValue").val();
    var repairPersonNameValue=$$("#repairPersonNameValue").val();
    if(repairPersonIdValue!=null&&repairPersonIdValue!=undefined&&repairPersonIdValue!=""&&repairPersonNameValue!=null&&repairPersonNameValue!=undefined&&repairPersonNameValue!="")
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
    if(repairModeValue!=null&&repairModeValue!=undefined&&repairModeValue!=""&&repairModeNameValue!=null&&repairModeNameValue!=undefined&&repairModeNameValue!="")
    {
        myApp.picker({
            input: '#repairModeName'+workerIndex,
            toolbarCloseText:'完成',
            cols: [
                {
                    textAlign: 'center',
                    values: eval(repairModeNameValue),
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
    if(workerTypeValue!=null&&workerTypeValue!=undefined&&workerTypeValue!=""&&workerTypeNameValue!=null&&workerTypeNameValue!=undefined&&workerTypeNameValue!="")
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

/** 取得所有设备 **/
function searchAllDevice()
{
    $$('#allDevice').html("");
    $$.ajax({
        type: "post",
        url: "/jcds/mobile/search/searchAllDevice",
        dataType: "json",
        success: function (data, textStatus) {
            var length = data.length;
            var innerHtml = '';
            if(length>0)
            {
                for (var i = 0; i < length; i++) {
                    innerHtml += '<option value="'+data[i].id+'">'+data[i].deviceName+'</option>';
                }
                $$('#allDevice').append(innerHtml);
                $$("#deviceId").val(data[0].id);
                $$("#deviceName").val(data[0].deviceName);
                $$("#showDevice").html(data[0].deviceName);
            }
        }

    });
}

/** 评价星级 **/
function star(showId,gradeId,gradeValue) {
    if(gradeValue!=null&&gradeValue!=undefined&&gradeValue!="")
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
    var showNum=parseInt(num)+1;
    myApp.confirm('确定删除人工明细('+showNum+')？',
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

function canAddWorker()
{
    //console.log($$("input[name$='.repairMode']"));
}

/**增加明细**/
function addWorkerList()
{
    var workerIndex=$$("#workerIndex").val();
    workerIndex=parseInt(workerIndex)+1;
    $$("#workerIndex").val(workerIndex);
    var workerNum=$$("#workerNum").val();
    workerNum=parseInt(workerNum)+1;
    $$("#workerNum").val(workerNum);
    var num=parseInt(workerNum)+1;
    var innerHtml = '';
    innerHtml +='<div class="item-block" id="workerList'+workerNum+'"><div class="repair-title row"><span class="left-text col-50">人工信息（<span class="num" id="workerNum'+workerNum+'">'+num+'</span>）</span>'+
        '<span class="right-button col-50"><a href="" class="icon-delete" id="removeWorker'+workerNum+'" onclick="removeWorkerList('+workerNum+')">删除2</a></span></div>'+
        '<div class="repair-content"><ul><li><div class="item-content"><div class="item-inner">'+
        '<div class="item-title">人员类别</div><div class="item-input">'+
        '<input type="text" id="repairModeName'+workerIndex+'">'+
        '<input type="hidden" id="repairMode'+workerIndex+'" name="workList['+workerIndex+'].repairMode">'+
        '</div></div></div></li><li>'+
        '<div class="item-content"><div class="item-inner"><div class="item-title">维修人员</div><div class="item-input">'+
        '<input type="text" id="repairPersonName'+workerIndex+'" name="workerList['+workerIndex+'].workerName">'+
        '<input type="hidden" id="repairPersonId'+workerIndex+'" name="workerList['+workerIndex+'].workerId">'+
        '</div></div></div></li><li>'+
        '<div class="item-content"><div class="item-inner"><div class="item-title">工种</div><div class="item-input">'+
        '<input type="text" id="workerTypeName'+workerIndex+'">'+
        '<input type="hidden" id="workerType'+workerIndex+'" name="workerList['+workerIndex+'].workerType">'+
        '</div></div></div></li><li>'+
        '<div class="item-content"><div class="item-inner"><div class="item-title">项目描述</div><div class="item-input">'+
        '<input type="number" id="descripton'+workerIndex+'" name="workerList['+workerIndex+'].descripton">'+
        '</div></div></div></li><li>'+
        '<div class="item-content"><div class="item-inner"><div class="item-title">工时(小时)</div><div class="item-input">'+
        '<input type="number" id="workHours'+workerIndex+'" name="workerList['+workerIndex+'].workHours"  onkeyup="isNum(this);"/>'+
        '</div></div></div></li><li>'+
        '<div class="item-content"><div class="item-inner"><div class="item-title">费用(元)</div><div class="item-input">'+
        '<input type="number" id="workerMoney'+workerIndex+'" name="workerList['+workerIndex+'].money"  onkeyup="isNumDouble(this);"/>'+
        '</div></div></div></li></ul></div></div>';
    $$("#worker").append(innerHtml);
    var personId=$$('#groupEmpId').val();
    if(personId!=null&&personId!="")
    {
        searchDutyGroupPersons(personId,workerIndex);
    }
    searchRepairMode(workerIndex);
    searchWorkerType(workerIndex);
}

function editWorkerList(num)
{
    var workerNum=parseInt(num)+1;
    $$("#workerList"+workerNum).attr("id","workerList"+num);
    $$("#workerNum"+workerNum).html(workerNum);
    $$("#workerNum"+workerNum).attr("id","workerNum"+num);
    $$("#removeWorker"+workerNum).attr("onclick","removeWorkerList("+num+")");
    $$("#removeWorker"+workerNum).attr("id","removeWorker"+num);
}

/** 统计人员费用 **/
function workerMoney() {
    var t = 0.00;
    $$.each($$('input[id^="workerMoney"]'), function (index,value) {
        var a = $$(value).val();
        if(a==""||a==undefined||a==NaN)
        {
            $$(value).val("");
            a=parseFloat(0);
        }
        t = t + a * 1.0;
    });
    $$("#workerMoneyTotal").html(formatMoneyMobile(t));
}

/**删除耗材费用明细**/
function removeSpareList(num)
{
    var showNum=parseInt(num)+1;
    myApp.confirm('确定删除耗材明细('+showNum+')？',
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
    spareIndex=parseInt(spareIndex)+1;
    $$("#spareIndex").val(spareIndex);
    var spareNum=$$("#spareNum").val();
    spareNum=parseInt(spareNum)+1;
    $$("#spareNum").val(spareNum);
    var num=parseInt(spareNum)+1;
    var innerHtml = '';
    innerHtml +='<div class="item-block" id="spareList'+spareNum+'"><div class="repair-title row"><span class="left-text col-50">耗材信息（<span class="num" id="spareNum'+spareNum+'">'+num+'</span>）</span>'+
    '<span class="right-button col-50"><a href="" class="icon-delete" id="removeSpare'+spareNum+'" onclick="removeSpareList('+spareNum+')">删除1</a></span></div>'+
        '<div class="repair-content"><ul><li><div class="item-content"><div class="item-inner">'+
        '<div class="item-title">配件类型</div><div class="item-input">'+
        '<input type="text" id="spareTypeName'+spareIndex+'">'+
        '<input type="hidden" id="spareType'+spareIndex+'" name="workList['+spareIndex+'].spareType">'+
        '</div></div></div></li><li>'+
        '<div class="item-content"><div class="item-inner"><div class="item-title">配件编号</div><div class="item-input">'+
        '<input type="text" id="spareCode'+spareIndex+'" name="spareList['+spareIndex+'].spareCode">'+
        '<input type="hidden" id="spareId'+spareIndex+'" name="spareList['+spareIndex+'].spareId">'+
        '</div></div></div></li><li>'+
        '<div class="item-content"><div class="item-inner"><div class="item-title">配件名称</div><div class="item-input">'+
        '<input type="text" id="spareName'+spareIndex+'" name="spareList['+spareIndex+'].spareName">'+
        '</div></div></div></li><li>'+
        '<div class="item-content"><div class="item-inner"><div class="item-title">规格型号</div><div class="item-input">'+
        '<input type="number" id="spareSpec'+spareIndex+'" name="spareList['+spareIndex+'].spareSpec">'+
        '</div></div></div></li><li>'+
        '<div class="item-content"><div class="item-inner"><div class="item-title">数量(个)</div><div class="item-input">'+
        '<input type="number" id="qty'+spareIndex+'" name="spareList['+spareIndex+'].qty"/>'+
        '</div></div></div></li><li>'+
        '<div class="item-content"><div class="item-inner"><div class="item-title">金额(元)</div><div class="item-input">'+
        '<input type="number" id="spareMoney'+spareIndex+'" name="spareList['+spareIndex+'].money"/>'+
        '</div></div></div></li></ul></div></div>';
    $$("#spare").append(innerHtml);
}

function editSpareList(num)
{
    var spareNum=parseInt(num)+1;
    $$("#spareList"+spareNum).attr("id","spareList"+num);
    $$("#spareNum"+spareNum).html(spareNum);
    $$("#spareNum"+spareNum).attr("id","spareNum"+num);
    $$("#removeSpare"+spareNum).attr("onclick","removeSpareList("+num+")");
    $$("#removeSpare"+spareNum).attr("id","spareWorker"+num);
}

/** 统计耗材费用 **/
function spareMoney() {
    var t = 0.00;
    $$.each($$('input[id^="spareMoney"]'), function (index,value) {
        var a = $$(value).val();
        if(a==""||a==undefined||a==NaN)
        {
            $$(value).val("");
            a=parseFloat(0);
        }
        t = t + a * 1.0;
    });
    $$("#spareMoneyTotal").html(formatMoneyMobile(t));
}

/** 待办流程提交 **/
function bpmSubmit(bpmId){
    var status=$$("#status").val();
    var canSubmit=false;
    if(status==1)
    {
        var repairPersonName=$$("#repairPersonName").val();
        if(repairPersonName==null||repairPersonName==""||repairPersonName==undefined)
        {
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
        var deviceName=$$("#deviceName").val();

        if(repairModeName==null||repairModeName==""||repairModeName==undefined)
        {
            alertModal("请选择维修模式","repairModeName");
            if(!$$("#li-2").hasClass("current"))
            {
                $$("#accordion-ul li").removeClass("current");
                $$("#li-2").addClass("current");
            }
        }else if(actualFinishTime==null||actualFinishTime==""||actualFinishTime==undefined)
        {
            alertModal("请选择实际完成时间","actualFinishTime");
            if(!$$("#li-2").hasClass("current"))
            {
                $$("#accordion-ul li").removeClass("current");
                $$("#li-2").addClass("current");
            }
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

        if(grade1==null||grade1==""||grade1==undefined)
        {
            alertModal("请选择服务质量评价");
            if(!$$("#li-3").hasClass("current"))
            {
                $$("#accordion-ul li").removeClass("current");
                $$("#li-3").addClass("current");
            }
        }else if(grade2==null||grade2==""||grade2==undefined)
        {
            alertModal("请选择服务效率评价");
            if(!$$("#li-3").hasClass("current"))
            {
                $$("#accordion-ul li").removeClass("current");
                $$("#li-3").addClass("current");
            }
        }else if(grade3==null||grade3==""||grade3==undefined)
        {
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
                    if(message!=null&&message!=""&&message!=undefined)
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
                    if(message!=null&&message!=""&&message!=undefined)
                    {
                        alertModal(message);
                    }
                }
            }

        });
    }
}

/** 选择具体设备 **/
function chooseDevice(value,content)
{
    var deviceNames=content.split("→")
    var deviceName=deviceNames;
    if(deviceNames.length>0)
    {
        deviceName=deviceNames[deviceNames.length-1];
    }
    $$("#deviceId").val(value);
    $$("#deviceName").val(deviceName);
    $$("#showDevice").html(deviceName);

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
        mainView.router.loadPage(formUrl);
    }
}

/**===========流程详细内容结束============**/

/**===========考勤管理模块开始============**/

/** 日历表功能初始化 **/
function initAttendance()
{
    var monthNames = ['01','02','03','04','05','06','07','08','09','10','11','12'];
    var calendarInline = myApp.calendar({
        container: '#calendar-inline-container',
        value: [new Date()],
        weekHeader: true,
        weekendDays:[0,6],
        dayNamesShort:['日','一','二','三','四','五','六'],
        toolbarTemplate:
        '<div class="toolbar calendar-custom-toolbar">' +
        '<div class="toolbar-inner">' +
        '<div class="left">' +
        '<a href="#" class="link icon-only"><i class="icon icon-back"></i></a>' +
        '</div>' +
        '<div class="center"></div>' +
        '<div class="right">' +
        '<a href="#" class="link icon-only"><i class="icon icon-forward"></i></a>' +
        '</div>' +
        '</div>' +
        '</div>',
        onOpen: function (p) {
            $$('.calendar-custom-toolbar .center').text(monthNames[p.currentMonth] +'月 ' + p.currentYear+'年');
            $$('.calendar-custom-toolbar .left .link').on('click', function () {
                calendarInline.prevMonth();
            });
            $$('.calendar-custom-toolbar .right .link').on('click', function () {
                calendarInline.nextMonth();
            });
            openAttendance();
            searchToday();
            countMonthAttendances(p.currentMonth,p.currentYear);
            searchMonthAttendances(p.currentMonth,p.currentYear);
            showUnusualMonth=p.currentMonth;
            showUnusualYear=p.currentYear;
        },
        onMonthYearChangeStart: function (p) {
            $$('.calendar-custom-toolbar .center').text(monthNames[p.currentMonth] +'月 ' + p.currentYear+'年');
            monthYearChange(p.currentMonth,p.currentYear);
            searchMonthAttendances(p.currentMonth,p.currentYear);
            showUnusualMonth=p.currentMonth;
            showUnusualYear=p.currentYear;
        },
        onDayClick:function(p,dayContainer,year,month,day)
        {
            searchAttendance(year,month,day);
        }
    });
}

/** 判断月份变换是否在数组里 **/
function checkMonthYearChange(month,year){
    month=month+1;
    var monthYear=year+'-'+(month<10?'0'+month:month);
    var length=monthChanges.length;
    if(length>0)
    {
        for(var i=0;i<length;i++)
        {
            if(monthYear==monthChanges[i])
            {
                $$("#unusualDetail").removeClass("close");
                $$("#unusualDetail").removeAttr("onclick");
                totalAttendance=totalAttendances[i];
                normalAttendance=normalAttendances[i];
                unusualAttendance=unusualAttendances[i];
                $$("#sumDate").html(year+"年"+month+"月考勤情况");
                $$("#normalDays").html("("+normalAttendance+")");
                $$("#unusualDays").html("("+unusualAttendance+")");
                if(unusualAttendance!=0)
                {
                    $$("#unusualDetail").addClass("close");
                    $$("#unusualDetail").attr("onclick","showList()");
                }
                return false;
            }
        }
    }
    return true;
}

/** 根据月份取出该月考勤情况并且展示在日历上 **/
function searchMonthAttendances(month,year)
{
    month=month+1;
    var monthYear=year+'-'+(month<10?'0'+month:month);
    $$.ajax({
        type: "get",
        url: "/jcds/mobile/attendance/searchMonthAttendances?condition.eventDate="+monthYear,
        dataType: "json",
        success: function (data, textStatus) {
            $$(".picker-calendar-day").removeClass("attendance_calendar0");
            $$(".picker-calendar-day").removeClass("attendance_calendar1");
            if(textStatus==200)
            {
                var length = data.length;
                if(length>0)
                {
                    for (var i = 0; i < length; i++) {
                        //console.log(data[i].dataDateCss);
                        if(data[i].dataDateCss!=null&&data[i].dataDateCss!="")
                        {
                            $$("[data-date='"+data[i].dataDate+"']").addClass("attendance_calendar"+data[i].dataDateCss);
                        }
                    }
                }
            }
        },
        error: function () {
            $$(".picker-calendar-day").removeClass("attendance_calendar0");
            $$(".picker-calendar-day").removeClass("attendance_calendar1");
        }
    });
}

/** 统计该月考勤情况 **/
function countMonthAttendances(month,year)
{
    month=eval(month)+1;
    var monthYear=year+'-'+(month<10?'0'+month:month);
    monthChanges.push(monthYear);
    $$.ajax({
        type: "get",
        url: "/jcds/mobile/attendance/countMonthAttendances?condition.eventDate="+monthYear,
        dataType: "json",
        success: function (data, textStatus) {
            $$("#unusualDetail").removeClass("close");
            $$("#unusualDetail").removeAttr("onclick");
            if(textStatus==200)
            {
                normalAttendance=data.normalAttendance;
                unusualAttendance=data.unusualAttendance;
                totalAttendance=data.totalAttendance;
            }
            else
            {
                normalAttendance=0;
                unusualAttendance=0;
                totalAttendance=0;
            }
            normalAttendances.push(normalAttendance);
            unusualAttendances.push(unusualAttendance);
            totalAttendances.push(totalAttendance);
            $$("#sumDate").html(year+"年"+month+"月考勤情况");
            $$("#normalDays").html("("+normalAttendance+")");
            $$("#unusualDays").html("("+unusualAttendance+")");
            if(unusualAttendance!=0)
            {
                $$("#unusualDetail").addClass("close");
                $$("#unusualDetail").attr("onclick","showList()");
            }
        },
        error:function(){
            normalAttendance=0;
            unusualAttendance=0;
            totalAttendance=0;
            normalAttendances.push(normalAttendance);
            unusualAttendances.push(unusualAttendance);
            totalAttendances.push(totalAttendance);
            $$("#sumDate").html(year+"年"+month+"月考勤情况");
            $$("#normalDays").html("("+normalAttendance+")");
            $$("#unusualDays").html("("+unusualAttendance+")");
            $$("#unusualDetail").removeClass("close");
            $$("#unusualDetail").removeAttr("onclick");
        }
    });
}

/** 判断该月份是否在数组，不在统计该月考勤，在直接展示**/
function monthYearChange(month,year)
{
    if(checkMonthYearChange(month,year))
    {
        countMonthAttendances(month,year);
    }
}

/** 日历打开时候数据清空　**/
function openAttendance()
{
    monthChanges=[];
    normalAttendances=[];
    unusualAttendances=[];
    totalAttendances=[];
    normalAttendance=0;
    unusualAttendance=0;
    totalAttendance=0;
}

/** 今日考勤 **/
function searchToday()
{
    var today=new Date();
    var year=today.getFullYear();
    var month=today.getMonth()+1;
    var day=today.getDate();
    var eventDate=year+'-'+(month<10?'0'+month:month)+'-'+(day<10?'0'+day:day);
    $$.ajax({
        type: "get",
        url: "/jcds/mobile/attendance/searchAttendance?condition.eventDate="+eventDate,
        dataType: "json",
        success: function (data, textStatus) {
            $$("#todayDate").html((month<10?'0'+month:month)+'-'+(day<10?'0'+day:day));
            $$("#showDate").html((month<10?'0'+month:month)+'-'+(day<10?'0'+day:day));
            if(textStatus==200)
            {
                if(data!=null&&data!=undefined)
                {
                    if(data.showComeTime!=null&&data.showComeTime!=undefined&&data.showComeTime!="")
                    {
                        $$("#todayComeTime").html(data.showComeTime);
                        $$("#todayComeStatus").html(data.comeStatusString);
                        $$("#comeTime").html(data.showComeTime);
                    }
                    if(data.offStatus!=null&&data.offStatus!=undefined&&data.offStatus!=9)
                    {
                        $$("#todayOffTime").html(data.showOffTime);
                        $$("#todayOffStatus").html(data.offStatusString);
                        $$("#offTime").html(data.showOffTime);
                    }
                    if(data.comeStatus!=null&&data.comeStatus!=undefined&&data.comeStatus==1)
                    {
                        $$(".today-morning").addClass("unusual");
                        $$(".am").addClass("unusual");
                    }
                    if(data.offStatus!=null&&data.offStatus!=undefined&&data.offStatus==1)
                    {
                        $$(".today-afternoon").addClass("unusual");
                        $$(".pm").addClass("unusual");
                    }
                }
            }
        },
        error:function(data,textStatus){
            $$("#todayDate").html((month<10?'0'+month:month)+'-'+(day<10?'0'+day:day));
            $$("#showDate").html((month<10?'0'+month:month)+'-'+(day<10?'0'+day:day));
        }
    });
}

/** 点击该日考勤信息 **/
function searchAttendance(year,month,day)
{
    month=eval(month)+1;
    var eventDate=year+'-'+(month<10?'0'+month:month)+'-'+(day<10?'0'+day:day);
    $$.ajax({
        type: "get",
        url: "/jcds/mobile/attendance/searchAttendance?condition.eventDate="+eventDate,
        dataType: "json",
        success: function (data, textStatus) {
            if(textStatus==200)
            {
                $$("#showDate").html((month<10?'0'+month:month)+'-'+(day<10?'0'+day:day));
                $$(".am").removeClass("unusual");
                $$(".pm").removeClass("unusual");
                if(textStatus==200)
                {
                    if(data!=null&&data!=undefined)
                    {
                        if(data.showComeTime!=null&&data.showComeTime!=undefined&&data.showComeTime!="")
                        {
                            $$("#comeTime").html(data.showComeTime);
                        }
                        if(data.isTodayOff!=null&&data.isTodayOff!=undefined&&data.isTodayOff==1)
                        {
                            $$("#offTime").html(data.showOffTime);
                        }
                        else
                        {
                            if(data.offStatus!=null&&data.offStatus!=undefined&&data.offStatus!=9)
                            {
                                $$("#offTime").html(data.showOffTime);
                            }
                            else
                            {
                                $$("#offTime").html("--:--");
                            }
                        }
                        if(data.comeStatus!=null&&data.comeStatus!=undefined&&data.comeStatus==1)
                        {
                            $$(".am").addClass("unusual");
                        }
                        if(data.offStatus!=null&&data.offStatus!=undefined&&data.offStatus==1)
                        {
                            $$(".pm").addClass("unusual");
                        }
                    }
                }
            }
        },
        error:function(data,textStatus){
            $$("#showDate").html((month<10?'0'+month:month)+'-'+(day<10?'0'+day:day));
            $$(".am").removeClass("unusual");
            $$(".pm").removeClass("unusual");
            $$("#offTime").html("--:--");
            $$("#comeTime").html("--:--");
        }
    });
}

function showList() {
    var BodyH = document.body.clientHeight;
    var listStatus = $$("#abnormal-list").css('display');
    if(listStatus == 'none'){
        $$(".content-block-title").hide();
        $$(".content-block").hide();
        $$("#abnormal-list").css('height',(BodyH-160)+'px');
        $$(".summed-content li.abnormal").removeClass('close').addClass('open');
        $$("#abnormal-list").show();
        searchUnusualMonthAttendances();
    }else{
        $$(".content-block-title").show();
        $$(".content-block").show();
        $$(".summed-content li.abnormal").removeClass('open').addClass('close');
        $$("#abnormal-list").hide();
    }
}

/** 根据月份取出该月异常考勤情况并且展示**/
function searchUnusualMonthAttendances()
{
    var month=eval(showUnusualMonth)+1;
    var monthYear=showUnusualYear+'-'+(month<10?'0'+month:month);
    $$.ajax({
        type: "get",
        url: "/jcds/mobile/attendance/searchUnusualMonthAttendances?condition.eventDate="+monthYear,
        dataType: "json",
        success: function (data, textStatus) {
            $$("#unusualDetails").html("");
            if(textStatus==200)
            {
                var length = data.length;
                if(length>0)
                {
                    var innerHtml = '';
                    for (var i = 0; i < length; i++) {
                        var amInnerHtml='';
                        var pmInnerHtml='';
                        if(data[i].comeStatus!=null&&data[i].comeStatus!=undefined&&data[i].comeStatus==1)
                        {
                            amInnerHtml='<span class="time unusual">('+data[i].showComeTime+')</span>';
                        }
                        else
                        {
                            amInnerHtml='<span class="time">('+data[i].showComeTime+')</span>';
                        }
                        if(data[i].offStatus!=null&&data[i].offStatus!=undefined&&data[i].offStatus==1)
                        {
                            pmInnerHtml='<span class="time unusual">('+data[i].showOffTime+')</span>';
                        }
                        else if(data[i].offStatus!=null&&data[i].offStatus!=undefined&&data[i].offStatus==9)
                        {
                            pmInnerHtml='<span class="time">(--:--)</span>';
                        }
                        else
                        {
                            pmInnerHtml='<span class="time">('+data[i].showOffTime+')</span>';
                        }
                        innerHtml +='<li><div class="item-content"><div class="item-media"><i class="icon icon-clock"></i></div>'+
                                '<div class="item-inner"><div class="item-text">'+data[i].showEventDate+' / '+data[i].showWeekDay+'</div>'+
                                '<div class="item-title-row"><div class="item-title abnormal"><span class="text">上班时间</span>'+
                                amInnerHtml+'</div>'+
                                '<div class="item-title"><span class="text">下班时间</span>'+pmInnerHtml+
                                '</div></div></div></div></li>';
                    }
                    $$("#unusualDetails").append(innerHtml);
                }
            }
        }
    });
}
/**===========考勤管理模块结束============**/

/**===========访客管理开始============**/
/** 访客搜索 **/
function visitSelect(visitStatus,visitHtml)
{
    $$("#visitSelectStatus").val(visitStatus);
    $$("#visitSelectStatus").html(visitHtml);
    $$("#visitorSelectOption >ul >li").removeClass("selected");
    $$("#visitIcon"+visitStatus).addClass("selected");
    fillVisitorData();
}


/** 访客取得第一次数据信息 **/
function fillVisitorData() {
    $$('#visitorTotal').val(0);
    $$('#visitorId').val(0);
    $$('#startNum').val(0);
    $$('#visitStatus').val($$("#visitSelectStatus").val());
    $$('#visitor_div > ul').html("");
    $$.ajax({
        type: "post",
        url: "/jcds/mobile/visitor/list_visitor",
        dataType: "json",
        data:myApp.formToJSON('#visitor_form'),
        success: function(data,textStatus) {
            var length=data.length;
            if(length!=0)
            {
                var innerHtml = '';
                var visitorSearch=$$("#visitorSearch").val();
                var showVisitor=true;
                var visitStatus=$$('#visitStatus').val();
                if(visitorSearch!=null&&visitorSearch!="")
                {
                    if(visitorSearch!=data[0].visitorSearch)
                    {
                        showVisitor=false;
                    }
                }
                if(showVisitor)
                {
                    if(visitStatus!=null&&visitStatus!=undefined&&visitStatus=='0')
                    {
                        for (var i = 0; i < length; i++) {
                            innerHtml += '<li class="swipeout type'+data[i].visitReason+'" id="'+data[i].id+'">'+
                                '<div class="swipeout-content">'+
                                '<a href="" onclick="visitorView('+data[i].id+')" class="item-link item-content">'+
                                '<div class="item-media"><i class="icon icon'+data[i].visitStatus+'"><i class="icon icon-visitor"></i></i></div>'+
                                '<div class="item-inner">'+
                                '<div class="item-title-row">'+
                                '<div class="item-title">'+
                                '<span class="name">'+data[i].visitors+'</span><span class="total">（共'+data[i].expectVisitors+'人）</span></div>'+
                                '<div class="item-title"><span class="text">'+data[i].visitReasonName+'</span>'+
                                '</div></div>'+
                                '<div class="item-title-row">'+
                                '<div class="item-after">'+
                                '<span class="date">'+data[i].startVisit+'-'+data[i].endVisit+'</span>'+
                                '</div>'+
                                '<div class="item-after">'+
                                '<span class="days">'+data[i].visitDays+'天</span>'+
                                '</div></div></div>'+
                                '</a></div>'+
                                '<div class="swipeout-actions-right">'+
                                '<a href="#" class="edit" onclick="visitorEdit('+data[i].id+')">编辑</a>'+
                                '<a href="#" class="delete" onclick="visitorDelete('+data[i].id+')">删除</a>'+
                                '</div></li>';
                        }
                    }
                    else
                    {
                        for (var i = 0; i < length; i++) {
                            innerHtml += '<li class="type'+data[i].visitReason>+'"'+
                                '<a href="" onclick="visitorView('+data[i].id+')" class="item-link item-content">'+
                                '<div class="item-media"><i class="icon icon'+data[i].visitStatus+'"><i class="icon icon-visitor"></i></i></div>'+
                                '<div class="item-inner">'+
                                '<div class="item-title-row">'+
                                '<div class="item-title">'+
                                '<span class="name">'+data[i].visitors+'</span><span class="total">（共'+data[i].expectVisitors+'人）</span></div>'+
                                '<div class="item-title"><span class="text">'+data[i].visitReasonName+'</span>'+
                                '</div></div>'+
                                '<div class="item-title-row">'+
                                '<div class="item-after">'+
                                '<span class="date">'+data[i].startVisit+'-'+data[i].endVisit+'</span>'+
                                '</div>'+
                                '<div class="item-after">'+
                                '<span class="days">'+data[i].visitDays+'天</span>'+
                                '</div></div></div>'+
                                '</a>'+
                                '</li>';
                        }
                    }
                    $$('#visitor_div > ul').append(innerHtml);
                    $$('#visitorId').val(data[length-1].visitorId);
                    $$('#visitorTotal').val(data[length-1].visitorTotal);
                    $$('#startNum').val(data[length - 1].rw);
                    isVisitorLoading=false;
                    if(data[length-1].visitorTotal<11)
                    {
                        myApp.detachInfiniteScroll($$('#visitorPreloader'));
                        $$('#visitorPreloader').hide();
                        isVisitorInfinite =true;
                    }
                    else
                    {
                        initVisitorPreload();
                    }
                }
            }
            else {
                myApp.detachInfiniteScroll($$('#visitorPreloader'));
                $$('#visitorPreloader').hide();
                isVisitorInfinite=true;
            }
        }
    });
};

/** 已办判断是否出现加载更多按钮 **/
function initVisitorPreload()
{
    visitor_lastIndex = $$('#visitor_div > ul > li').length;
    visitor_total=$$('#visitorTotal').val();

    $$('#visitorContent').on('infinite',function(){
        if(isVisitorLoading){
            return;
        }
        isVisitorLoading=true;
        if(isVisitorInfinite)
        {
            myApp.attachInfiniteScroll($$('#visitorPreloader'));
            $$('#visitorPreloader').show();
            isVisitorInfinite=false
        }
        setTimeout(function () {
            if(visitor_lastIndex>=visitor_total)
            {
                myApp.detachInfiniteScroll($$('#visitorPreloader'));
                $$('#visitorPreloader').hide();
                isVisitorInfinite=true;
            }
            else
            {
                $$.ajax({
                    type: "post",
                    url: "/jcds/mobile/visitor/list_visitor",
                    dataType: "json",
                    data:myApp.formToJSON('#visitor_form'),
                    success: function (data, textStatus) {
                        //生成新条目的HTML
                        var length = data.length;
                        var innerHtml = '';
                        var visitStatus=$$('#visitStatus').val();
                        if(visitStatus!=null&&visitStatus!=undefined&&visitStatus=='0')
                        {
                            for (var i = 0; i < length; i++) {
                                innerHtml += '<li class="swipeout type'+data[i].visitReason+'" id="'+data[i].id+'">'+
                                    '<div class="swipeout-content">'+
                                    '<a href="" onclick="visitorView('+data[i].id+')" class="item-link item-content">'+
                                    '<div class="item-media"><i class="icon icon-visitor"></i></div>'+
                                    '<div class="item-inner">'+
                                    '<div class="item-title-row">'+
                                    '<div class="item-title">'+
                                    '<span class="name">'+data[i].visitors+'</span><span class="total">（共'+data[i].expectVisitors+'人）</span></div>'+
                                    '<div class="item-title"><span class="text">'+data[i].visitReasonName+'</span>'+
                                    '</div></div>'+
                                    '<div class="item-title-row">'+
                                    '<div class="item-after">'+
                                    '<span class="date">'+data[i].startVisit+'-'+data[i].endVisit+'</span>'+
                                    '</div>'+
                                    '<div class="item-after">'+
                                    '<span class="days">'+data[i].visitDays+'天</span>'+
                                    '</div></div></div>'+
                                    '</a></div>'+
                                    '<div class="swipeout-actions-right">'+
                                    '<a href="#" class="edit" onclick="visitorEdit('+data[i].id+')">编辑</a>'+
                                    '<a href="#" class="delete" onclick="visitorDelete('+data[i].id+')">删除</a>'+
                                    '</div></li>';
                            }
                        }
                        else
                        {
                            for (var i = 0; i < length; i++) {
                                innerHtml += '<li class="type'+data[i].visitReason>+'"'+
                                    '<a href="" onclick="visitorView('+data[i].id+')" class="item-link item-content">'+
                                    '<div class="item-media"><i class="icon icon-visitor"></i></div>'+
                                    '<div class="item-inner">'+
                                    '<div class="item-title-row">'+
                                    '<div class="item-title">'+
                                    '<span class="name">'+data[i].visitors+'</span><span class="total">（共'+data[i].expectVisitors+'人）</span></div>'+
                                    '<div class="item-title"><span class="text">'+data[i].visitReasonName+'</span>'+
                                    '</div></div>'+
                                    '<div class="item-title-row">'+
                                    '<div class="item-after">'+
                                    '<span class="date">'+data[i].startVisit+'-'+data[i].endVisit+'</span>'+
                                    '</div>'+
                                    '<div class="item-after">'+
                                    '<span class="days">'+data[i].visitDays+'天</span>'+
                                    '</div></div></div>'+
                                    '</a>'+
                                    '</li>';
                            }
                        }
                        $$('#visitorId').val(data[length - 1].id);
                        $$('#startNum').val(data[length - 1].rw);

                        // 添加新条目
                        $$('#visitor_div > ul').append(innerHtml);
                        // 更新最后加载的序号
                        visitor_lastIndex =$$('#visitor_div > ul > li').length;

                        isVisitorLoading=false;
                    }

                });
            }
        },1000)
    })
}

/**===========访客管理结束============**/

/**===========访客管理-预约登记开始============**/
/** 访客管理设置高度 **/
function setVisitorHeight()
{
    var bodyH= document.body.clientHeight;;
    var contentHeight=bodyH-44*2;
    var visitorContent=contentHeight+4;
    //设置content-block高度
    $$('#selectVisitorDataContent').css('height', contentHeight+'px');
    $$('#visitorContent').css('height', visitorContent+'px');
}

/** 访客管理来访时间 **/
function searchVisitTime(hasVisitTime){
    var today=new Date();
    if(hasVisitTime!=null&&hasVisitTime!=undefined&&hasVisitTime!="")
    {
        today=new Date(hasVisitTime);
    }
    var visitTime = myApp.picker({
        input: '#visitTime',
        rotateEffect:true,
        toolbarCloseText:'完成',
        value: [today.getFullYear(), ((today.getMonth()+1) < 10 ? '0' + (today.getMonth()+1) : (today.getMonth()+1)),
            (today.getDate() < 10 ? '0' + today.getDate() : today.getDate())],
        onChange:function(picker,values,displayValues){
            var daysInMonth=new Date(picker.value[0],picker.value[1]*1,0).getDate();
            if(values[2]>daysInMonth)
            {
                picker.cols[4].setValue(daysInMonth);
            }
        },
        formatValue: function (p, values, displayValues) {
            return values[0]+'-'+values[1]+'-'+values[2];
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
            }
        ]
    });
}

function searchVisitReason(hasVisitReason)
{
    var visitReason=0;
    if(hasVisitReason!=null&&hasVisitReason!=undefined&&hasVisitReason!="")
    {
        visitReason=hasVisitReason;
    }
    $$.ajax({
        type: "get",
        url: "/jcds/mobile/pick/searchVisitReason",
        dataType: "json",
        success: function (data, textStatus) {
            var visitReasonPicker = myApp.picker({
                input: '#visitReasonName',
                toolbarCloseText:'完成',
                value:visitReason,
                cols: [
                    {
                        textAlign: 'center',
                        values: eval(data.value),
                        displayValues:eval(data.displayValue)
                    }
                ],
                formatValue: function (p, values, displayValues) {
                    $$("#visitReason").val(values[0]);
                    $$("#visitReasonName").val(displayValues[0]);
                    return displayValues[0];
                }
            });
        }

    });
}

function visitSubmit(){

    var canSubmit=true;
    var visitorName=$$("#visitorName").val();
    var expectVisitors=$$("#expectVisitors").val();
    var visitTime=$$("#visitTime").val();
    var visitDays=$$("#visitDays").val();
    var visitReason=$$("#visitReason").val();

    var visitorPhone=$$("#visitorPhone").val();
    var visitCars=$$("#visitCars").val();
    var visitCarNos=$$("#visitCarNos").val();

    if(visitorName==null||visitorName==""||visitorName==undefined)
    {
        alertModal("请输入来访客人","visitorName");
        canSubmit=false;
        return;
    }
    else
    {
        if(visitorName.length>8)
        {
            alertModal("来访客人信息过长","visitorName");
            canSubmit=false;
            return;
        }
    }
    if(expectVisitors==null||expectVisitors==""||expectVisitors==undefined)
    {
        alertModal("请输入来访人数","expectVisitors");
        canSubmit=false;
        return;
    }
    else
    {
        if(!patternZzs.test(expectVisitors))
        {
            alertModal("来访人数为正整数","expectVisitors");
            canSubmit=false;
            return;
        }
    }
    if(visitTime==null||visitTime==""||visitTime==undefined)
    {
        alertModal("请选择来访时间","visitTime");
        canSubmit=false;
        return;
    }
    else
    {
        var curDate=(new Date).dateFormat("yyyy-MM-dd");
        if(visitTime<curDate)
        {
            alertModal("来访时间必须大于今日","visitTime");
            canSubmit=false;
            return;
        }
    }
    if(visitDays==null||visitDays==""||visitDays==undefined)
    {
        alertModal("请输入来访天数","visitDays");
        canSubmit=false;
        return;
    }
    else
    {
        if(!patternZzs.test(visitDays))
        {
            alertModal("来访天数为正整数","visitDays");
            canSubmit=false;
            return;
        }
    }
    if(visitReason==null||visitReason==""||visitReason==undefined)
    {
        alertModal("请输入来访事由","visitReason");
        canSubmit=false;
        return;
    }
    if(visitorPhone!=null&&visitorPhone!=""&&visitorPhone!=undefined)
    {
        if(!patternTel.test(visitorPhone))
        {
            alertModal("请输入正确电话格式","visitorPhone");
            canSubmit=false;
            return;
        }
    }
    if(visitCars!=null&&visitCars!=""&&visitCars!=undefined)
    {
        if(!patternZzs.test(visitCars))
        {
            alertModal("来访车辆数为正整数","visitCars");
            canSubmit=false;
            return;
        }
    }
    if(canSubmit)
    {
        $$.ajax({
            type: "post",
            url: "/jcds/mobile/visitor/save",
            dataType: "json",
            data:myApp.formToJSON('#visitor_save'),
            success: function (data, textStatus) {
                if(data.status=="success")
                {
                    var message=data.data;
                    if(message!=null&&message!=""&&message!=undefined)
                    {
                        visitor_message=message;
                    }
                    mainView.router.back({
                        url:"/jcds/mobile/visitor/list",
                        force:true,
                        pushState:false
                    });
                    mainView.router.refreshPage();
                }
                else {
                    var message=data.message;
                    if(message!=null&&message!=""&&message!=undefined)
                    {
                        alertModal(message);
                    }
                }
            }

        });
    }
}

function selectVisitorDate(visitorDate){
    $$("#visitorDate").val(visitorDate);
    myApp.closePanel('left');
    fillVisitorData();
}

function visitorView(visitId){
    if(visitId!=null&&visitId!=undefined&&visitId!=0)
    {
        mainView.router.loadPage("/jcds/mobile/visitor/view?id="+visitId);
    }
    else
    {
        alertModal("该访客登记记录有误");
    }
}

function visitorDelete(visitId){
    myApp.confirm('确定删除该条访客预约？',
        function () {
            $$.ajax({
                type: "post",
                url: "/jcds/mobile/visitor/delete?id="+visitId,
                dataType: "json",
                success: function (data, textStatus) {
                    var message="";
                    if(data.status=="success")
                    {
                        myApp.swipeoutDelete("#"+visitId);
                        message=data.data;
                    }
                    else {
                        myApp.swipeoutClose("#"+visitId)
                        message =data.message;

                    }
                    if(message!=null&&message!=""&&message!=undefined)
                    {
                        alertModal(message);
                    }
                }
            });
        },
        function () {
            myApp.swipeoutClose("#"+visitId)
        }
    );
}

function visitorEdit(visitId){
    myApp.confirm('确定修改该条访客预约？',
        function () {
            mainView.router.loadPage("/jcds/mobile/visitor/edit?id="+visitId);
            myApp.swipeoutClose("#"+visitId)
        },
        function () {
            myApp.swipeoutClose("#"+visitId)
        }
    );

}

/**===========访客管理-预约登记结束============**/

function alertModal(text,focusId){

    var modal=myApp.modal({
        text:text
    });

    $$(".modal-overlay").on("click", function (e) {
        myApp.closeModal(modal);
    });

    setTimeout(function () {
        myApp.closeModal(modal);
    },1500);

    if(focusId!=null&&focusId!=undefined&&focusId!="")
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
