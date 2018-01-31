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
initMine();

var change_message;
/** 页面加载初始化 **/
$$(document).on('pageInit', function (e) {
    // Do something here when page loaded and initialized

    var page = e.detail.page;
    
    if(page.name=="mine")
    {
        initMine();
    }
    if(page.name=="config")
    {
        initConfig();
    }
});

/** 判断是否隐藏toolbar **/
$$(document).on('pageBeforeAnimation', function (e) {
    // Do something here when page loaded and initialized
    var page = e.detail.page;

    if(page.name == 'mine'){
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

/** 初始化我的页面 **/
function initMine()
{
    setMineHeight();
    setHeadPic("headImg");
}

/**初始化设置页面 **/
function initConfig()
{
    setHeadPic("photo");
    if(change_message!=undefined&&change_message!=null&&change_message!="")
    {
        alertModal(change_message);
        change_message="";
    }
}

/**===========初始化方法结束============**/

/**===========我的开始============**/
/** 我的设置高度 **/
function setMineHeight()
{
    var bodyH= document.body.clientHeight-49;
    var contentHeight=bodyH-44;
    //设置content-block高度
    $$('#mineContent').css('height', contentHeight+'px');
}

function setHeadPic(imageId)
{
    $$.ajax({
        type: "post",
        url: "/jcds/mobile/mine/getHeadPic",
        dataType: "json",
        success: function (data, textStatus) {
            if(textStatus==200)
            {
                var headPic=data;
                if(headPic!=undefined&&headPic!=null&&headPic!="")
                {
                    $$("#"+imageId).attr("src",headPic);
                }
            }
        }

    });
}
/**===========我的结束============**/

/**===========更换头像开始============**/
function changeHeadPic()
{
    var type="1";//0代表不截图，1代表截图
    var widthScale="1";//宽的比例
    var heightScale="1";//高的比例
    var address="uploadImg";//地址方法
    var uploadPhoto=type+","+widthScale+","+heightScale+","+address;
    console.log(uploadPhoto);
    supcon.chooseImage(
        function (response) {
            if(typeof response == "string"){
                response = JSON.parse(response);
            }
            var headPic=response.imagePath;
            if(headPic!=undefined&&headPic!=null)
            {
                $$("#photo").attr("src",headPic);
            }
        },uploadPhoto
    )
}
/**===========更换头像结束============**/

/**===========修改密码开始==========**/

function changePassword()
{
    if(!$$("#change_submit").hasClass("disabled"))
    {
        $$("#change_submit").addClass("disabled");
    }
    var oldPass=$$("#oldPass").val();
    var newPass=$$("#newPass").val();
    var repeatPass=$$("#repeatPass").val();
    var canSubmit=true;
    if(oldPass==undefined||oldPass=="")
    {
        $$("#change_submit").removeClass("disabled");
        alertModal("请输入旧密码","oldPass");
        canSubmit=false;
        return;
    }
    if(newPass==undefined||newPass=="")
    {
        $$("#change_submit").removeClass("disabled");
        alertModal("请输入新密码","newPass");
        canSubmit=false;
        return;
    }
    if(repeatPass==undefined||repeatPass=="")
    {
        $$("#change_submit").removeClass("disabled");
        alertModal("请确认新密码","repeatPass");
        canSubmit=false;
        return;
    }
    if(newPass.length<6)
    {
        $$("#change_submit").removeClass("disabled");
        alertModal("新密码不能少于6位","newPass");
        canSubmit=false;
        return;
    }
    if(newPass!=repeatPass)
    {
        $$("#change_submit").removeClass("disabled");
        alertModal("请确认两次输入新密码一致","newPass");
        canSubmit=false;
        return;
    }
    if(canSubmit)
    {
        $$.ajax({
            type: "post",
            url: "/jcds/mobile/mine/changePass",
            dataType: "json",
            data:myApp.formToJSON('#change_password'),
            success: function (data, textStatus) {
                if(data.status=="success")
                {
                    var message=data.data;
                    if(message!=undefined&&message!=null&&message!="")
                    {
                        change_message=message;
                    }
                    mainView.router.back({
                        url:"/jcds/mobile/mine/config",
                        force:true,
                        pushState:false
                    });
                    mainView.router.refreshPage();
                }
                else {
                    var message=data.message;
                    $$("#change_submit").removeClass("disabled");
                    if(message!=undefined&&message!=null&&message!="")
                    {
                        alertModal(message);
                    }
                }
            }

        });
    }
}

/**===========修改密码结束==========**/
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

function CreatePhoto(type,widthScale,heightScale,address)
{
    var uploadPhoto= new Object;
    uploadPhoto.type = type;//0代表原图；1代表裁剪
    uploadPhoto.widthScale= widthScale;//裁剪比例
    uploadPhoto.heightScale = heightScale;
    uploadPhoto.address=address;//上传接口方法
    return uploadPhoto;
}
//function loadToolBarUrl(url)
//{
//    alertModal(url);
//    mainView.router.loadPage(url);
//}
