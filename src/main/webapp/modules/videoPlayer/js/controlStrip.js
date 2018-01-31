// JavaScript Document
/* code by shenjiankang 2013/4/1 */

var isIE = (document.all) ? true : false;

var $$ = function (id) {
	return "string" == typeof id ? document.getElementById(id) : id;
};

var Class = {
	create: function() {
		return function() { this.initialize.apply(this, arguments); }
	}
}

var Extend = function(destination, source) {
	for (var property in source) {
		destination[property] = source[property];
	}
}

var Bind = function(object, fun) {
	return function() {
		return fun.apply(object, arguments);
	}
}

var BindAsEventListener = function(object, fun) {
	return function(event) {
		return fun.call(object, (event || window.event));
	}
}

var CurrentStyle = function(element){
	return element.currentStyle || document.defaultView.getComputedStyle(element, null);
}

function addEventHandler(oTarget, sEventType, fnHandler) {
	if (oTarget.addEventListener) {
		oTarget.addEventListener(sEventType, fnHandler, false);
	} else if (oTarget.attachEvent) {
		oTarget.attachEvent("on" + sEventType, fnHandler);
	} else {
		oTarget["on" + sEventType] = fnHandler;
	}
};

function removeEventHandler(oTarget, sEventType, fnHandler) {
    if (oTarget.removeEventListener) {
        oTarget.removeEventListener(sEventType, fnHandler, false);
    } else if (oTarget.detachEvent) {
        oTarget.detachEvent("on" + sEventType, fnHandler);
    } else { 
        oTarget["on" + sEventType] = null;
    }
};

//拖放程序
var Drag = Class.create();
Drag.prototype = {
  //拖放对象
  initialize: function(drag, options) {
	this.Drag = $$(drag);//拖放对象
	this._x = this._y = 0;//记录鼠标相对拖放对象的位置
	this._marginLeft = this._marginTop = 0;//记录margin
	//事件对象(用于绑定移除事件)
	this._fM = BindAsEventListener(this, this.Move);
	this._fS = Bind(this, this.Stop);
	
	this.SetOptions(options);
	
	this.Limit = !!this.options.Limit;
	this.mxLeft = parseInt(this.options.mxLeft);
	this.mxRight = parseInt(this.options.mxRight);
	this.mxTop = parseInt(this.options.mxTop);
	this.mxBottom = parseInt(this.options.mxBottom);
	
	this.LockX = !!this.options.LockX;
	this.LockY = !!this.options.LockY;
	this.Lock = !!this.options.Lock;
	
	this.onStart = this.options.onStart;
	this.onMove = this.options.onMove;
	this.onStop = this.options.onStop;
	
	this._Handle = $$(this.options.Handle) || this.Drag;
	this._mxContainer = $$(this.options.mxContainer) || null;
	this._axisNow = $$(this.options.axisNow) || null;
	
	this.Drag.style.position = "absolute";
	//透明
	if(isIE && !!this.options.Transparent){
		//填充拖放对象
		with(this._Handle.appendChild(document.createElement("div")).style){
			width = height = "100%"; backgroundColor = "#fff"; filter = "alpha(opacity:0)"; fontSize = 0;
		}
	}
	//修正范围
	this.Repair();
	addEventHandler(this._Handle, "mousedown", BindAsEventListener(this, this.Start));
  },
  //设置默认属性
  SetOptions: function(options) {
	this.options = {//默认值
		Handle:			"",//设置触发对象（不设置则使用拖放对象）
		Limit:			false,//是否设置范围限制(为true时下面参数有用,可以是负数)
		mxLeft:			0,//左边限制
		mxRight:		9999,//右边限制
		mxTop:			0,//上边限制
		mxBottom:		9999,//下边限制
		mxContainer:	"",//指定限制在容器内
		LockX:			false,//是否锁定水平方向拖放
		LockY:			false,//是否锁定垂直方向拖放
		Lock:			false,//是否锁定
		Transparent:	false,//是否透明
		onStart:		function(){},//开始移动时执行
		onMove:			function(){},//移动时执行
		onStop:			function(){}//结束移动时执行
	};
	Extend(this.options, options || {});
  },
  //准备拖动
  Start: function(oEvent) {
	if(this.Lock){ return; }
	this.Repair();
	//记录鼠标相对拖放对象的位置
	this._x = oEvent.clientX - this.Drag.offsetLeft;
	this._y = oEvent.clientY - this.Drag.offsetTop;
	//记录margin
	this._marginLeft = parseInt(CurrentStyle(this.Drag).marginLeft) || 0;
	this._marginTop = parseInt(CurrentStyle(this.Drag).marginTop) || 0;
	//mousemove时移动 mouseup时停止
	addEventHandler(document, "mousemove", this._fM);
	addEventHandler(document, "mouseup", this._fS);
	if(isIE){
		//焦点丢失
		addEventHandler(this._Handle, "losecapture", this._fS);
		//设置鼠标捕获
		this._Handle.setCapture();
	}else{
		//焦点丢失
		addEventHandler(window, "blur", this._fS);

		//阻止默认动作
		oEvent.preventDefault();
	};
	//附加程序
	this.onStart();
  },
  //修正范围
  Repair: function() {
	if(this.Limit){
		//修正错误范围参数
		this.mxRight = Math.max(this.mxRight, this.mxLeft + this.Drag.offsetWidth);
		this.mxBottom = Math.max(this.mxBottom, this.mxTop + this.Drag.offsetHeight);
		//如果有容器必须设置position为relative或absolute来相对或绝对定位，并在获取offset之前设置
		!this._mxContainer || CurrentStyle(this._mxContainer).position == "relative" || CurrentStyle(this._mxContainer).position == "absolute" || (this._mxContainer.style.position = "relative");
	}
  },
  //拖动
  Move: function(oEvent) {
	//判断是否锁定
	if(this.Lock){ this.Stop(); return; };
	//清除选择
	window.getSelection ? window.getSelection().removeAllRanges() : document.selection.empty();
	//设置移动参数
	var iLeft = oEvent.clientX - this._x, iTop = oEvent.clientY - this._y;
	//设置范围限制
	if(this.Limit){
		//设置范围参数
		var mxLeft = this.mxLeft, mxRight = this.mxRight, mxTop = this.mxTop, mxBottom = this.mxBottom;
		//如果设置了容器，再修正范围参数
		if(!!this._mxContainer){
			mxLeft = Math.max(mxLeft, 0);
			mxTop = Math.max(mxTop, 0);
			mxRight = Math.min(mxRight, this._mxContainer.clientWidth);
			mxBottom = Math.min(mxBottom, this._mxContainer.clientHeight);
		};
		//修正移动参数
		iLeft = Math.max(Math.min(iLeft, mxRight - this.Drag.offsetWidth), mxLeft);
		iTop = Math.max(Math.min(iTop, mxBottom - this.Drag.offsetHeight), mxTop);
	}
	//设置位置，并修正margin
	if(!this.LockX){ this.Drag.style.left = iLeft - this._marginLeft + "px"; }
	if(!this.LockY){ this.Drag.style.top = iTop - this._marginTop + "px"; }
	
	//设置已拖动的长度
	this._axisNow.style.width = this.Drag.offsetLeft+"px";
	//附加程序
	this.onMove();
  },
  //停止拖动
  Stop: function() {
	//移除事件
	removeEventHandler(document, "mousemove", this._fM);
	removeEventHandler(document, "mouseup", this._fS);
	if(isIE){
		removeEventHandler(this._Handle, "losecapture", this._fS);
		this._Handle.releaseCapture();
	}else{
		removeEventHandler(window, "blur", this._fS);
	};
	//附加程序
	this.onStop();
  }
};
//获取某元素以浏览器左上角为原点的坐标
function getPoint(obj) { 
	var l = obj.offsetLeft; //对应父容器的上边距
	//判断是否有父容器，如果存在则累加其边距
	while (obj = obj.offsetParent) {
		l += obj.offsetLeft; //叠加父容器的左边距
	}
	return l;
}
//点击控制条时，将滑块定位到鼠标当前位置
function locate(idDrag,idAxis,idAxisNow){
	var drag = $$(idDrag);
	var axis = $$(idAxis);
	var axisNow = $$(idAxisNow);
	var dragX = getPoint(drag);
	axis.onmousedown = function(oEvent){
	oEvent= oEvent || window.event;
	var x = oEvent.clientX;	
	var now_x = x - dragX;
	var max_x = axis.offsetWidth - drag.offsetWidth;
	if(now_x>max_x){
		now_x = max_x;
		}
	drag.style.left = now_x+"px";
	axisNow.style.width = now_x+"px";
	}
 }
//控制条减少函数
function reduce(idDrag,idAxis,idAxisNow){
	var drag = $$(idDrag);
	var axis = $$(idAxis);
	var axisNow = $$(idAxisNow);
	var now_x = drag.offsetLeft;
	var max_x = axis.offsetWidth - drag.offsetWidth;
	var new_x = now_x - 0.1*max_x;
		if(new_x<0){
			drag.style.left = 0+"px";
			axisNow.style.width = 0+"px";
			}else{
				drag.style.left = new_x+"px";
				axisNow.style.width = new_x+"px";
				}	
	}
//控制条增加函数
function plus(idDrag,idAxis,idAxisNow){
	var drag = $$(idDrag);
	var axis = $$(idAxis);
	var axisNow = $$(idAxisNow);
	var now_x = drag.offsetLeft;
	var max_x = axis.offsetWidth - drag.offsetWidth;
	var new_x = now_x+0.1*max_x;
		if(new_x>max_x){
			new_x = max_x;
			}
	drag.style.left = new_x+"px";
	axisNow.style.width = new_x+"px";
	}
//获得当前控制条所在位置占总长度的百分比，以滑块左侧位置为准
function getNow(idDrag,idAxisNow,idAxis){
	var drag = $$(idDrag);
	//控制条总长度 = 控制条实际长度 - 滑块宽度
	var axis_w = $$(idAxis).offsetWidth- drag.offsetWidth;
	var axisNow_w = $$(idAxisNow).offsetWidth;
	return axisNow_w/axis_w;
	}