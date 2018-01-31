	//tab切换
	var resizeTimer;
  function tab(tab,center,on){
	  $("#"+tab+" li").first().addClass(on).siblings().removeClass(on);
	  $("#"+center+" > div").first().show().siblings().hide();
	  $("#"+tab+" li").click(function(){
		  var index = $("#"+tab+" li").index(this);
		  $(this).addClass(on).siblings().removeClass(on);
		  $("#"+center+" > div").eq(index).show().siblings().hide();
	  });
  }
  
  function setsibling(obj,on){
	  $(""+obj+"").each(function(i){
		  $(this).click(function(){
			  $(this).addClass(on).siblings().removeClass(on);
		  });
	  });
  }
	
	function initGisIndex(){
		var addWt = 0;
		var leftDiv=$("#G_MapMenu").width();
		if($("#G_MapMenu").length==0  || $("#G_MapMenu").css("display")=="none"){//设备查询不显示
			addWt=leftDiv;
			$("#G_Map").css("left","0px");
		}
		var rootEl=document.compatMode=="CSS1Compat"?document.documentElement:document.body;
		var rootHd=parseInt(rootEl.clientHeight);
		var rootWt = parseInt(rootEl.clientWidth);
		$("#G_MapMuneCon").height(rootHd - 27);
		$("#G_ItemListsBox").height(rootHd - 27 -124);
		$(".ui-floor").height(rootHd - 27 -124);
		$("#G_Map").height(rootHd - 27);
		$("#G_Map").width(rootWt - leftDiv + 20 + addWt);
		 
		$(window).resize(function(){
			var addWt = 0;
			var leftDiv=$("#G_MapMenu").width();
			if($("#G_MapMenu").length>0  && $("#G_MapMenu").css("display")=="none"){//设备查询不显示
				addWt=leftDiv;
				$("#G_Map").css("left","0px");
			}
			var rootEl=document.compatMode=="CSS1Compat"?document.documentElement:document.body;
			var rootHd=parseInt(rootEl.clientHeight);
			var rootWt = parseInt(rootEl.clientWidth);
			$("#G_MapMuneCon").height(rootHd - 27);
			$("#G_ItemListsBox").height(rootHd - 27 -124);
			$(".ui-floor").height(rootHd - 27 -124);
			$("#G_Map").height(rootHd - 27);
			$("#G_Map").width(rootWt - leftDiv + 20  + addWt);
		});
		tab("G_Tabs","G_MapMuneCon","on");
		$(".toggle_menu_btn").click(function(){
			var left =$("#G_MapMenu").width();			
			if($(".toggle_menu").offset().left>0){
				clearTimeout(resizeTimer);
				$(this).attr("title","展开");
				$(".toggle_menu").animate({left:0,opacity:"fast"});
				$("#gisbar").animate({left:30,opacity:"fast"});
				$("#G_MapMenu").animate({left:-left,opacity:"fast"});
				$("#G_Map").animate({left:0, width:parseInt($("#G_Map").width())+left, opacity:"fast"},function(){/*g_map.updateSize()*/});
				$(".toggle_menu_btn").css("background-position","-24px -2px");
			}
			else{
				clearTimeout(resizeTimer);
				$(this).attr("title","收起");
				$(".toggle_menu").animate({left:left,opacity:"fast"});
				$("#gisbar").animate({left:left+30,opacity:"fast"});
				$("#G_MapMenu").animate({left:0,opacity:"fast"});
				//console.log(parseInt($("#G_Map").width())-left);
				$("#G_Map").animate({left:left, width:parseInt($("#G_Map").width())-left, opacity:"fast"},function(){/*g_map.updateSize()*/});
				$(".toggle_menu_btn").css("background-position","0px -2px");
			}
		});

		$("#G_MapToolBtn").click(function(){
			if($("#G_MapToolList").css("display")=="none"){
				$("#G_MapToolList").css({"top":"27px", "left":($("#G_MapToolBtn").offset().left)+"px"});
				$("#G_MapToolList").show();
			}else{
				$("#G_MapToolList").hide();
			}
		});
		$(".G_MapregionBtn").click(function(){
			if($("#G_MapToolregion").css("display")=="none"){
				$("#G_MapToolregion").css({"top":"27px", "left":($(this).offset().left)+"px"});
				$("#G_MapToolregion").show();
                circleNow =$(this).attr("tag");
			}else if($(this).offset().left!=  $("#G_MapToolregion").offset().left){
                $("#G_MapToolregion").css({"top":"27px", "left":($(this).offset().left)+"px"});
                circleNow =$(this).attr("tag");
            }else{
				$("#G_MapToolregion").hide();
			}
		});
		$("#G_MapToolregion li").click(function(){
				$("#G_MapToolregion").hide();
			});
		$(".ui-floor_a").click(function(){
			$(this).addClass("ui-floor_a_hover");
			$(this).parent().siblings().find(".ui-floor_a").removeClass("ui-floor_a_hover");
			})
		//右侧信息列表初始化——搜索输入框
		$(".inputBox input").each(function(){			
			if($(this).val()){//当输入框不为空，隐藏提示信息
				$(this).siblings(".placeholder").css("display","none");
			}
			else{
				$(this).siblings(".placeholder").css("display","block");
			}
			this.onfocus = function(){
				$(".inputBox").removeClass("focus");
				$(this).parent(".inputBox").addClass("focus");
				$(this).siblings(".placeholder").css("display","none");
			}
			this.onblur = function(){
				$(this).parent(".inputBox").removeClass("focus");
				if(!$(this).val()){//当输入框为空，显示提示信息
					$(this).siblings(".placeholder").css("display","block");
				}
			}
		});
	}
    function triggerLegend(str){
        if(str=="close"){
            $("#map-legend").css("display","none");
        }else{
            var state = $("#map-legend").css("display") == "block" ? "none" : "block";
            $("#map-legend").css("display",state);
        }
    }
    function gis_item_clickHandler(objId){
		$("#"+objId).addClass("fix").siblings().removeClass("fix");
	}
	function gis_item_mouseEnterHandler(objId){
		$("#"+objId).addClass("hover");
	}
	function gis_item_mouseLeaveHandler(objId){
		$(".item_list").removeClass("hover");
	}
	function gis_closeAll(){
		$(".item_list").removeClass("fix");
	}
	/*---------------------------------------------*/
	//function typeSelected(typeName){
	//	$("#G_TypesSubTypes").show();
	//	$("#G_TypesSlideWrap").hide();
	//}
	function backToAllTypes(){
		$("#G_TypesSubTypes").hide();
		$("#G_TypesSlideWrap").show();
	}
	function showMorePopup(){
		$("#G_MoreInfoConPopup").toggle();
	}
	
	