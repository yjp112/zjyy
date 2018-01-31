(function() {
	//样式(css)文件
	var cssFiles=['popstyle.css','tilermap.css'];
	//js文件
	var jsFiles=['supconjs/GisFramedCloud.js',
	             'supconjs/HCPanZoomBar.js',
	             'gisConfig.js',
				 "hightlight.js",
				 'gisDeviceControl.js',
				 "keyMap.js",
				 "gis.js",
				 "popup.js",
				 "tilermap.js"
	];
	
	var scriptName="Supconmap.js";
	
	//引用文件
	function initialize(){
		var r = new RegExp("(^|(.*?\\/))(" + scriptName + ")(\\?|$)"),
        s = document.getElementsByTagName('script'),
        src, m, l = "";
		for(var i=0, len=s.length; i<len; i++) {
			src = s[i].getAttribute('src');
			if(src) {
				m = src.match(r);
				if(m) {
					l = m[1];
					break;
				}
			}
		}
		var host = l + "core/";
		for(var i=0;i<cssFiles.length;i++){
			document.write("<link rel='stylesheet' href='"+host+cssFiles[i]+"' type='text/css'>");
		}
		for(var j=0;j<jsFiles.length;j++){
			document.write("<script src ='"+host+jsFiles[j]+"'></script>");
		}
	}
	//初始化执行
	initialize();	
})()
