/**
 *定义气泡
 */
var gs_popup=null;

/**
 *添加气泡
 */
function g_addPopUp(feature,title,content,jsmethod){
	g_removePopUp();
	if(feature.CLASS_NAME=="OpenLayers.Feature.Vector"){
		content=content+"<div id='methodDiv' style='width:1px;height:1px'><div>"
		var offset = {'size':new OpenLayers.Size(0,0),'offset':new OpenLayers.Pixel(0,20)};
		gs_popup = new OpenLayers.Popup.GisFramedCloud("chicken", 
								 feature.geometry.getBounds().getCenterLonLat(),
								 null,
								 title,
								 content,
								 offset, true,function(){
                g_removePopUp();
            });
		gs_popup.fixedRelativePosition=true;
		g_map.addPopup(gs_popup);
		if(typeof(jsmethod)!="undefined"&&jsmethod!=""&&jsmethod!=null){
			//document.getElementById("methodDiv").innerHTML='<div style="overflow:hidden;">55<div>'+jsmethod;
		}
	}
}

/**
 *删除气泡
 */
function g_removePopUp(){
	if(gs_popup!=null){
		g_map.removePopup(gs_popup);
        gs_popup.destroy();
		gs_popup=null;
	}
}


/**
 *添加气泡
 *参数
 *lon气泡经度
 *lat气泡纬度
 *气泡内容
 */
function gs_addStatePopup(feature,contentHtml,w,h){
	//alert(feature.arrtibutes.ps)
	var popup = new OpenLayers.Popup.Anchored("popup",
		feature.geometry.getBounds().getCenterLonLat(),
		//new OpenLayers.Size(160,60),  //气泡宽高
		new OpenLayers.Size(w,h),  //气泡宽高
		//new OpenLayers.Size(151,55),  //气泡宽高
		contentHtml,
		null,
		false
	);
	var position="br";//B：bottom,T:TOP,R:right,l:left
	var ps=feature.attributes.position;
	var offsetx=11;//增加-》右移
	var offsety=-2;//增加-》下移
	if(ps=="tr"){
		var position="tr";
		offsetx=11;
		offsety=2;
	}else if(ps=="tl"){
		var position="tl";
		offsetx=-11;
		offsety=3;
	}else if(ps=="bl"){
		var position="bl";
		offsetx=-11;
		offsety=-1;
	}
	var offset = {'size':new OpenLayers.Size(0,0),'offset':new OpenLayers.Pixel(offsetx,offsety)} ; //气泡偏移量
	popup.backgroundColor = 'transparent';
	popup.anchor = offset;
	popup.relativePosition = position;
	popup.calculateRelativePosition = function () {
		 return position;
	};
	var showlevel=feature.attributes.showlevel;
	var hpid=feature.attributes.showlevel;
	
	var zoom=g_map.getZoom(); //当前地图级别
	var level=1;
	if(zoom>1&&zoom<4){
		level=2;
	}else if(zoom>3){
		level=3;
	}

	g_map.addPopup(popup);
	
	if(showlevel>level||showlevel==level){
		popup.hide();
	}
	var obj={};
	obj.showlevel=showlevel;
	obj.pop=popup;
	gs_wsfpop.push(obj);
}

function g_addPopupById(hpid,contenthtml){
	hpid=OpenLayers.String.trim(hpid);
	gs_getFeaturesById(hpid,4,function(feature,flag){
		var content=contenthtml;
		gs_addStatePopup(feature,content);
	}); 
}
/**
 *显示图层气泡内容
 */
function g_showDeviceState(layername){
	var typename=gs_getTypeNameByLayer(layername);
	if(typename!=""){
		gs_query(typename,"",function(features){
			if(features.length>0){
				for(var i=0;i<features.length;i++){
					var hpid=features[i].attributes.hpid;
					var local="br";
					if(features[i].attributes.position!=null ){
						local=features[i].attributes.position;
					}
					gc_showPopup(features[i],local,hpid);
				}
			}
		});
	}
}

/**
 * 
 * @param feature
 * @return
 */
function gs_addAlarmPop(feature){
	var hpid=feature.attributes.hpid;
	var popup = new OpenLayers.Popup.Anchored("popup",
		feature.geometry.getBounds().getCenterLonLat(),
		new OpenLayers.Size(35,30),  //气泡宽高
		//'<img src="img/alarmbutton.png" onclick="alert(\''+projectName+'\');">',
		'<input type="button" value="处理" onclick="alert(\''+hpid+'\');"/>',
		null,
		false
	);
	var offset = {'size':new OpenLayers.Size(0,0),'offset':new OpenLayers.Pixel(-20,-10)} ; //气泡偏移量
	popup.backgroundColor = 'transparent';
	popup.anchor = offset;
	popup.relativePosition = "bl";
	popup.calculateRelativePosition = function () {
		 return 'bl';
	};
	gs_alarmpop.put(hpid, popup);
	g_map.addPopup(popup);
}

/**
 * 
 * @param hpid
 * @return
 */
function gs_removeAlarmPop(hpid){
	var pop=gs_alarmpop.get(hpid);
	if(pop){
		gs_alarmpop.remove(hpid);
		g_map.removePopup(pop);
		pop.destroy();
	}
	pop=null;
} 