var wmsAjaxQuery=function(){

	var ajxQuery=this;
	/**
	 *URL请求超时
	 */
	this.timeout=3000;
	
	/**
	 *获得图层的URL
	 *参数
	 *layer WMS图层对象
	 */
	function getLayerUrl(layer){
		return layer.url;
	}
	/**
	 *获得图层名
	 *参数
	 *layer WMS图层对象
	 */
	function getLayerStr(layer){
		return layer.params.LAYERS;
	}
	
	/**
	 *url查询
	 *参数
	 *url ： String 服务地址
	 *layers ：String 服务器图层名称
	 *async ：bool 同异步查询
	 */
	function urlQuery(url,layers,async){
		var fs=new Array()
		var params = {
			REQUEST: "GetFeature",  //查询方式
			srsName: 'EPSG:4326',  //投影
			service: "WFS",       //服务类型
			version: "1.0.0",
			cql_filter: ajxQuery.filter,  //CQL 过滤
			typeName:layers
		};
		if(typeof params == 'string') {
			params = OpenLayers.Util.getParameters(params);
		}	
		var request = OpenLayers.Request.GET({
			url:url,
			params:params,
			success:function (response){
				var gmlParse = new OpenLayers.Format.GML();
				var features= gmlParse.read(response.responseText);
				fs=features;
				ajxQuery.onComplete(features);
			},
			failure:function (response){
				ajxQuery.onFailure();
			},
			async: async,
			timeout:ajxQuery.timeout  //超时
		});
		return fs;
	}
	
	/**
	 *filter过滤条件转CQL
	 *参数
	 *filter 查询条件
	
	function filterToCQL(filter){
		var filterstr="";
		if((typeof filter.CLASS_NAME=="OpenLayers.Filter.Spatial")||(typeof filter.CLASS_NAME=="OpenLayers.Filter.Logical")||(typeof filter.CLASS_NAME=="OpenLayers.Filter.Comparison")){
			filterstr=filter.toString();
		}else if(typeof filter == "string"){
			filterstr=filter;
		}
		return filterstr;
	} */
	
	/**
	 *多态查询方法
	 *根据参数类型调用不同的方法
	 *参数
	 *para1 使用URL查询时，为URL，使用wms图层时为图层对象
	 *para2 使用URL查询时，为服务器图层名，使用wms图层时无效
	 */
	this.query=function(para1,para2){
		if((typeof para1.CLASS_NAME!="undefined")){
			para2=getLayerStr(para1);
			para1=getLayerUrl(para1);
		}
		if(checkeURL(para1)&&(typeof para2 == "string")){
			urlQuery(para1,para2,true) //URL查询
		}else{
			alert("参数错误！");
		}
	}
	
	/**
	 *同步查询
	 *参数
	 *para1 使用URL查询时，为URL，使用wms图层时为图层对象
	 *para2 使用URL查询时，为服务器图层名，使用wms图层时无效
	 */
	this.tbQuery=function(para1,para2){
		var fs=new Array();
		if(typeof para1.CLASS_NAME!="undefined"){
			para2=getLayerStr(para1);
			para1=getLayerUrl(para1);
		}
		if(checkeURL(para1)&&(typeof para2 == "string")){
			fs=urlQuery(para1,para2,false) //URL查询
		}
		return fs;
	}
	
	/**
	 *查询条件
	 */
	this.filter=null;
	
	/**
	 *查询成功
	 */
	this.onComplete=function(features){
	
	}
	
	/**
	 *查询失败
	 */
	this.onFailure=function(){
	}
	
	/**
	 *判断URL是否有效
	 *参数
	 *URL:URL字符串
	 */
	function checkeURL(URL){   
		var str=URL;
		var Expression=/([\w-]+\.)+[\w-]+(\/[\w- .\/?%&=]*)?/;    
		var objExp=new RegExp(Expression);   
		if(str.indexOf("localhost")){   
			str = str.replace("localhost","127.0.0.1");   
		}   
		if(objExp.test(str)==true){   
			return true;   
		}else{   
			return false;   
		}   
	}
}