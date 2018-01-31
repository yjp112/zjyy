/*
Ajax 三级联动
日期：2013-2-26

settings 参数说明
-----
buildingUrl:大楼下拉数据获取URL,josn返回
buildingValue:默认大楼下拉value
floorUrl:楼层数据获取URL,josn返回
floorValue:默认楼层value
nodata:无数据状态
required:必选项
clickCallback:点击时的回调函数
------------------------------ */
(function($){
	$.fn.building=function(settings){
		if($(this).size()<1){return;};
		// 默认值
		settings=$.extend({
			buildingUrl:"js/city.min.js",
			floorUrl:"js/city.min.js",
			buildingValue:null,
			floorValue:null,
            floorId:null,
			nodata:null,
			required:true,
			clickCallback:function(){}
		},settings);

		var box_obj=this;
		var building_obj=box_obj.find(".building");
		var floor_obj=box_obj.find(".choose_floor");
		var floorHidden_obj=box_obj.find(".choose_floor_hidden");
		var floorPanel_obj=box_obj.find("#floorNum");
		var select_prehtml=(settings.required) ? "" : "<option value=''>请选择</option>";

		var prepareSelectHtml=function(jsonArray){
			var temp_html=select_prehtml;
			$.each(jsonArray,function(index,row){
				temp_html+="<option value='"+row.value+"'>"+row.text+"</option>";
			});	
			return temp_html;
		};
		var prepareFloorPanelHtml=function(jsonArray){
			var temp_html='<table id="floor_table" cellpadding="0" cellspacing="0">';
			var count=0;
			$.each(jsonArray,function(index,row){
				if(count==0){
					temp_html+='<tr>';
				}
				var otherAttr="";
				if(row.other){
					otherAttr="other="+row.other+"";
				}
				temp_html+='<td '+otherAttr+' floorId='+row.value+'>'+row.text+'</td>';
				if(count>0&&count%3==0){
					temp_html+='</tr>';
					count=-1;
				}
				count=count+1;
			});	
			temp_html+='</table>';
			return temp_html;
		};
		// 赋值二级下拉框函数
		var createFloorPanel=function(){
			floor_obj.val('');
			floorHidden_obj.val('');
			//floorPanel_obj.empty();
            if(building_obj.val()==''){
            	return;
            }
			$.getJSON(settings.floorUrl, { buildingId: building_obj.val(), time: new Date().getTime() }, function(jsonResult){
				if(!jsonResult.success){
					if(settings.nodata=="none"){
						floorPanel_obj.css("display","none");
					}else if(settings.nodata=="hidden"){
						floorPanel_obj.css("visibility","hidden");
					};
					return;
				}
				// 遍历赋值二级下拉列表
				floorPanel_obj.html(prepareFloorPanelHtml(jsonResult.data));
				floorPanel_obj.find('td').click(function(){
					//hide
					var text = $(this).html();
					var value = $(this).attr("floorId");
					var other =$(this).attr("other");
					floor_obj.val(text);
					floorHidden_obj.val(value);
					floorPanel_obj.css("display","none");
					settings.clickCallback(value,text,other);
				});
				/*$('body').filter('.choose_floor').click(function(){
					alert(1)
					floorPanel_obj.css("display","none");
				});	*/
			});
			

		};


		var init=function(){
			// 遍历赋值一级下拉列表
			$.getJSON(settings.buildingUrl, {time: new Date().getTime() }, function(jsonResult){
				if(!jsonResult.success){
					return;
				}
				// 遍历赋值一级下拉列表
				building_obj.html(prepareSelectHtml(jsonResult.data));	
				createFloorPanel();
				// 若有传入大楼与楼层的值，则选中。（setTimeout为兼容IE6而设置）
				setTimeout(function(){
					if(settings.buildingValue && settings.buildingValue.length>0){
						building_obj.val(settings.buildingValue);
						createFloorPanel();
						setTimeout(function(){
							if(settings.floorValue!=null){
                                //$("#floor_table").find("td[floorId='"+settings.floorValue+"']").click();
                                //alert($("#floor_table").find("td[floorId=\""+settings.floorValue+"\"]").html());
								floor_obj.val(settings.floorValue);
                                floorHidden_obj.val(settings.floorId);
                               // alert($("#floor_table > td[floorId=\"3\"]").attr("other"));
                               // $("#floor_table > td[floorId="+settings.floorValue+"]").click();



							};
						},1);
					};
				},1);
			});

			// 选择一级时发生事件
			building_obj.bind("change",function(){
				createFloorPanel();
			});

			floor_obj.click(function(){
				//show
				//alert(floorPanel_obj.html())
				//floorPanel_obj.css("height","100px");
				//floorPanel_obj.css("width","100px");
				//floorPanel_obj.css('floorNum');
				floorPanel_obj.css("display","block");
			});
		};

		// 初始化第一个下拉框
		init();
	};
})(jQuery);