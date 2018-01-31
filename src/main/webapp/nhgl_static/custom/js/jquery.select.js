/*
Ajax 三级联动
日期：2013-2-26

settings 参数说明
-----
firstUrl:一级下拉数据获取URL,josn返回
firstValue:默认一级下拉value
secondUrl:二级下拉数据获取URL,josn返回
secondValue:默认二级下拉value
thirdUrl:三级下拉数据获取URL,josn返回
thirdValue:默认三级下拉value
nodata:无数据状态
required:必选项
------------------------------ */
(function($){
	$.fn.linkSelect=function(settings){
		if($(this).size()<1){return;};
		// 默认值
		settings=$.extend({
			firstUrl:"js/city.min.js",
			firstValue:null,
			secondValue:null,
			thirdValue:null,
			nodata:null,
			required:true
		},settings);

		var box_obj=this;
		var first_obj=box_obj.find(".f");
		var second_obj=box_obj.find(".s");
		var third_obj=box_obj.find(".t");
		var select_prehtml=(settings.required) ? "" : "<option value=''>请选择</option>";

		var prepareSelectHtml=function(jsonArray){
			var temp_html=select_prehtml;
			$.each(jsonArray,function(index,row){
				temp_html+="<option value='"+row.value+"'>"+row.text+"</option>";
			});	
			return temp_html;
		};
		// 赋值二级下拉框函数
		var secondStart=function(){
			second_obj.empty().attr("disabled",true);
			third_obj.empty().attr("disabled",true);
            if(first_obj.val()==''){
            	return;
            }
			$.getJSON(settings.secondUrl, { firstValue: first_obj.val(), time: new Date().getTime() }, function(jsonResult){
				if(!jsonResult.success){
					if(settings.nodata=="none"){
						second_obj.css("display","none");
						third_obj.css("display","none");
					}else if(settings.nodata=="hidden"){
						second_obj.css("visibility","hidden");
						third_obj.css("visibility","hidden");
					};
					return;
				}
				// 遍历赋值二级下拉列表
				second_obj.html(prepareSelectHtml(jsonResult.data)).attr("disabled",false).css({"display":"","visibility":""});
				thirdStart();				
			});
			

		};

		// 赋值三级下拉框函数
		var thirdStart=function(){
			third_obj.empty().attr("disabled",true);

			$.getJSON(settings.thirdUrl, { firstValue: first_obj.val(),secondValue:second_obj.val(), time: new Date().getTime() }, function(jsonResult){
				if(!jsonResult.success){
					if(settings.nodata=="none"){
						third_obj.css("display","none");
					}else if(settings.nodata=="hidden"){
						third_obj.css("visibility","hidden");
					};
					return;
				}
				// 遍历赋值三级下拉列表
				third_obj.html(prepareSelectHtml(jsonResult.data)).attr("disabled",false).css({"display":"","visibility":""});
				thirdStart();				
			});
		};

		var init=function(){
			// 遍历赋值一级下拉列表
			$.getJSON(settings.firstUrl, {time: new Date().getTime() }, function(jsonResult){
				if(!jsonResult.success){
					return;
				}
				// 遍历赋值一级下拉列表
				first_obj.html(prepareSelectHtml(jsonResult.data));	
				secondStart();
				// 若有传入一级与二级的值，则选中。（setTimeout为兼容IE6而设置）
				setTimeout(function(){
					if(settings.firstValue && settings.firstValue.length>0){
						first_obj.val(settings.firstValue);
						secondStart();
						setTimeout(function(){
							if(settings.secondValue && settings.secondValue.length>0){
								second_obj.val(settings.secondValue);
								thirdStart();
								setTimeout(function(){
									if(settings.thirdValue && settings.thirdValue.length>0){
										third_obj.val(settings.thirdValue);
									};
								},1);
							};
						},1);
					};
				},1);
			});

			// 选择一级时发生事件
			first_obj.bind("change",function(){
				secondStart();
			});

			// 选择二级时发生事件
			second_obj.bind("change",function(){
				thirdStart();
			});
		};

		// 初始化第一个下拉框
		init();
	};
})(jQuery);