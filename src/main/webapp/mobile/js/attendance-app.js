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
setAttendanceHeight();
initAttendance();

var monthChanges;
var normalAttendances;
var unusualAttendances;
var totalAttendances;
var normalAttendance,unusualAttendance,totalAttendance;
var showUnusualMonth,showUnusualYear;

/** 页面加载初始化 **/
$$(document).on('pageInit', function (e) {
    // Do something here when page loaded and initialized

    var page = e.detail.page;

    if(page.name=="attendance")
    {
        setAttendanceHeight();
        initAttendance();
        //$$("#attendanceRefresh").on('refresh',function(e)
        //{
        //    setTimeout(function (e) {
        //        myApp.pullToRefreshDone();
        //    },2000);
        //});
    }
});

/** 判断是否隐藏toolbar **/
$$(document).on('pageBeforeAnimation', function (e) {
    // Do something here when page loaded and initialized
    var page = e.detail.page;

    if(page.name == 'attendance'){
        //显示工具栏
        showToolBar();
    }else {
        //隐藏工具栏
        hiddenToolBar();
        //$$(".view").css('padding-bottom',0);
    }
});

/**===========考勤管理模块开始============**/

/** 设置高度 **/
function setAttendanceHeight()
{
    var bodyH= document.body.clientHeight-49;
    $$('#attendance-content').css('height', bodyH+'px');
}

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
                if(data!=undefined&&data!=null)
                {
                    if(data.showComeTime!=undefined&&data.showComeTime!=null&&data.showComeTime!="")
                    {
                        $$("#todayComeTime").html(data.showComeTime);
                        $$("#todayComeStatus").html(data.comeStatusString);
                        $$("#comeTime").html(data.showComeTime);
                    }
                    if(data.offStatus!=undefined&&data.offStatus!=null&&data.offStatus!=9)
                    {
                        $$("#todayOffTime").html(data.showOffTime);
                        $$("#todayOffStatus").html(data.offStatusString);
                        $$("#offTime").html(data.showOffTime);
                    }
                    if(data.comeStatus!=undefined&&data.comeStatus!=null&&data.comeStatus==1)
                    {
                        $$(".today-morning").addClass("unusual");
                        $$(".am").addClass("unusual");
                    }
                    if(data.offStatus!=undefined&&data.offStatus!=null&&data.offStatus==1)
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
                    if(data!=undefined&&data!=null)
                    {
                        if(data.showComeTime!=undefined&&data.showComeTime!=null&&data.showComeTime!="")
                        {
                            $$("#comeTime").html(data.showComeTime);
                        }
                        if(data.isTodayOff!=undefined&&data.isTodayOff!=null&&data.isTodayOff==1)
                        {
                            $$("#offTime").html(data.showOffTime);
                        }
                        else
                        {
                            if(data.offStatus!=undefined&&data.offStatus!=null&&data.offStatus!=9)
                            {
                                $$("#offTime").html(data.showOffTime);
                            }
                            else
                            {
                                $$("#offTime").html("--:--");
                            }
                        }
                        if(data.comeStatus!=undefined&&data.comeStatus!=null&&data.comeStatus==1)
                        {
                            $$(".am").addClass("unusual");
                        }
                        if(data.offStatus!=undefined&&data.offStatus!=null&&data.offStatus==1)
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

/**展示该月详细考勤**/
function showList() {
    var BodyH = document.body.clientHeight;
    var listStatus = $$("#abnormal-list").css('display');
    if(listStatus == 'none'){
        $$(".content-block-title").hide();
        $$(".content-block").hide();
        $$("#abnormal-list").css('height',(BodyH-165)+'px');
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
                        if(data[i].comeStatus!=undefined&&data[i].comeStatus!=null&&data[i].comeStatus==1)
                        {
                            amInnerHtml='<span class="time unusual">('+data[i].showComeTime+')</span>';
                        }
                        else
                        {
                            amInnerHtml='<span class="time">('+data[i].showComeTime+')</span>';
                        }
                        if(data[i].offStatus!=undefined&&data[i].offStatus!=null&&data[i].offStatus==1)
                        {
                            pmInnerHtml='<span class="time unusual">('+data[i].showOffTime+')</span>';
                        }
                        else if(data[i].offStatus!=undefined&&data[i].offStatus!=null&&data[i].offStatus==9)
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
