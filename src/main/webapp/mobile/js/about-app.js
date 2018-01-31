// Initialize your app
var myApp = new Framework7({
    modalTitle:'中控集成大厦',
    modalButtonOk:'确认',
    modalButtonCancel:'离开',
    animatePages:true,
    swipeout:false,
    sortable:false,
    swipeBackPage:false
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

/** 页面加载初始化 **/
$$(document).on('pageInit', function (e) {
    // Do something here when page loaded and initialized

    var page = e.detail.page;

    if(page.name=="test")
    {

    }
});

/** 判断是否隐藏toolbar **/
$$(document).on('pageBeforeAnimation', function (e) {
    // Do something here when page loaded and initialized
    var page = e.detail.page;

    if(page.name == 'about'){
        //显示工具栏
        showToolBar();
    }else {
        //隐藏工具栏
        //myApp.hideToolbar('.toolbar');
        hiddenToolBar();
        //$$(".view").css('padding-bottom',0);
    }
});

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
