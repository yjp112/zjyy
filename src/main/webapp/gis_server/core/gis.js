//var strGisIp="http://10.10.76.19:8282/";
//openlayers的地址
var OlURL=strGisIp+gs_workSpace;
//geoserver服务器地址
var layerURL=strGisIp+gs_serverName;
//map对象
var g_map=null;
//点击查询层
var gs_querylayers=new Array();
//hover查询层
var gs_hoverlayers=new Array();
//marker列表
var gs_markermap=null;
var gs_drawControls,gs_drawLayer,fenquLayer;
//当前选中楼层
var g_selectfloor="";
//当前选中楼层id
var g_selectfloorId;
//报警数组
var gs_alarmarr=new Array();
//状态数组
var gs_statearr=new Array();
var gs_alarmpop=null;
//状态级别map
var gs_deviceShowLevel=null;
var gs_wsfpop=[];

var light=null;

OpenLayers.Util.onImageLoadErrorColor = "transparent";
/**
 * 初始化
 * 参数
 * floor 楼层
 */
function g_init(floor){
	//g_selectfloor=floor;
	//OpenLayers.ProxyHost= OlURL+"/cgi/proxy.cgi?url=";
	OpenLayers.ProxyHost=proxyHost;
	gs_querylayers.length=0;
	gs_hoverlayers.length=0;
	gs_alarmarr.length=0;
	gs_statearr.length=0;
	gs_wsfpop.length=0;

	if(gs_deviceShowLevel!=null){
		gs_deviceShowLevel.destroy();
	}
	gs_deviceShowLevel=new MarkerMap();
	
	if(gs_markermap!=null){
		gs_markermap.destroy();
	}
	gs_markermap=new MarkerMap();
	
	var bildnum=floor;
	if(bulids[bildnum]==null){
		alert("错误，"+bildnum+"楼未配置地图等级");
	}
	g_map = new OpenLayers.Map('G_Map',{
		controls: [
			new OpenLayers.Control.HCPanZoomBar(),
			new OpenLayers.Control.Navigation({dragPanOptions:{enableKinetic:true},defaultDblClick:null})
		],
		maxExtent: gs_getMapBounds(bildnum),
		projection: "EPSG:4326",
		scales: gs_getMapScales(bildnum),
		units: 'degrees'
	});
	g_map.fractionalZoom =true;

	var baselayer = new OpenLayers.Layer.WMS(
		"基础地图", layerURL+"/wms",
		{
			LAYERS:bulids[bildnum].baseLayer,
			format: 'image/png'
		},
		{
			buffer: 1,
			displayOutsideMaxExtent: true,
			singleTile: true,
			isBaseLayer: true,
			transitionEffect:"resize",
			yx : {'EPSG:4326' : true}
		}
	);
	g_map.addLayers([baselayer]);
	g_map.setCenter(gs_getMapCenter(bildnum),gs_getInitLevel(bildnum));
	g_map.events.register("zoomend", this, gs_zoomChanged);
}

function g_changeFloor(floor){
    if(gs_getMapBounds(floor)==null){
        alert("GIS地图不存在！"+"------>");
        return false;
    }
	if(g_map!=null){
		if(g_map.CLASS_NAME){
			g_removePopUp();
			for(var i=0;i<g_map.popups.length;i++){
				g_map.removePopup(g_map.popups[i]);
			}
		}
		g_map.destroy();
		g_map=null;
		light=null;
	}
	g_selectfloor=bulids[floor].baseLayer;
    g_selectfloorId = floor;
    g_init(floor);
    g_selectFeatures();
    light=new hightlight(g_map);
    light.hoverin=gc_hoverinSort;
    light.hoverout=gc_hoveroutSort;
    light.activate();
	gc_changeFloor(floor);
    return true;
}

/**
 * 通过hpid异步获得feature对象
 * 参数
 * hpid 设备唯一标识
 * flag 报警状态标识
 * callback 回调函数
 */
function gs_getFeaturesById(hpid,flag,callback){
    var  hs = gsMaostringToArray(hpid);
	var layers=gs_getTypeNameByHpid(hs[1]);
	if(layers!=""){
		var filter="hpid='"+hs[0]+"'";
		gs_query(layers,filter,function(features){
				if(features.length>0){
					callback(features[0],flag,hs[1],hs[2]);
				}});
	}
	layers=null;
	//CollectGarbage();
}

/**
 * 刷新车库的停车状态，车库图层名为ceng_PMS
 */
function refreshCarparkStatus(){
	var chekulayers=g_map.getLayersByName("ceng_cheku");
	if(chekulayers.length>0){
		chekulayers[0].mergeNewParams({'time':new Date().getTime()})
	}
}

/**
 * 取消删除指定marker
 * 参数
 * layername：String 图层名,
 * hpid:String 唯一标示
 */
function gs_removeAlarmMarker(hpid){
    var  hs = gsMaostringToArray(hpid);
    var layername=hs[1];
	if(gs_markerLayerExist(layername)==true){
		var mklayer=g_map.getLayersByName(layername+"_mk")[0];
		var marker=gs_markermap.get(hs[0]);
		if(marker){
			mklayer.removeMarker(marker);
			gs_markermap.remove(hpid);
			marker.destroy();
		}
		mklayer=null;
		marker=null;
	}
}

/**
 * 判断markerlayer
 * @param layername
 * @return
 */
function gs_markerLayerExist(layername){
	var exist=false;
	if(g_map.CLASS_NAME&&g_map.CLASS_NAME=="OpenLayers.Map"){
		if(g_map.getLayersByName(layername+"_mk").length>0){
			exist=true;
		}
	}
	return exist;
}

/**
 * 添加marker点
 * 参数
 * markerLayer：String图层名,
 * ll：lonlat 经纬度,
 * imgurl：String 图片路径,
 * sizenum：number 图片大小,
 * hpid:String 唯一标示
 */
function gs_addAlarmMark(markerLayer,ll,imgurl,sizenum,hpid,showlevel){
	if(typeof markerLayer!="undefined"){
		var size = new OpenLayers.Size(100,100);
		var re = /^[1-9]\d*$/;
		if (re.test(sizenum)){
			size = new OpenLayers.Size(sizenum,sizenum);
		}
		var offset = new OpenLayers.Pixel(-(size.w/2), -size.h);
		var icon = new OpenLayers.Icon(imgurl, size);
		var marker = new OpenLayers.Marker(ll, icon);
		
		var level=1;
		var zoom=g_map.getZoom();
		if(zoom>1&&zoom<4){
			level=2;
		}else if(zoom>3){
			level=3;
		}
		
		if(showlevel==null||showlevel<level){
			//alert(showlevel+"&&&&&&&&&&&&"+level)
			markerLayer.addMarker(marker); //报警图标
		}
		gs_markermap.put(hpid, marker);
		markerLayer=null;
		size=null;
		re=null;
		offset=null;
		icon=null;
		marker=null;
	}
}

/**
 * 添加设备图层
 * 参数
 * layername:String 图层名
 * isStatLayer:bool 是否状态图层
 */
function g_addDeviceLayers(layername,ishow){
	var layers=gs_getTypeNameByLayer(layername);
	if(layers!=""){
		OpenLayers.Request.GET({
			url: layerURL+"/wfs?SERVICE=WFS&VERSION=1.1.0&REQUEST=DescribeFeatureType&TYPENAME="+layers,
			success: function(req){
			
			  var featureTypesParser = new OpenLayers.Format.WFSDescribeFeatureType();
			  var responseText = featureTypesParser.read(req.responseText);
			  var featureTypes = responseText.featureTypes;
			  if(typeof (featureTypes)=="undefined"){
				  return;
			  }else{
					var newlayer=gs_getwmsLayer(layername,layers);
					var mklayer=gs_addMarkerLayer(layername);
					newlayer.setVisibility(ishow);
					mklayer.setVisibility(ishow);
					g_map.addLayers([newlayer,mklayer]);
					gs_changQueryLayer();
			  }
			},
			failure:function (response){
			},
			async: false,
			timeout:1000
		});
	}
}

/**
 * 添加非设备层
 *
 */
function g_addOtherLayer(layername,typename,ishow){
	var layer=gs_getwmsLayer(layername,typename);
	layer.setVisibility(ishow);
	g_map.addLayers([layer]);
}


/**
 * 创建marker层
 * 参数：
 * layername：String 图层名
 */
function gs_addMarkerLayer(layername){
	return new OpenLayers.Layer.Markers(layername+"_mk",{displayInLayerSwitcher:false});
}

/***
 * 获得WMS地图图层的方法
 * 参数
 * name：String 自定义图层名,
 * layername：String 地图服务器上的图层,多图层用","隔开
 */
function gs_getwmsLayer(name,layername){
	return new OpenLayers.Layer.WMS(
		name, layerURL+"/wms",
		{
			LAYERS: layername,   //修改
			transparent:true
		},{
			singleTile: true
		}
	);
}

/**
 * 图层控制
 * 参数
 * layernames：String 图层名字
 * isShow：bool 是否显示
 */
function g_changLayerVisibility(layername,isShow){
	var bol=false;
	var layers = g_map.getLayersByName(layername);
	if(layers.length>0){
		bol=true;
		layers[0].setVisibility(isShow);
	}
	var mklayers = g_map.getLayersByName(layername+"_mk");
	if(mklayers.length>0){
		bol=true;
		mklayers[0].setVisibility(isShow);
	}
	if(!bol)alert("图层显示/隐藏失败，未加载该图层");
	gs_changQueryLayer();
}

/**
 * 根据图层控制改变查询图层
 */
function gs_changQueryLayer(){
	gs_querylayers.length=0;
	gs_hoverlayers.length=0;
	if(clickLayer.length>0){
		for(var j=0;j<clickLayer.length;j++){
			var layers=g_map.getLayersByName(clickLayer[j])
			if(layers.length>0){
				if(layers[0].visibility==true){
					gs_querylayers.push(layers[0]);
				}
			}
		}
	}
	if(hoverLayer.length>0){
		for(var j=0;j<hoverLayer.length;j++){
			var layers=g_map.getLayersByName(hoverLayer[j])
			if(layers.length>0){
				if(layers[0].visibility==true){
					gs_hoverlayers.push(layers[0]);
				}
			}
		}
	}
}

/**
 * 添加几何查询
 */
function g_selectFeatures(){
	gs_drawLayer = new OpenLayers.Layer.Vector("DrawLayer",{displayInLayerSwitcher:false});  //定义绘制图层
	gs_drawLayer.events.on({
		beforefeatureadded: function(event) {
			gs_drawLayer.removeAllFeatures();//清空绘制图层
			selectquery(event.feature.geometry);
			g_toggleControl("point");
		}
	}); 
	g_map.addLayers([gs_drawLayer]);
	gs_drawControls = {
		"pointhover":new OpenLayers.Control.WMSGetFeatureInfo({
				url: layerURL+'/wms', 
				infoFormat: "application/vnd.ogc.gml",
				layers: gs_hoverlayers,
				queryVisible: true,
				hover:true,
				eventListeners: {
					getfeatureinfo: function(event) {
						if(event){
							var features=event.features;
							if(features.length>0){
								//showPopup(features[0])  //显示气泡
								alert("hover查询结果"+features[0].attributes.hpid);
								//clickDevice(features[0].attributes.hpid);	
							}
						}
					}
				}
			}),	
		"point":new OpenLayers.Control.WMSGetFeatureInfo({
					url: layerURL+'/wms', 
					infoFormat: "application/vnd.ogc.gml",
					layers: gs_querylayers,
					queryVisible: true,
					hover:false,
					eventListeners: {
						getfeatureinfo: function(event) {
							if(event){
								var features=event.features;
								if(features.length>0){
									gc_clickDevice(features[0]);
								}
							}
						}
					}
				}),
		"square":new OpenLayers.Control.DrawFeature(gs_drawLayer, 
					OpenLayers.Handler.RegularPolygon,{handlerOptions: {sides: 4,irregular: true}}), 
		"polygon":new OpenLayers.Control.DrawFeature(gs_drawLayer,
					OpenLayers.Handler.Polygon),			
		"circle":new OpenLayers.Control.DrawFeature(gs_drawLayer, 
					OpenLayers.Handler.RegularPolygon,{handlerOptions: {sides: 40}}) 
	};
	for(var key in gs_drawControls) {
		g_map.addControl(gs_drawControls[key]);
	}
	g_toggleControl("point");
}

/**
 * 几何绘图切换方法
 * 参数
 * keystr drawControls的key,传空获得其他字符串停止所以drawControls操作
 */
function g_toggleControl(keystr) {
	gs_drawLayer.removeAllFeatures();//清空绘制层
	for(key in gs_drawControls) {
		var control = gs_drawControls[key];
		if(keystr == key||(keystr+"hover")==key) {
			control.activate();
		} else {
			control.deactivate();
		}
	}
}

/**
 * 几何查询方法（可以扩展到任意图层，目前仅摄像头）
 * 参数：
 * geometry：几何对象
 */
function selectquery(geometry){
	var filter="intersects(the_geom,"+geometry+")" //几何查询
	//for(var i=0;i<geometryLayer.length;i++){
		var layername=circleNow;
		var layer=g_map.getLayersByName(layername);
		var layers=gs_getTypeNameByLayer(layername);
		if(layer.length>0){
			if(layer[0].visibility==true){
				gs_query(layers,filter,function(features){
						var hpids=[];
						for(var i=0;i<features.length;i++){
							hpids.push(features[i].attributes.hpid);
						}
						gc_kuangxuan(hpids);
					});
			}
		}
	//}
}

/**
 * 查询
 * 参数
 * 
 */
function gs_query(layers,filter,callback){
	if(layers!=null&&layers!=""&&typeof layers!="undefined"){
		var params = {
			REQUEST: "GetFeature",  //查询方式
			srsName: 'EPSG:4326',  //投影
			service: "WFS",       //服务类型
			version: "1.0.0",
			//MAXFEATURES:9, //查询结果最大数量
			typeName:layers  //查询图层
			//typeName:"hospital:A_01F_xiaofang,hospital:A_01F_shexiangtou"  //查询图层
		};
		if(filter!=""&&filter!=null){
			params.cql_filter=filter;  //CQL 过滤
		}
		var request = OpenLayers.Request.GET({
			url:layerURL+"/wfs",
			params:params,
			success:function (response){
				var gmlParse = new OpenLayers.Format.GML();
				var features= gmlParse.read(response.responseText);
				callback(features)
			},
			failure:function (response){
			}
		});
	}
}

/**
 * 获得当前地图中可见的设备层(即包含字符串"ceng_")
 * @return
 */
function g_getLayerName(){
	var namestr= new Array();
	var layers=g_map.layers;
	if(layers!=null){	
		for(var i=0;i<layers.length;i++){
			if(layers[i].visibility==true&&!OpenLayers.String.contains(layers[i].name,"_mk")){
				namestr.push(layers[i].name);
			}
		}
	}
	return namestr;
}

/**
 *地图缩放后鼠标处理事件
 */
function gs_zoomChanged(evt){
	var zoom=g_map.getZoom(); //当前地图级别
	var level=1;
	if(zoom>1&&zoom<4){
		level=2;
	}else if(zoom>3){
		level=3;
	}
	if(gs_statearr&&gs_statearr.length>0){
		for(var i=0;i<gs_statearr.length;i++){
			var hpid=OpenLayers.String.trim(gs_statearr[i]);
            var  hs = gsMaostringToArray(hpid);
            var layername=hs[1];
            var showlevel=gs_deviceShowLevel.get(hs[0]);
			if(gs_markerLayerExist(layername)==true){
				var mklayer=g_map.getLayersByName(layername+"_mk")[0];
				var marker=gs_markermap.get(hs[0]);
				if(marker){
					mklayer.removeMarker(marker);
					if(showlevel<level){
						mklayer.addMarker(marker);
					}
				}
				mklayer=null;
			}
		}
	}
	for(var i=0;i<gs_wsfpop.length;i++){
		gs_wsfpop[i].pop.hide();
		if(gs_wsfpop[i].showlevel<level){
			gs_wsfpop[i].pop.show();
		}
	}
}