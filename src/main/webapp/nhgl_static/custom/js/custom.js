var temperataure="°C"; 
var electric="kWh";
var water= "t";
var gas = "t";
var energy = "J";
var T = window.T = {};
T.tabLayout = null;
(function($){
	$.fn.circular = function(){
		var circular = $('<div class="cir cir-lt"></div><div class="cir cir-lb"></div><div class="cir cir-rt"></div><div class="cir cir-rb"></div>');
		this.append(circular);
		this.find(".nh-table tr").find("th:last").css("border-right","none");
		this.find(".nh-table tr").find("td:last").css("border-right","none");
		this.find(".nh-table tbody tr").bind({
			mouseover:function(){
				$(this).css("background","#06251d");
			},
			mouseleave:function(){
				$(this).css("background","none");
			}
		});
		return this;
	};
})($||jQuery);
T.setBarSize = function(v){
	var width = v.width();
	var ratio = parseFloat(v.attr("ratio"));
	ratio = isNaN(ratio)?1:ratio;
	v.width(width*ratio);
};
T.nhInit = function(){
	if(T.innerLayout&&!T.innerLayout.destroyed){
		T.innerLayout.destroy();
	}
	T.innerLayout = $("#centerMain").layout({
		defaults : {
			resizable : false,
			closable : false,
			spacing_open : 0
		},
		west:{
			size:42
		},
		onresize_end:function(){
     //       var contHeight = $(".nh-content").height();
		   // var tHeight = contHeight>=T.innerLayout.center.state.innerHeight-58?contHeight:T.innerLayout.center.state.innerHeight-58;
		   // $(".nh-box,.nh-content").css("height",tHeight);
        }
	});
	$(".handle-bar").each(function(){
   		T.setBarSize($(this));
    });
	$(".pitem-bar").each(function(){
   		T.setBarSize($(this));
    });
    $(".item-ratio-i").each(function(){
   		T.setBarSize($(this));
    });
   // var contHeight = $(".nh-content").height();
   // var tHeight = contHeight>=T.innerLayout.center.state.innerHeight-58?contHeight:T.innerLayout.center.state.innerHeight-58;
   // $(".nh-box,.nh-content").css("height",tHeight);
   $(".nh-option>li>a").click(function(){
   		$(this).addClass("current").parent().siblings().find("a").removeClass("current");
   });
   $(".nh-grid").circular();
};
T.nhgsInit = function(){
	if(T.innerLayout&&!T.innerLayout.destroyed){
		T.innerLayout.destroy();
	}
	T.innerLayout = $("#centerMain").layout({
		defaults : {
			resizable : false,
			closable : false,
			spacing_open : 0
		},
		west:{
			size:42
		}
	});
	$(".nh-td-bar").each(function(){
   		T.setBarSize($(this));
    });
	$(".nh-option>li>a").click(function(){
   		$(this).addClass("current").parent().siblings().find("a").removeClass("current");
   });
   $(".nh-grid").circular();
};