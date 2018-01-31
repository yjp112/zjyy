/**
 * Created by DELL on 2015-11-26.
 */
//框选摄像头
//layer 和框选设备的一样
//hpid传出设备的hpid 数个hpid的数组[a,b,c]
var circleVSDemo =function (layer,hpid){

}
//                                  _____________________________________
//                                 | circleEvent: 是否框选事件：1为是，0为否
//                                 | circleFunction:  框选事件调用函数名称
//                                 | circleWindowH: 框选事件弹出窗口高度
//                                 | circleWindowTitle: 框选事件弹出窗口标题名称
//                                 | circleWindowW:  框选事件弹出窗口宽度
//                                 | clickEvent: 是否单击事件：1为是，0为否
//                                 | clickFunction: 单击事件调用函数名称
//  layer参数列表                  | clickWindowH: 单击事件弹出窗口高度
//                                 | clickWindowTitle:单击事件弹出窗口标题名称
//                                 | clickWindowW: 单击事件弹出窗口宽度
//                                 | flashH: flash的高度
//                                 | flashName: flash的名称
//                                 | flashW: flash的宽度
//                                 | layerCodeGis: GIS图层的名称
//                                 | layerCodeJs: 图层的编码
//                                 | layerNameJs: 图层的名字
//                                 | lstSystemRule: Array[0]
//                                 | pop:是否存在飘窗：1为是，0为否
//                                 | popWindowH:飘窗的高度
//                                 | popWindowW: 飘窗的宽度
//                                  --------------------------------------
//     hpid设备的hpid
//     feature GIS图层的相关信息  包括点击点的坐标  图层名称 以及点击的hpid
var clickDMDemo =function (layer,hpid,feature){
    debugger;
    //调用弹窗的方法名称
    //g_addPopUp(feature,弹出名称,页面的地址可以用iframe加载);
}
//单击电梯
var clickDTXT =function (layer,hpid,feature){
	if(!isEmpty(layer.flashName) && !isEmpty(layer.flashH) && !isEmpty(layer.flashW)){
		var url = "../extendGis/toFlashDTXT?hpid="+hpid+"&flashH="+layer.flashH+"&flashW="+layer.flashW+"&flashName="+layer.flashName+"&r="+Math.random();
		quick_dialog("gis_click_dlg",layer.clickWindowTitle,url,layer.clickWindowW,layer.clickWindowH);
	}else{
		alert("未在flash配置表里配置flash信息");
	}
}
//单击远程抄表
var clickYCCB =function (layer,hpid,feature){
	g_addPopUp(feature,layer.clickWindowTitle,'<iframe height="'+layer.clickWindowH+'"  width="'+layer.clickWindowW+'" src="../../../extendGis/toYCCB?hpid='+hpid+'&r='+Math.random()+'" frameborder="0" scrolling="no" allowtransparency="true"></iframe>');
}

//单击配电柜
var clickPTD =function (layer,hpid,feature){
	g_addPopUp(feature,layer.clickWindowTitle,'<iframe height="'+layer.clickWindowH+'"  width="'+layer.clickWindowW+'" src="../../../extendGis/toPTD?hpid='+hpid+'&r='+Math.random()+'" frameborder="0" scrolling="no" allowtransparency="true"></iframe>');
}

//单击远程抄表
var clickVRV =function (layer,hpid,feature){
	g_addPopUp(feature,layer.clickWindowTitle,'<iframe height="'+layer.clickWindowH+'"  width="'+layer.clickWindowW+'" src="../../../extendGis/toVRV?hpid='+hpid+'&r='+Math.random()+'" frameborder="0" scrolling="no" allowtransparency="true"></iframe>');
}

//单击门禁
//var clickMJ =function (layer,hpid,feature){
//	g_addPopUp(feature,layer.clickWindowTitle,'<iframe height="'+layer.clickWindowH+'"  width="'+layer.clickWindowW+'" src="../../../extendGis/toMJ?hpid='+hpid+'&r='+Math.random()+'" frameborder="0" scrolling="no" allowtransparency="true"></iframe>');
//}