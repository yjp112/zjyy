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
 * 获得图片
 * 参数
 * hpid 唯一编码
 * flag 区分报警和状态的标识
 */
function gs_getImgUrl(code,flag){
    keystr =device_img[code][flag];
	return d_img_path+keystr;//gs_getPzValue(gs_pzimg,keystr,typestr);
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
    var layerkey=gs_pzlayer[layername];
    if(layerkey!=""){
        typename=gs_workSpace+":"+g_selectfloor+"_"+layerkey;
    }
	return typename;
}

function gs_getTypeNameByHpid(hpid){
    var typename=gs_pzlayer[hpid];
    var servType=null;
    if(typename!=""){
        servType=gs_workSpace+":"+g_selectfloor+"_"+typename;  //拼接typename
    }
    return servType;
}
