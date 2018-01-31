// Initialize your app
var myApp = new Framework7({
    modalTitle:'中控集成大厦',
    modalButtonOk:'确认',
    modalButtonCancel:'离开',
    animatePages:true,
    sortable:false,
    swipeBackPage:false,
    swipeBackPageAnimateShadow:false,
    swipeBackPageAnimateOpacity:false,
    cache:false
});

// Export selectors engine
var $$ = Dom7;

// Add main View
var mainView = myApp.addView('#view', {
});

// Now we need to run the code that will be executed only for About page.
// For this case we need to add event listener for "pageInit" event
initVisitor();

var isVisitorInfinite,isVisitorLoading,visitor_message,visitor_lastIndex,visitor_total;
var lastVisitorSearch;
var visitorSearchbar;
var patternZzs=/^[0-9]*[1-9][0-9]*$/;
//var patternTel=/((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/;
var patternTel= /(^[0-9]{3,4}\-[0-9]{7,8}$)|(^[0-9]{7,8}$)|(^\([0-9]{3,4}\)[0-9]{3,8}$)|(^0{0,1}13[0-9]{9}$)|(13\d{9}$)|(15[0135-9]\d{8}$)|(18[267]\d{8}$)/;

/** 页面加载初始化 **/
$$(document).on('pageInit', function (e) {
    // Do something here when page loaded and initialized

    var page = e.detail.page;

    if(page.name=="visitor")
    {
        initVisitor();
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

    if(page.name == 'visitor'){
        //显示工具栏
        showToolBar();
    }else {
        //隐藏工具栏
        hiddenToolBar();
        //$$(".view").css('padding-bottom',0);
    }
});

/**===========初始化方法开始============**/

/** 初始化访客预约页面 **/
function initVisitor()
{
    isVisitorInfinite=false;
    isVisitorLoading=false;
    lastVisitorSearch=undefined;
    $$("#visitSelectStatus").val('0');
    setVisitorHeight();
    fillVisitorData(false);

    visitorSearchbar=myApp.searchbar('#visitor_form',{
        customSearch:true
    });

    $$('#visitor_form').on('search input',function(e)
    {
        var visitorSearch=$$("#visitorSearch").val();
        if(visitorSearchbar.active&&(visitorSearch==undefined||visitorSearch.trim()==""))
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
        fillVisitorData(true);
    });
    if(visitor_message!=undefined&&visitor_message!=null&&visitor_message!="")
    {
        alertModal(visitor_message);
        visitor_message="";
    }

    $$("#visitorContent").on('refresh',function(e)
    {
        $$("#visitorContent").removeClass("content-refresh");
        setTimeout(function (e) {
            //if (visitorSearchbar.active){
            //    visitorSearchbar.disable();
            //}
            //else
            //{
            //    fillVisitorData();
            //}
            fillVisitorData(false);
            myApp.pullToRefreshDone();
            $$("#visitorContent").addClass("content-refresh");
        },2000);
    });

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
/**===========初始化方法结束============**/

/**===========访客管理开始============**/

/** 访客搜索 **/
function visitSelect(visitStatus,visitHtml)
{
    if($$("#visitIcon"+visitStatus).hasClass("selected"))
    {
        if (visitorSearchbar.active){
            visitorSearchbar.disable();
        }
    }
    else
    {
        $$("#visitSelectStatus").val(visitStatus);
        $$("#visitSelectStatus").html(visitHtml);
        $$("#visitorSelectOption >ul >li").removeClass("selected");
        $$("#visitIcon"+visitStatus).addClass("selected");
        if (visitorSearchbar.active){
            var visitorSearch=$$("#visitorSearch").val();
            if(visitorSearch==undefined||visitorSearch.trim()=="")
            {
                visitorSearchbar.disable();
                fillVisitorData(false);
            }
            else
            {
                visitorSearchbar.disable();
            }
        }else
        {
            fillVisitorData(false);
        }
    }
}

/** 访客取得第一次数据信息 **/
function fillVisitorData(isSearch) {
    if(!isSearch||lastVisitorSearch!=$$("#visitorSearch").val()) {
        if(isSearch)
        {
            lastVisitorSearch = $$("#visitorSearch").val();
        }
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
                    var visitStatus=$$('#visitStatus').val();
                    if(visitStatus!=undefined&&visitStatus!=null&&visitStatus=='0')
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
                            innerHtml += '<li class="type'+data[i].visitReason+'">'+
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
                            console.log(innerHtml);
                        }
                    }
                    $$('#visitor_div > ul').append(innerHtml);
                    $$('#visitorId').val(data[length-1].visitorId);
                    $$('#visitorTotal').val(data[length-1].visitorTotal);
                    $$('#startNum').val(data[length - 1].rw);
                    isVisitorLoading=false;
                    if(data[length-1].visitorTotal<21)
                    {
                        myApp.detachInfiniteScroll($$('#visitorContent'));
                        $$('#visitorPreloader').hide();
                    }
                    else
                    {
                        isVisitorInfinite =true;
                        initVisitorPreload();
                    }
                }
                else {
                    myApp.detachInfiniteScroll($$('#visitorContent'));
                    $$('#visitorPreloader').hide();
                }
            }
        });
    }
};

/** 已办判断是否出现加载更多按钮 **/
function initVisitorPreload()
{
    visitor_lastIndex = $$('#visitor_div > ul > li').length;
    visitor_total=$$('#visitorTotal').val();

    if(isVisitorInfinite)
    {
        myApp.attachInfiniteScroll($$('#visitorContent'));
        $$('#visitorPreloader').show();
        isVisitorInfinite=false
    }

    $$('#visitorContent').on('infinite',function(){
        if(isVisitorLoading){
            return;
        }
        isVisitorLoading=true;
        setTimeout(function () {
            if(visitor_lastIndex>=visitor_total)
            {
                myApp.detachInfiniteScroll($$('#visitorContent'));
                $$('#visitorPreloader').hide();
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
                        if(visitStatus!=undefined&&visitStatus!=null&&visitStatus=='0')
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
                                innerHtml += '<li class="type'+data[i].visitReason+'">'+
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
    var bodyH= document.body.clientHeight-49;
    var contentHeight=bodyH-44*2;
    var visitorContent=contentHeight+4;
    //设置content-block高度
    $$('#selectVisitorDataContent').css('height', contentHeight+'px');
    $$('#visitorContent').css('height', visitorContent+'px');
    $$('.searchbar-overlay').css('margin-top', '88px');
}

/** 访客管理来访时间 **/
function searchVisitTime(hasVisitTime){
    var today=new Date();
    if(hasVisitTime!=undefined&&hasVisitTime!=null&&hasVisitTime!="")
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
    if(hasVisitReason!=undefined&&hasVisitReason!=null&&hasVisitReason!="")
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

/** 提交访客信息 **/
function visitSubmit(){

    if(!$$("#visitor_submit").hasClass("disabled"))
    {
        $$("#visitor_submit").addClass("disabled");
    }
    var canSubmit=true;
    var visitorName=$$("#visitorName").val();
    var expectVisitors=$$("#expectVisitors").val();
    var visitTime=$$("#visitTime").val();
    var visitDays=$$("#visitDays").val();
    var visitReason=$$("#visitReason").val();
    var visitorPhone=$$("#visitorPhone").val();
    var visitCars=$$("#visitCars").val();
    var visitCarNos=$$("#visitCarNos").val();

    if(visitorName==undefined||visitorName==null||visitorName=="")
    {
        $$("#visitor_submit").removeClass("disabled");
        alertModal("请输入来访客人","visitorName");
        canSubmit=false;
        return;
    }
    else
    {
        if(visitorName.length>8)
        {
            $$("#visitor_submit").removeClass("disabled");
            alertModal("来访客人信息过长","visitorName");
            canSubmit=false;
            return;
        }
    }
    if(expectVisitors==undefined||expectVisitors==null||expectVisitors=="")
    {
        $$("#visitor_submit").removeClass("disabled");
        alertModal("请输入来访人数","expectVisitors");
        canSubmit=false;
        return;
    }
    else
    {
        if(!patternZzs.test(expectVisitors))
        {
            $$("#visitor_submit").removeClass("disabled");
            alertModal("来访人数为正整数","expectVisitors");
            canSubmit=false;
            return;
        }
    }
    if(visitTime==undefined||visitTime==null||visitTime=="")
    {
        $$("#visitor_submit").removeClass("disabled");
        alertModal("请选择来访时间","visitTime");
        canSubmit=false;
        return;
    }
    else
    {
        var curDate=(new Date).dateFormat("yyyy-MM-dd");
        if(visitTime<curDate)
        {
            $$("#visitor_submit").removeClass("disabled");
            alertModal("来访时间必须大于今日","visitTime");
            canSubmit=false;
            return;
        }
    }
    if(visitDays==undefined||visitDays==null||visitDays=="")
    {
        $$("#visitor_submit").removeClass("disabled");
        alertModal("请输入来访天数","visitDays");
        canSubmit=false;
        return;
    }
    else
    {
        if(!patternZzs.test(visitDays))
        {
            $$("#visitor_submit").removeClass("disabled");
            alertModal("来访天数为正整数","visitDays");
            canSubmit=false;
            return;
        }
    }
    if(visitReason==undefined||visitReason==null||visitReason=="")
    {
        $$("#visitor_submit").removeClass("disabled");
        alertModal("请输入来访事由","visitReason");
        canSubmit=false;
        return;
    }
    if(visitorPhone!=undefined&&visitorPhone!=null&&visitorPhone!="")
    {
        if(!patternTel.test(visitorPhone))
        {
            $$("#visitor_submit").removeClass("disabled");
            alertModal("请输入正确电话格式","visitorPhone");
            canSubmit=false;
            return;
        }
    }
    if(visitCars!=undefined&&visitCars!=null&&visitCars!="")
    {
        if(!patternZzs.test(visitCars))
        {
            $$("#visitor_submit").removeClass("disabled");
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
                    if(message!=undefined&&message!=null&&message!="")
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
                    $$("#visitor_submit").removeClass("disabled");
                    if(message!=undefined&&message!=null&&message!="")
                    {
                        alertModal(message);
                    }
                }
            }

        });
    }
}

/** 左侧时间选择查询 **/
function selectVisitorDate(visitorDate){
    if($$("#"+visitorDate).hasClass("selected"))
    {
        myApp.closePanel('left');
    }
    else
    {
        $$(".selected").removeClass("selected");
        $$("#"+visitorDate).addClass("selected");
        $$("#visitorDate").val(visitorDate);
        myApp.closePanel('left');
        fillVisitorData(false);
    }

}

/** 展示访客信息 **/
function visitorView(visitId){
    if(visitId!=undefined&&visitId!=null&&visitId!=0)
    {
        mainView.router.loadPage("/jcds/mobile/visitor/view?id="+visitId);
    }
    else
    {
        alertModal("该访客登记记录有误");
    }
}

/** 删除访客信息 **/
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
                    if(message!=undefined&&message!=null&&message!="")
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

/** 修改访客信息 **/
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
/** 冒泡信息　**/
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
