var _iframe_layout_north_init_size = null;

/**
 * 表单折叠展开 默认高度 46
**/
function search_fold(obj){
	if(null == _iframe_layout_north_init_size){
		_iframe_layout_north_init_size = _iframe_layout.state.north.size;
	}
	var o = $(obj);
	if(o.hasClass("up")){
		_iframe_layout.sizePane("north",_iframe_layout_north_init_size);
		o.removeClass("up");
		o.html('<i class="fa fa-angle-double-up"></i>');
	}else{
		_iframe_layout.sizePane("north",46);
		o.addClass("up");
		o.html('<i class="fa fa-angle-double-down"></i>');
	}
}

/**
*
* Dialog 快捷方法
* 参数(id)：指定弹出框的id
* 参数(title)：指定弹出框的标题
* 参数(url)：指定弹出框加载的iframe地址
* 参数(width)：指定弹出框的宽度，可选
* 参数(height)：指定弹出框的高度，可选
* 参数(btn)：指定要恢复的按钮，可选
* 参数(reload)：Boolean，指定是否在弹出框弹出时刷新iframe页面，可选
**/
function quick_dialog(id,title,url,width,height,btn,reload){
	if(window != top.window){
		top.quick_dialog(id,title,url,width,height,btn);
		return;
	}

	if(btn){
		btn_running(btn);
	}
	var $el;
	if(id){
		$el = $("#" + id);
	}else{
		id = "dlg_" + new Date().getTime();
	}
	if(!$el || $el.length == 0){
		$el = $('<div id="' + id + '"></div>').appendTo('body');
	}

	var win = $("#" + id).data("kendoWindow");
	if(win){
		win.open();
		if(reload){
			win.refresh({
				iframe : true
			});
		}
		return;
	}
	var options = {
		title : title,
		content : url,
		//appendTo : top.window.document.body,
		iframe : true,
	    modal : true,
	    pinned : true,
	    actions : ["Maximize","Refresh","Close"],
	    activate : function(){
	    	$("#" + id).addClass("k-window-iframecontent-loading");
	    	iframe_loaded($("iframe","#" + id)[0],function(){
	    		$("#" + id).removeClass("k-window-iframecontent-loading");
	    	});
	    },
	    open : function(){
	    	this.center();
	    },
	    close : function(){
	    	if(btn){
	    		btn_enabled(btn);
	    	}
	    	this.destroy();
	    }
	};
	if(width){
		options.width = width;
		options.orginalWidth = width;
	}
	if(height){
		options.height = height;
	}

	$el.hcWindow(options);

}

/**
* 获取Dialog对象
**/
function get_dialog(id){
	if(window != top.window){
		return	top.get_dialog(id);
	}
	return $("#" + id).data("kendoWindow");
}

/**
* 获取Grid对象
**/
function get_grid(grid_el){
	return $(grid_el).data("kendoGrid");
}

/**
* 获取Dialog中的Grid对象
**/
function get_grid_in_dialog(dialog_id,grid_el){
	var dlg = get_dialog(dialog_id);
	if(!dlg)return null;
	var iframe=$("iframe",dlg.element[0]);
	if(!iframe)return null;
	return iframe[0].contentWindow.get_grid(grid_el);
}

/**
* 获取Dialog中的Element对象
**/
function get_el_in_dialog(dialog_id,el){
	var dlg = get_dialog(dialog_id);
	if(!dlg)return null;
	var iframe=$("iframe",dlg.element[0]);
	if(!iframe)return null;
	return iframe[0].contentWindow.get_el(el);
}

/**
* 把Dom对象封装成jQuery对象
**/
function get_el(el){
	return $(el);
}

/**
* 关闭Dialog同时销毁Dialog对象
**/
function close_dialog(id,destroy){
	if(id == undefined){
		//FIXME
		id = $(".k-window-iframecontent",top.window.document.body).attr("id");
	}
	if(window != top.window){
		top.close_dialog(id);
		return;
	}
	var win = $("#" + id).data("kendoWindow");
	if(destroy == undefined){
		destroy = true;
	}
	win.close();
	if(destroy){
		win.destroy();
		return;
	}
}

/**
* 加载iframe时的回调函数
**/
function iframe_loaded(iframe,callback){
	if (iframe.attachEvent){
    	iframe.attachEvent("onload", function(){
    		callback();
    	});

	} else {
   		iframe.onload = function(){
   			callback();
    	};
    }

}
/**
*
* Ajax 快捷方法
* 参数(url)：ajax请求地址
* 参数(data)：ajax传递到服务端的数据，可选
* 参数(success_callback)：ajax请求成功的回调函数，可选
* 参数(btn)：ajax请求完成之后恢复按钮，可选
* 参数(grid)：要刷新的Grid对象，可选
* 参数(type)：请求方式，POST或GET
* 参数(msg)：请求成功之后的消息提醒文案，默认 "操作成功！"
**/
function quick_ajax(url,data,success_callback,btn,grid,type,msg){
	type = type || "POST";
	if(btn){
		btn_running(btn);
	}
	$.ajax({
		url : url,
		type : type,
		data : data || {},
		success : function(res){
			if(res && res.status == "success"){
				notify(msg || res.message || "操作成功！","success");
				if(grid){
					grid.dataSource.read();
				}
				if(success_callback && $.isFunction(success_callback)){
					success_callback();
				}
			}else if(res && res.status == "error"){
				notify(res.message || "操作失败！","error");
			}
		},
		complete : function(){
			if(btn){
				btn_enabled(btn);
			}
		}
	});
}

/**
*
* TreeList 快捷方法
* 参数(el)：指定装载tree的容器
* 参数(formId)：表单的id，自动将指定表单中的数据传送到服务器，可选
* 参数(readUrl)：获取数据的 URL 地址
* 参数(columns)：TODO
* 参数(options)：tree 配置，可选
* 参数(dsOptions)：DataSource 配置，可选
* 参数(operates)：生成操作按钮，可选
* 参数(toolbarId)：工具条的id，可选
**/
function quick_treelist(el,formId,readUrl,columns,options,dsOptions,operates,toolbarId){
	options = options || {};
	toolbarId  = toolbarId || "#grid_toolbar_template";
	var _dbopt = {
		transport: {
			read: {
				url : readUrl,
				type : "POST",
				dataType: "json",
				cache: false,
				data : function(p){
					if(formId){
						var arr = $(formId).serializeArray();
						if(arr && arr.length > 0){
							var d = {};
							$.each(arr,function(idx,it){
								if(it.value) {
									d[it.name] = it.value;
								}
							});
							return d;
						}
					}
					return {};
				}
			} ,
            schema: {
                model: {
                    id: "id",
                    parentId: "pid"
                }
            }
        }
	};
	if(dsOptions){
		_dbopt = $.extend(_dbopt,dsOptions);
	}
	var _opt = {
		dataSource: new kendo.data.TreeListDataSource(_dbopt),
		toolbar: hc.template($(toolbarId).html().replace(/&lt;/g, '<').replace(/&gt;/g, '>')),
		height: "100%",
		columns : columns
	};
	if(options){
		_opt = $.extend(_opt,options);
	}
	$(el).hcTreeList(_opt);

	var $toolbar = $(".k-grid-toolbar .toolbar .grid_operates",el);
	if(operates && operates.length > 0){
		$.each(operates,function(idx,it){
			var $oper = $('<li><a href="javascript:void(0)">' + (it.icon ? '<i class="' + it.icon + '"></i>' : '') + it.name + '</a></li>');
			if(it.onclick && $.isFunction(it.onclick)){
				$("a",$oper).bind("click.toolbar",function(e){
					it.onclick(this,$(el).data("kendoTreeList"));
				});
			}
			$toolbar.append($oper);
		});
	}

	if(formId){
		$(formId).submit(function(event){
			var _this = this;
			var grid = $(el).data("kendoTreeList");
			btn_running($("*[type='submit']",_this));

			grid.dataSource.xquery({
			},function(){
				btn_enabled($("button[type='submit']",_this));
			});
			event.preventDefault();
			return false;
		});
	}
}

/**
*
* DataGrid 快捷方法
* 参数(el)：指定装载tree的容器
* 参数(formId)：表单的id，自动将指定表单中的数据传送到服务器，可选
* 参数(readUrl)：获取数据的 URL 地址
* 参数(columns)：TODO
* 参数(options)：tree 配置，可选
* 参数(dsOptions)：DataSource 配置，可选
* 参数(operates)：生成操作按钮，可选
* 参数(toolbarId)：工具条的id，可选
**/
function quick_datagrid(el,formId,readUrl,columns,options,dsOptions,operates,toolbarId){
	options = options || {};
	toolbarId  = toolbarId || "#grid_toolbar_template";
	var _dbopt = {
		transport: {
			read: {
				url : readUrl,
				type : "POST",
				dataType: "json",
				cache: false,
				data : function(p){
					if(formId){
						var arr = $(formId).serializeArray();
						if(arr && arr.length > 0){
							var d = {};
							$.each(arr,function(idx,it){
								if(it.value) {
									d[it.name] = it.value;
								}
							});
							return d;
						}
					}
					return {};
				}
			},
			parameterMap: function(data, type) {
				if (type == "read") {
					data.pageNo = data.page;
					data.page = undefined;
					data.take = undefined;
					data.skip = undefined;
					delete data.page;
					delete data.take;
					delete data.skip;
					return data;
				}
			}
		},
		schema  :{
			type : "json",
			data  : "rows",
			total : "total"
		},
		serverPaging: true,
		pageSize: options.pageSize || 20
	};
	if(dsOptions){
		_dbopt = $.extend(_dbopt,dsOptions);
	}
	var _opt = {
		dataSource: new kendo.data.DataSource(_dbopt),
		toolbar: hc.template($(toolbarId).html().replace(/&lt;/g, '<').replace(/&gt;/g, '>')),
		height: "100%",
		groupable: false,
		selectable : true,
		pageable: {
			refresh: false,
			pageSizes: [5,10,20,50],
			input : false,
			buttonCount: 5
		},
		columns : columns
	};
	if(options){
		_opt = $.extend(_opt,options);
	}
	$(el).hcGrid(_opt);

	var $toolbar = $(".k-grid-toolbar .toolbar .grid_operates",el);
	if(operates && operates.length > 0){
		$.each(operates,function(idx,it){
			var $oper = $('<li><a href="javascript:void(0)">' + (it.icon ? '<i class="' + it.icon + '"></i>' : '') + it.name + '</a></li>');
			if(it.onclick && $.isFunction(it.onclick)){
				$("a",$oper).bind("click.toolbar",function(e){
					it.onclick(this,$(el).data("kendoGrid"));
				});
			}
			$toolbar.append($oper);
		});
	}

	if(formId){
		$(formId).submit(function(event){
  			var _this = this;
  			var grid = $(el).data("kendoGrid");
  			btn_running($("*[type='submit']",_this));

  			grid.dataSource.xquery({
  				page:1,
  				pageSize : grid.dataSource.pageSize()
  			},function(){
  				btn_enabled($("button[type='submit']",_this));
  			});
  			event.preventDefault();
  			return false;
  		});
	}

}


/**
* Dialog中的form表单验证失败，重新设定dialog的宽度，居中定位
* 参数(formEl)：dialog中的表单id
* 参数(dialogId)：dialog对象的id
**/
function form_validation_dialog_event(formEl,dialogId){
	$(formEl).on("validation", function(e, current){
			var dlg = get_dialog(dialogId);
	    if(this.isValid === false){
	    	if(dlg.options.orginalWidth == dlg.options.width){
	    		dlg.setOptions({width:dlg.options.orginalWidth + 100});
	    		dlg.center();
	    	}
	    }else{
	    	dlg.setOptions({width:dlg.options.orginalWidth});
	    	dlg.center();
	    }
	})
}

/**
* Notify 信息提示封装
* 参数(message)：显示在提示框中的文本
* 参数(status)：信息提示框的状态，默认 "success"，如果状态是 "success" 或 "warn"，自动关闭
**/
function notify(message,status){
	if(window != top.window){
		top.notify(message,status);
		return;
	}
	$.notify(message, {
		globalPosition : 'top center',
		arrowShow : true,
		className : status || "success",
		autoHide : (status == "success" || status == "warn") ? true : false
	});
}

/**
* 表单提交的button组件
* 参数(button)：指定按钮元素
* 参数(el)：指定表单元素
* 参数(callback)：TODO
**/
function form_btn_submit(button, el, callback) {

	var btn = $(button);
	var form = $(el);

	var instance = btn.data("kendoButton");
	if (instance) {
		btn.data("oldValue", btn.html());
		btn.html("正在提交...");
		instance.enable(false);
	}
	form.ajaxSubmit({
		success : function(res) {
		if(typeof res=='string'  && res.constructor==String){
			var res=$.parseJSON(res);
		}
			if (res && res.status == "success") {
				if(callback && $.isFunction(callback)){
					callback(res);
				}
			} else {
				$.notify(res.message || "操作失败！", {
					globalPosition : 'top center',
					className : 'error',
					autoHide : false
				});
			}

		},
		complete : function() {
			if (instance) {
				btn.html(btn.data("oldValue"));
				instance.enable(true);
			}
		}
	});

}

/**
* 获取layout的center高度
**/
function _layout_center_height(lay) {
	return lay.state.center.innerHeight - 2;
}

/**
* 控制按钮的状态
**/
function btn_running(btn) {
	if (!btn)
		return;
		// var runningText = '<i class="fa fa-refresh fa-spin"></i> 运行中..';
		var runningText = '<i class="fa fa-refresh"></i> 运行中..';
		btn = $(btn)[0];
	if (btn.tagName.toUpperCase() == "A") {
		$(btn).addClass("disabled").data("text", $(btn).html()).html(runningText);
		var onclick = $(btn).attr("onclick");
		if (onclick) {
			$(btn).data("onclickevent", onclick);
			$(btn).removeAttr("onclick");
		}
	} else if (btn.tagName.toUpperCase() == "BUTTON") {
		var button = $(btn).data("kendoButton");
		if (button) {
			$(btn).data("text", $(btn).html()).html(runningText);
			button.enable(false);
		}
	}

}

/**
* 恢复按钮的状态
**/
function btn_enabled(btn) {
	if (!btn)
		return;
	btn = $(btn)[0];
	if (btn.tagName.toUpperCase() == "A") {
		$(btn).removeClass("disabled").html($(btn).data("text"));
		var onclick = $(btn).data("onclickevent");
		if (onclick) {
			$(btn).attr("onclick", onclick);
		}
	} else if (btn.tagName.toUpperCase() == "BUTTON") {
		var button = $(btn).data("kendoButton");
		if (button) {
			$(btn).html($(btn).data("text"));
			button.enable(true);
		}
	}
}

/**
* 日期格式化
**/
Date.prototype.format = function(format) {
	var date = {
		"M+": this.getMonth() + 1,
		"d+": this.getDate(),
		"H+": this.getHours(),
		"m+": this.getMinutes(),
		"s+": this.getSeconds(),
		"q+": Math.floor((this.getMonth() + 3) / 3),
		"S+": this.getMilliseconds()
	};
	if (/(y+)/i.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
	}
	for (var k in date) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1
				? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
		}
	}
	return format;
}

/* 初始化内容 */
$(function() {
	$(".hc_btn").hcButton();
	$(".ui-datetime").hcDateTimePicker({
		format: "yyyy-MM-dd HH:mm:ss"
	});
	$(".ui-combobox").hcComboBox();
	$("._current_date_time").val(new Date().format("yyyy-MM-dd HH:mm:ss"));
});

/* Cookie 存取 */
function createCookie(name,value,days) {
  if (days) {
    var date = new Date();
    date.setTime(date.getTime()+(days*24*60*60*1000));
    var expires = "; expires="+date.toGMTString();
  }
  else var expires = "";
  document.cookie = name+"="+value+expires+"; path=/";
}

/* Cookie 读取 */
function readCookie(name) {
  var nameEQ = name + "=";
  var ca = document.cookie.split(';');
  for(var i=0;i < ca.length;i++)
  {
    var c = ca[i];
    while (c.charAt(0)==' ') c = c.substring(1,c.length);
    if (c.indexOf(nameEQ) == 0){
      return c.substring(nameEQ.length,c.length);
    }
  }
  return null;
}
