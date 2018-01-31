// 当中间iframe页面只有tab页时，用来自适应高度
/*
	参数列表
	1.tabs，tab页实例对象.
	2.supSelect，tab父级可以用做layout容器的选择器,默认为"body".
	3.supGridSelect,grid元素的父级元素选择器，默认为".grid-wrap".
	4.formSelect,grid的搜索条件选择器，默认为".ui-form"
	5.fun_resize，高度变化时执行的函数.
*/
function tabsLayout(tabs,supSelect,supGridSelect,formSelect,fun_resize){
	if(!supSelect){
		supSelect = "body";
	}
	if(!supGridSelect){
		supGridSelect = ".grid-wrap";
	}
	if(!formSelect){
		formSelect = ".ui-form";
	}	
	var _layout = $(supSelect).layout({
		resizable: false,
        closable: false,
        spacing_open: 0,
		center__paneSelector:".k-tabstrip-wrapper",
		onresize_end:function(){
			_autoHeight(tabs,formSelect,_layout,supGridSelect);	
			if(fun_resize && $.isFunction(fun_resize)){
				fun_resize();
			}
		}
	});
	tabs.bind("activate",function(){
		_autoHeight(tabs,formSelect,_layout,supGridSelect);
	});
	_autoHeight(tabs,formSelect,_layout,supGridSelect);
}
function _autoHeight(tabs,formSelect,layout,supGrid){
	var index = $(tabs.select()).index();
	var $items = $(tabs.element[0]).find(".k-item");
	var $activeContent = $(tabs.contentElements[index]);
	var formHeight = $activeContent.find(formSelect).length >0?$activeContent.find(formSelect).height():0;
	var contHeight = layout.center.state.innerHeight-$items.height()-3;
	var gridHeight = contHeight - formHeight;
	$(supGrid).height(gridHeight);
	$activeContent.height(contHeight);
}

// 当中间iframe页面只有tab页且tab中有树菜单时，用来自适应高度
/*
	参数列表
	1.tabs，tab页实例对象.
	2.supSelect，tab父级可以用做layout容器的选择器,默认为"body".
	3.supGridSelect,grid元素的父级元素选择器，默认为".grid-wrap".
	4.formSelect,grid的搜索条件选择器，默认为".ui-form"
	5.fun_resize，高度变化时执行的函数.
*/
function tabsTreeLayout(tabs,supSelect,supGridSelect,formSelect,ztreeContSelect,fun_resize){
	if(!supSelect){
		supSelect = "body";
	}
	if(!supGridSelect){
		supGridSelect = ".grid-wrap";
	}
	if(!formSelect){
		formSelect = ".ui-form";
	}
	if(!ztreeContSelect){
		ztreeContSelect = ".ztree-cont";
	}	
	var _layout = $(supSelect).layout({
		resizable: false,
        closable: false,
        spacing_open: 0,
		center__paneSelector:".k-tabstrip-wrapper",
		onresize_end:function(){
			_autoTreeHeight(tabs,formSelect,_layout,supGridSelect,ztreeContSelect);	
			if(fun_resize && $.isFunction(fun_resize)){
				fun_resize();
			}
		}
	});
	tabs.bind("activate",function(){
		_autoTreeHeight(tabs,formSelect,_layout,supGridSelect,ztreeContSelect);
	});
	_autoTreeHeight(tabs,formSelect,_layout,supGridSelect,ztreeContSelect);
}
function _autoTreeHeight(tabs,formSelect,layout,supGrid,ztreeContSelect){
	var index = $(tabs.select()).index();
	var $items = $(tabs.element[0]).find(".k-item");
	var $activeContent = $(tabs.contentElements[index]);
	var $treeCont = $activeContent.find(ztreeContSelect);
	var $tree = $treeCont.find(".ztree");
	var $treebrother = $tree.siblings();
	var formHeight = $activeContent.find(formSelect).length >0?$activeContent.find(formSelect).height():0;
	var contHeight = layout.center.state.innerHeight-$items.height()-3;
	var contWidth = layout.center.state.innerWidth - $treeCont.width()-2;
	var gridHeight = contHeight - formHeight;
	$activeContent.height(contHeight);
	$(supGrid).height(gridHeight).width(contWidth);	
	$tree.height(function(){
		var height = contHeight;
		$.each($treebrother,function(i,v){
			height -= $(v).outerHeight();
		});
		return height -10;
	});
}

// 获取grid中有checkbox元素，并且选中的数据
/*
	参数列表:
	  1.grid的选择器
	  2.grid中checkbook的选择器
	返回
	  Array
*/
function getCheckedData(gridSelector,checkSelector){
	var grid = $(gridSelector).data("kendoGrid");
	var data = grid._data;
	var rows = $(grid.tbody).find("tr");
	var length = rows.length;
	var resData = [];
	for(var i=0;i<length;i++){
		if($(rows[i]).has(checkSelector+":checked").length>0){
			resData.push(data[i]);
		}
	}
	return resData;
}

//全选函数
/*
	参数列表:
	  1.控制全选/全不选的checkbook选择器
	  2.被控制的checkbook选择器
*/
function checkALL(checkAllSelector,checkSelector){
	$(checkAllSelector).change(function(){
		var isChecked = this.checked;
		$.each($(checkSelector),function(i,v){
			v.checked = isChecked;
		});
	});
}

;(function(){
	$.fn.grade = function(opts){
		var that = $(this);
		var defaults = {
			star:5
		};
		var opt = $.extend("",defaults,opts);
		var items = "";
		for(var i=1;i<=opt.star;i++){
			var item = "<li data-index='"+i+"'></li>";
			items +=item;
		}
		that.append(items);
		if(opt.currentGrade>0&&opt.currentGrade<=opt.star){
			var currentIndex = opt.currentGrade-1;
			that.find("li").eq(currentIndex).addClass("checked");
			that.find("li").eq(currentIndex).prevAll().addClass("checked");
			that.find("li").eq(currentIndex).nextAll().removeClass("checked");
		}
		that.bind({
			mouseleave:function(){
				that.find("li").removeClass("hl");
			}
		});
		that.find("li").bind({
			mouseenter:function(){
				$(this).addClass("hl");
				$(this).prevAll().addClass("hl");
				$(this).nextAll().removeClass("hl");
			},
			click:function(){
				$(this).addClass("checked");
				$(this).prevAll().addClass("checked");
				$(this).nextAll().removeClass("checked");
			}
		});
		that.getGrade = function(){
			var index = $(this).find("li.checked").length;
			return index;
		};

		return that;
	};
})();

function areaPosition(){
	var outWidth = $(this).width();
    	if(outWidth>1000){
    		var right = ($(this).width()-1000)/2;
    		$(".ui-co-area").css("right",right);
    	}  
}