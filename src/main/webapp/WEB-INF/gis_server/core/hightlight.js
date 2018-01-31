//Namespace.register("honeycomb.gis");
var hightlight=function (map){
	var lgt=this;
	/**
	 *禁用/开启标志位
	 */
	var listable=false;
	
	/**
	 *默认样式
	 */
	this.unlightimg=projectName+"/gis_server/img/symbol/${symbol}.png";
	
	/**
	 *选中样式
	 */
	this.lightimg=projectName+"/gis_server/img/symbol/${symbol}_H.png";
	
	/**
	 *图片宽度
	 */
	this.imgWidth=30;
	
	/**
	 *图片高度
	 */
	this.imgHeight=30;
	
	/**
	 *选中对象
	 */
	this.selectedFeature=null;
	
	/**
	 *矢量层
	 */
	layer = new OpenLayers.Layer.Vector("Marker Shadows",{
                    styleMap: new OpenLayers.StyleMap({
                        "default": new OpenLayers.Style({
							externalGraphic: lgt.unlightimg,
							graphicWidth: lgt.imgWidth,
							graphicHeight: lgt.imgHeight,
							graphicXOffset:-13,
							graphicYOffset:-36,
							graphicZIndex: "${zIndex}"
						}),
						"select": new OpenLayers.Style({
							externalGraphic: lgt.lightimg,
							graphicXOffset:-13,
							graphicYOffset:-36
							
						})
                    }),
                    rendererOptions: {zIndexing: true}
                });
	
	//添加矢量层
	map.addLayer(layer);
	
	var selectclick=new OpenLayers.Control.SelectFeature(
		layer,
		{	
			clickout: false,
			hover: false,
			multiple: false
		}
	);
	
	var selectlist=new OpenLayers.Control.SelectFeature(
		layer,
		{	
			clickout: false,
			hover: false,
			multiple: false
		}
	);
	
	selectclick.onSelect=function(feature){
		lgt.selectedFeature=feature;lgt.featureSelect(feature.attributes.id)
	}
	selectclick.onUnselect=function(feature){
		lgt.selectedFeature=null;lgt.featureUnselect(feature.attributes.id)
	}
	
	var selecthover = new OpenLayers.Control.SelectFeature(
		layer,
		{
			hover: true,
			highlightOnly: true,
			multiple: false
		}
	);
	
	selecthover.events.register("featurehighlighted",this,function(evt){lgt.hoverin(evt.feature.attributes.id)}); //hover高亮
	selecthover.events.register("featureunhighlighted",this,function(evt){lgt.hoverout(evt.feature.attributes.id)});//hoverout 取消高亮
	/**
	 *重新绘点,必须包含x、y、id关键字段
	 */
	this.drawFeatures=function(fsarr){
		lgt.removeAll();
		var features = [];  	
		for (var i = 0; i < fsarr.length; i++) {
			fsarr[i].zIndex=2;	
			features.push(
				new OpenLayers.Feature.Vector(
					new OpenLayers.Geometry.Point(fsarr[i].x, fsarr[i].y),fsarr[i]
				)
			);
		}
		layer.addFeatures(features);
	};
	
	/**
	 */
	this.drawFeature=function(obj){
		obj.zIndex=2;
		var fs=new OpenLayers.Feature.Vector(new OpenLayers.Geometry.Point(obj.x, obj.y),obj)
		layer.addFeatures([fs]);
	}
	
	this.removeAll=function(){
		lgt.unselectAll();
		layer.removeFeatures(layer.features); //情况图层
	}
	
	/**
	 *启用方法
	 */
	this.activate=function(){
		listable=true;
		map.addControl(selecthover);
		selecthover.activate();
		map.addControl(selectlist);
		selectlist.activate();
		map.addControl(selectclick);
		selectclick.activate();
	};
	
	/**
	 *禁用方法
	 */
	this.deactivate=function(){
		listable=false;
		selecthover.events.unregister("featurehighlighted",this,function(evt){lgt.hoverin(evt.feature.attributes.id)});
		selecthover.events.unregister("featureunhighlighted",this,function(evt){lgt.hoverout(evt.feature.attributes.id)});//hoverout 取消高亮
		selecthover.deactivate();
		selectlist.deactivate();
		selectclick.deactivate();
		map.removeControl(selecthover);
		map.removeControl(selectclick);		
	};
	
	/**
	 *列表鼠标click
	 */
	this.clickListItem=function (id){
		if(listable){
			var fs=layer.getFeaturesByAttribute("id",id);
			if(fs.length>0){
				selectlist.clickFeature(fs[0]);
				map.setCenter(new OpenLayers.LonLat(fs[0].geometry.x,fs[0].geometry.y));
			}
		}
	};
	
	/**
	 *根据id取消高亮
	 */
	this.unSelect=function(id){
		var fs=layer.getFeaturesByAttribute("id",id);
		if(fs.length>0){
			selectlist.unselect(fs[0]);
		}
	}
	
	/**
	 *列表鼠标mouseover
	 */
	this.hoveInListItem=function (id){
		if(listable){
			var fs=layer.getFeaturesByAttribute("id",id);
			if(fs.length>0){
				selecthover.overFeature(fs[0]);
			}
		}
	};
	
	/**
	 *列表鼠标mouseout
	 */
	this.hoveOutListItem=function (id){
		if(listable){
			var fs=layer.getFeaturesByAttribute("id",id);
			if(fs.length>0){
				selecthover.outFeature(fs[0]);
			}
		}
	};
	
	/**
	 * 取消全部选择
	 */
	this.unselectAll=function(){
		selectlist.unselectAll();
		selectclick.unselectAll();
		selecthover.unselectAll();
	};
	
	/**
	 *地图鼠标mouseover
	 */
	this.hoverin=function (id) {
	};
	
	/**
	 *地图鼠标mouseout
	 */
	this.hoverout=function (id) {
	};
	
	/**
	 *地图鼠标选中
	 */
	this.featureSelect=function(id) {
	};
	
	/**
	 *地图鼠标取消选中
	 */
	this.featureUnselect=function(id) {
	}; 
}