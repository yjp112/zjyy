/**
 * 设备报警或者开关方法
 * ids：设备id字符串
 * flag：标示位  0为取消报警，2添加状态
 */
function g_dcPoint(ids,flag){
	if(ids == null || ids == ""){
		if(flag==2){
			gs_deletealarm(gs_alarmarr);  //执行报警删除
		}
		if(flag==1){
			gs_deletestate(gs_statearr);  //执行状态删除
		}
		return;
	}
	var deviceids=gs_stringToArray(ids);
	//添加报警
	if(flag==2){
		if(deviceids.length>0){
			var addalarmarr=gs_array_diff(deviceids,gs_alarmarr);  //获得新添加报警数组
			var delealarmarr=gs_array_diff(gs_alarmarr,deviceids);  //获得新删除报警数组
			gs_addalarm(addalarmarr);  //执行报警添加
			gs_deletealarm(delealarmarr);  //执行报警删除
			addalarmarr=null;
			delealarmarr=null;
		}else{
			gs_deletealarm(gs_alarmarr);  //执行报警删除
		}
	}
	//添加状态
	if(flag==1){
		if(deviceids.length>0){
			var addstatearr=gs_array_diff(deviceids,gs_statearr);  //获得新添加状态数组
			var delestatearr=gs_array_diff(gs_statearr,deviceids);  //获得新删除状态数组
			gs_addstate(addstatearr);//执行状态添加
			gs_deletestate(delestatearr); //执行状态删除
			addstatearr=null;
			delestatearr=null;
		}else{
			gs_deletestate(gs_statearr);  //执行状态删除
		}
	}
	ids=null;
	flag=null;
	deviceids=null;
	//CollectGarbage();
}

/**
 * 添加报警
 * 参数
 * array hpid数组
 */
function gs_addalarm(array){
	var intersectionstate=gs_arrayIntersection(array,gs_statearr);//报警与状态重复
	if(intersectionstate.length>0){
		gs_deletestate(intersectionstate);  //优先显示报警
	}
	intersectionstate=null;	
	for(var i=0;i<array.length;i++){
		var hpid=OpenLayers.String.trim(array[i]);
		gs_getFeaturesById(hpid,2,gs_getFeaturesByIdCallBack);  //获得hpid，ajax执行回调函数
	}
	array=null;
}

/**
 * 删除报警
 * 参数
 * array hpid数组
 */
function gs_deletealarm(array){
	if(array.length>0){
		gs_removePointstate(array); //删除报警markers
	}
	gs_alarmarr=gs_array_diff(gs_alarmarr,array); //求差集删除报警获得新数组
	array=null;
}

/**
 * 添加状态
 * 参数
 * array hpid数组
 */
function gs_addstate(array){
	var arr=gs_array_diff(array,gs_alarmarr); //排除报警中的状态hpid
	for(var i=0;i<arr.length;i++){
		var hpid=OpenLayers.String.trim(arr[i]);
		if(OpenLayers.String.contains(hpid,g_selectfloor)){
			gs_getFeaturesById(OpenLayers.String.trim(arr[i]),1,gs_getFeaturesByIdCallBack);  //获得hpid，ajax执行回调函数
		}
	}
	arr=null;
}

/**
 * 删除状态
 * 参数
 * array hpid数组
 */
function gs_deletestate(array){
	if(array.length>0){
		gs_removePointstate(array); //删除状态markers
	}
	gs_statearr=gs_array_diff(gs_statearr,array);  //求差集删除状态获得新数组
	array=null;
}

/**
 * 数组差集
 * var array1 = [3,4,5,96,7,8];
 * var array2 = [1,2,3,4,5,6];
 * alert(gs_array_diff(array1, array2));//96,7,8
 * alert(gs_array_diff(array2, array1));//1,2,6
 */
function gs_array_diff(array1, array2){
	var o={};//转成hash可以减少运算量，数据量越大，优势越明显。
    if(array2.length==0){
        return array1;
    }
	for(var i=0,len=array2.length;i<len;i++){
		o[array2[i]]=true;
	}
	var result=[];
	var v;
	for(i=0,len=array1.length;i<len;i++){
		v = array1[i];
		if(o[v]) continue;
		result.push(v);
	}
	return result;
}

/**
 * 数组求交集
 * 参数
 * a hpid 数组
 * b hpid 数组
 */
function gs_arrayIntersection(array1,array2){
	var o={};//转成hash可以减少运算量，数据量越大，优势越明显。
	for(var i=0,len=array2.length;i<len;i++){
		o[array2[i]] = true;
	}
	var result=[];
	var v ;
	for(i=0,len=array1.length;i<len;i++) {
		v =array1[i];
		if(o[v]){
			result.push(v);
		}
	}
	return result;
}

/**
 * 报警回调函数
 * layername 图层名
 * feature 点对象，用于消防，楼控等获得kind类型
 * flag 楼控报警和状态标识位，0为报警2为状态
 */
function gs_getFeaturesByIdCallBack(feature,flag,ceng,code){
	if(gs_markerLayerExist(ceng)==true){
		var imgurl=gs_getImgUrl(code,flag);  //通过hpid获得marker层
		var hpid=feature.attributes.hpid;
		var ll=new OpenLayers.LonLat(feature.geometry.x,feature.geometry.y)
		var markerLayer=g_map.getLayersByName(ceng+"_mk")[0];
		var showlevel=feature.attributes.showlevel;
		if(flag==2){
			//gs_addAlarmPop(feature);
			gs_addAlarmMark(markerLayer,ll,imgurl,24,hpid,null)
			gs_alarmarr.push(hpid+":"+ceng+":"+code)
		}else if(flag==1){
			gs_addAlarmMark(markerLayer,ll,imgurl,24,hpid,showlevel)
			gs_statearr.push(hpid+":"+ceng+":"+code)
			gs_deviceShowLevel.put(hpid,showlevel);  //显示级别
		}
		markerLayer=null;
		ll=null;
		hpid=null;
		imgurl=null;
	}
	layername=null;
}

/**
 * 参数layers 图层面
 * hpid  唯一标示符
 * getFeatrueSetCenter('hospital:B-05F_ZNZM','B-05F-ZNZM-049');  //调用方法
 */
function g_getFeatrueSetCenter(hpid){
	gs_getFeaturesById(hpid,4,gs_onComplete); 
}

/**
 * 查询完成
 */
function gs_onComplete(feature,flag){
	if(features.length>0){
		var feature=features[0];
		var center =feature.geometry.getBounds().getCenterLonLat()
		map.setCenter(center);
		feature=null;
		center=null;
	}
	features=null;
}

/**
 * 删除markerceng对应图标
 * 参数
 * deviceids 数组
 */
function gs_removePointstate(deviceids){
	for(var i=0;i<deviceids.length;i++){
		gs_removeAlarmMarker(deviceids[i]);
	}
}


/**
 * id字符串转数组
 * 参数
 * idstr hpid字符串
 */
function gs_stringToArray(idstr){
	if(idstr==null)idstr="";
	var ids=idstr.split(",");
	return ids;
}

/**
 * id字符串转数组
 * 参数
 * idstr hpid字符串
 */
function gsMaostringToArray(idstr){
    if(idstr==null)idstr="";
    var ids=idstr.split(":");
    return ids;
}

/**
 *通过features获得对应abcd
 */
function gs_geoQueryAddFlag(features){
	for(var i=0;i<features.length;i++){
		features[i].attributes.flag=gs_getFlag(i)
	}
}

/**
 *通过序号计算ABCD
 */
function gs_getFlag(num){
	var flags=["A","B","C","D","E","F","G"];
	var i=num%(flags.length);
	return flags[i];
}

/**
 *通过id添加features
 *参数
 *objs
 */
function g_idAddFeature(objs){
	if(light!=null){
		light.removeAll();//清空当前图层
	}
	gs_addQueryPoint(objs,"");
}

/**
 *鼠标点击列表事件
 *参数
 *hpid：点击列表id
 *objs：当前列别点
 */
function g_queryListClick(hpid,layername,objs){
	var floor=gs_getFloorByHpid(hpid);//获得图层
	if(floor!=g_selectfloor){//判断是否为当前图层
		g_changeFloor(floor); //切换楼层
		g_addDeviceLayers(layername,true)//添加图层
		
		if(objs!=null){
			gs_addQueryPoint(objs,hpid); //绘制点
		}
		
		//根据layer判断是否有气泡
		//g_showDeviceState(layername);//显示气泡（温度值）

	}else{
		light.unselectAll();
		light.clickListItem(hpid)//设置中心点
	}
	g_map.zoomTo(4);
}
/**
 *鼠标点击列表事件
 *参数
 *hpid：点击单个id，巡更用到
 *objs：当前列别点
 *只显示A，只有一个点
 */
function g_queryListClickSingle(hpid,layername,objs){
	var floor=gs_getFloorByHpid(hpid);//获得图层
	//alert(hpid+":"+floor)
	//if(floor!=g_selectfloor){//判断是否为当前图层
		g_changeFloor(floor); //切换楼层
		g_addDeviceLayers(layername,true)//添加图层

		if(objs!=null){
			//alert(objs[0].gisSort+"="+objs[0].hpid+"="+hpid)
			gs_addQueryPoint(objs,hpid); //绘制点
		}
		
		//根据layer判断是否有气泡
		//g_showDeviceState(layername);//显示气泡（温度值）
	/*}else{
		light.unselectAll();
		light.clickListItem(hpid)//设置中心点
	}*/
	g_map.zoomTo(4);
}
function gs_addQueryPoint(objs,centerid){
	for(var i=0;i<objs.length;i++){
		if(OpenLayers.String.contains(objs[i].hpid,g_selectfloor)){ //当前层里面的点
			gs_getFeaturesById(objs[i].hpid,objs[i].gisSort,function(feature,gisSort){
				//alert("test:"+gisSort+","+feature.attributes.hpid+",x:"+feature.geometry.x+",y:"+feature.geometry.y)
				var obj={'x':feature.geometry.x,'y':feature.geometry.y,'id':feature.attributes.hpid,'symbol':gisSort}
					light.drawFeature(obj);  //画点
				if(centerid==feature.attributes.hpid){
					light.unselectAll();
					light.clickListItem(centerid)//设置中心点
				}
			});
		}
	}
}

function createXmlhttp(func,func2) {
    var xmlhttp;
    if (window.XMLHttpRequest) {
        //code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    } else {
        //code for IE6, IE5
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }

    xmlhttp.onreadystatechange=function(){
        if (xmlhttp.readyState==4 ){
            if(xmlhttp.status==200){
                var data = xmlhttp.responseText;
                if(data!=null && data!=""){
                    data = eval("("+data+")");
                }
                func(data);
            }else{

            }
            if(func2!=null){func2();}
        }
    }
    return xmlhttp;
}
function sendXmlhttp(_xmlhttp,method,url,dataIn) {
    if(method.toUpperCase()=="GET"){
        _xmlhttp.open("GET",url,true);
        _xmlhttp.send();
    }else if(method.toUpperCase()=="POST"){
        _xmlhttp.open("POST",url,true);
        _xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
        _xmlhttp.send(dataIn);
    }
}
//空判断
function isEmpty(s){
    return (s==null || s==undefined || s=="");
}