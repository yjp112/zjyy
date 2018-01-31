(function($){
	$.fn.circular = function(){
		var circular = $('<div class="cir cir-lt"></div><div class="cir cir-lb"></div><div class="cir cir-rt"></div><div class="cir cir-rb"></div>');
		this.append(circular);
		return this;
	};
	$.fn.tablehover = function(){
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

function getIdent(){
	var v = $(".ui-tab-v").find("li.current").attr("data-ident-v");
	var h = $(".ui-tab-h").find("li.current").attr("data-ident-h");
	return {
		vitem:v,
		hitem:h
	};
}