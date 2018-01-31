//全局变量
var gs_workSpace=gis_workSpace;//geoserver服务器工作空间名
var gs_serverName=gis_tomcat_webapp;//tomcat下webapps下的地图工程名
/******************************************需要配置的信息**********************************************************/
//图片配置
var d_img_path = device_img_path;
//图层配置
var hoverLayer=[];   //几何查询图层

/*******************************************************************************************************************/

/**
 * 读取json配置信息
 * 参数
 *
 */
function gs_getPzValue(obj,typestr,keystr){
	var value="";
	var type=obj[typestr];
	if(typeof (type)!="undefined"){
		var key=type[keystr];
		if(typeof (key)!="undefined"){
			value=key;
		}
	}
	return value;
}

/**
 * 解析图片配置名
 * 参数
 * hpid 唯一编码
 */
function gs_getImgPz(hpid){
	var ids=hpid.split("_");
	if(hpid){
		return ids[2]+"_"+ids[3]
	}else{
		return null;
	}
}

/**
 * 解析图层配置名
 * 参数
 * hpid 唯一编码
 */
/*function gs_getLayerPz(hpid){
	var ids=hpid.split("_");
	var type=null;
	if(ids.length>3){
		if(typeof (gs_pzlayer[ids[2]])!="undefined"){
			type=ids[2];
		}else if(typeof (gs_pzlayer[ids[3]])!="undefined"){
			type=ids[3];
		}
		
		
	}
	if(OpenLayers.String.contains(hpid,"ES_EL")){
		type="ES";
	}
	return type
}*/
function gs_getLayerPz(hpid){
	var ids=hpid.split("_");
	var type=null;
	if(ids.length>3){
		for (var key in gs_pzlayer)
	    {
	       if(OpenLayers.String.contains(","+key+",",","+ids[2]+"_"+ids[3]+",")){
	    	   type=key;
	    	   return type;
	       }
	    }
	}
	if(type==null){alert("根据hpid解析图层配置名失败")}
    return type;
}

/**
 * 获得图片
 * 参数
 * hpid 唯一编码
 * flag 区分报警和状态的标识
 */
function gs_getImgUrl(hpid,flag){
	var keystr=gs_getImgPz(hpid);//获得小类标识
	//var typestr="stateimg";
	var s="ON_";
	var e=".png";
	if(flag==0){
		//typestr="alarmimg";
		s="AL_";
		e=".gif";
	}
	return d_img_path+s+keystr+e;//gs_getPzValue(gs_pzimg,keystr,typestr);
}

/**
 * 根据hpid获得对应图层名字
 * 参数
 *
 */
function gs_getTypeById(hpid){
	var keystr=gs_getLayerPz(hpid);//获得大类标识
	return gs_getPzValue(gs_pzlayer,keystr,"layername");
}

/**
 * 根据id获得服务器图层名(getLayersByHpid)
 * 参数
 */
function gs_getTypeNameByHpid(hpid){
	var keystr=gs_getLayerPz(hpid);//获得大类标识
	var floor=hpid.substring(0,5);//获得楼层
	//var floor=ids[0]+"_"+ids[1];
	var typename=gs_getPzValue(gs_pzlayer,keystr,"typename");//获得服务器上的名字
	var servType=null;
	if(typename!=""){
		servType=gs_workSpace+":"+floor+"_"+typename;  //拼接typename
	}
	return servType;
}

/**
 * 获得楼范围
 * 参数
 * buildnum 楼代号 如:"A"代表内科
 */
function gs_getMapBounds(buildnum){
	var bounds=null;
	if(bulids!=""&&bulids!=null){
		var minLon=gs_getPzValue(bulids,buildnum,"minLon");
		var minLat=gs_getPzValue(bulids,buildnum,"minLat");
		var maxLon=gs_getPzValue(bulids,buildnum,"maxLon");
		var maxLat=gs_getPzValue(bulids,buildnum,"maxLat");
		if(OpenLayers.String.isNumeric(minLon)&&OpenLayers.String.isNumeric(minLat)&&OpenLayers.String.isNumeric(maxLon)&&OpenLayers.String.isNumeric(maxLat)){
			bounds=new OpenLayers.Bounds(minLon,minLat,maxLon,maxLat);
		}
	}
	return bounds;
}

/**
 * 获得比例尺
 * 参数
 * buildnum 楼代号 如:"A"代表内科
 */
function gs_getMapScales(buildnum){
	var scales=null;
	if(bulids!=""&&bulids!=null){
		scales=gs_getPzValue(bulids,buildnum,"scales");
	}
	return scales;
}

/**
 * 获得中心点
 * 参数
 * buildnum 楼代号 如:"A"代表内科
 */
function gs_getMapCenter(buildnum){
	var center=null;
	if(bulids!=""&&bulids!=null){
		var centerx=gs_getPzValue(bulids,buildnum,"centerx");
		var centery=gs_getPzValue(bulids,buildnum,"centery");
		if(OpenLayers.String.isNumeric(centerx)&&OpenLayers.String.isNumeric(centery)){
			center=new OpenLayers.LonLat(centerx,centery);
		}
	}
	return center;
}

/**
 * 获得初始级别
 * 参数
 * buildnum 楼代号 如:"A"代表内科
 */
function gs_getInitLevel(buildnum){
	var InitLevel=0;
	if(bulids!=""&&bulids!=null){
		var level=gs_getPzValue(bulids,buildnum,"initlevel");
		if(OpenLayers.String.isNumeric(level)){
			InitLevel=level;
		}
	}
	return InitLevel;
}

/**
 * 根据图层名获得typename
 * 参数
 * layername 图层名
 */
function gs_getTypeNameByLayer(layername){
	var typename="";
	for(var key in gs_pzlayer){
		var layerkey=gs_pzlayer[key];
		if(layername==layerkey.layername){
			typename=gs_workSpace+":"+g_selectfloor+"_"+layerkey.typename
			break;
			//"hospital:A_01F_shexiangtou"
		}
	}
	return typename;
}

/**
 * 获得楼层"A_01F"代号中的A
 * 参数
 * floor 楼层 如:"A_01F"
 */
function gs_getBuildNum(floor){
	return floor.split("_")[0];
}

/**
 * 获得气泡内flash的URL
 * 参数
 * hpid 唯一编码
 * flag 区分报警和状态的标识
function gs_getSwfUrl(hpid){
	var keystr=gs_getImgPz(hpid);//获得小类标识
	if(keystr!=null){
		return gs_getPzValue(gs_pzimg,keystr,"popurl");
	}else{
		return "";
	}
} */

/**
 *根据hpid获得楼层
 */
function gs_getFloorByHpid(hpid){
	var ids=hpid.split("_");
	//return hpid.substring(0,5);
	var floor=ids[0]+"_"+ids[1];
	return floor;
}