/**
 *定义气泡
 */
var g_tilertmap=function(divid,tilerservice){
	var tm=this;
	var map=null;
	var mapBounds = new OpenLayers.Bounds( 0.0, -4242.0, 6000.0, 0.0);
	OpenLayers.IMAGE_RELOAD_ATTEMPTS = 3;
	
	var stylemap=new OpenLayers.StyleMap({
		"default": new OpenLayers.Style({
			fillOpacity:0,
			strokeWidth: 0
		}),
		"select": new OpenLayers.Style({
			fillColor: "#fccb07",
			fillOpacity:0.2,
			strokeColor: "#fccb07",
			strokeWidth: 1
		}),
		"temporary":new OpenLayers.Style({
			fillColor: "#fccb07",
			fillOpacity:0.2,
			strokeColor: "#fccb07",
			strokeWidth: 1
		})
	});

	var selectCtrl;
	function init(){
		var options = {
			controls: [new OpenLayers.Control.HCPanZoomBar(),
			new OpenLayers.Control.Navigation({dragPanOptions:{enableKinetic:true},defaultDblClick:null})],
			maxExtent: new OpenLayers.Bounds(  0.0, -4242.0, 6000.0, 0.0 ),
			maxResolution: 32.000000,
			numZoomLevels: 6
			};
		map = new OpenLayers.Map(divid, options);
		
		var layer = new OpenLayers.Layer.TMS( "TMS Layer",tilerservice,
			{  url: '', serviceVersion: '.', layername: '.', alpha: true,
				type: 'png', getURL: overlay_getTileURL 
			});
		map.addLayer(layer);		
		var vectors = new OpenLayers.Layer.Vector("vector",{styleMap: stylemap});		
		map.addLayers([vectors]);

		var feature = new OpenLayers.Feature.Vector(
			OpenLayers.Geometry.fromWKT(
				"POLYGON((1228 -2194,1273 -2548,1490 -2623,1532 -2600,1593 -2571,1655 -2560,1719 -2557,1783 -2568,1841 -2585,1900 -2606,1955 -2631,2001 -2652,2050 -2684,2093 -2714,2136 -2746,2160 -2767,2351 -2646,2342 -2561,2301 -2541,2311 -2525,2293 -2239,1854 -2082,1849 -2087,1827 -2078,1821 -2008,1776 -1990,1768 -1951,1759 -1947,1701 -1999,1703 -2026,1626 -1999,1228 -2194))"
			),{'id':'AA'}
		);
		var feature1 = new OpenLayers.Feature.Vector(
			OpenLayers.Geometry.fromWKT(
				"POLYGON((1881 -1549,1849 -1572,1849 -1584,1833 -1597,1868 -2009,1876 -2015,1921 -2032,1932 -2023,1936 -2009,1943 -1990,1953 -2005,1974 -2021,2027 -2043,2071 -2061,2154 -2024,2127 -1617,2124 -1611,2083 -1595,2079 -1598,2068 -1593,2073 -1590,2059 -1584,2053 -1588,2043 -1583,2045 -1580,2007 -1565,2002 -1569,1992 -1565,1998 -1559,1982 -1555,1976 -1559,1966 -1554,1971 -1550,1930 -1536,1926 -1539,1914 -1535,1892 -1553,1881 -1549))"
			),{'id':'BB'}
		);		
		var feature2 = new OpenLayers.Feature.Vector(
			OpenLayers.Geometry.fromWKT(
				"POLYGON((3959 -2152,3870 -2249,3851 -2242,3822 -2277,3817 -2557,3904 -2603,3980 -2632,4188 -2716,4214 -2696,4243 -2711,4305 -2694,4416 -2573,4426 -2314,3976 -2141,3959 -2152))"
			),{'id':'CC'}
		);
		
		var feature3 = new OpenLayers.Feature.Vector(
			OpenLayers.Geometry.fromWKT(
				"POLYGON((2132 -1658,2364 -1748,2364 -1824,2265 -1920,2144 -1878,2132 -1658))"
			),{'id':'DD'}
		);		
		var feature4 = new OpenLayers.Feature.Vector(
			OpenLayers.Geometry.fromWKT(
				"POLYGON((2508 -1281,2409 -1283,2389 -1299,2393 -1417,2377 -1429,2379 -1457,2397 -1465,2411 -1785,2438 -1793,2457 -1791,2470 -1779,2502 -1790,2503 -1808,2528 -1790,2553 -1786,2573 -1794,2578 -1824,2566 -1835,2567 -1846,2595 -1859,2627 -1872,2718 -1905,2890 -1963,2949 -1915,2950 -1811,2970 -1819,2981 -1816,2996 -1823,2999 -1830,3017 -1835,3024 -1830,3042 -1840,3061 -1853,3072 -1852,3074 -1846,3081 -1845,3080 -1835,3165 -1819,3166 -1809,3161 -1800,3165 -1800,3165 -1795,3162 -1787,3198 -1775,3199 -1427,3192 -1424,3182 -1397,3035 -1344,3034 -1329,2953 -1300,2939 -1309,2905 -1298,2881 -1319,2835 -1304,2761 -1362,2760 -1373,2508 -1281))"
			),{'id':'EE'}
		);
		var feature5 = new OpenLayers.Feature.Vector(
			OpenLayers.Geometry.fromWKT(
				"POLYGON((2715 -1080,2550 -1210,2554 -1295,2759 -1369,2760 -1360,2834 -1301,2881 -1318,2904 -1297,2937 -1307,2952 -1298,3036 -1327,3036 -1342,3153 -1385,3183 -1394,3194 -1420,3202 -1427,3201 -1560,3528 -1673,3562 -1647,3560 -1621,3647 -1546,3648 -1519,3258 -1380,3264 -1375,3216 -1359,3210 -1359,3211 -1344,3253 -1323,3210 -1321,3173 -1311,2932 -1228,2899 -1199,2890 -1219,2872 -1236,2858 -1230,2852 -1235,2810 -1218,2808 -1113,2715 -1080))"
			),{'id':'FF'}
		);		
		var feature6 = new OpenLayers.Feature.Vector(
			OpenLayers.Geometry.fromWKT(
				"POLYGON((2953 -2291,2891 -2356,2853 -2341,2831 -2363,2790 -2348,2693 -2445,2704 -2544,2647 -2600,2650 -2645,2491 -2805,2509 -2946,2445 -3010,2452 -3069,2443 -3079,2486 -3103,2470 -3123,2569 -3169,2579 -3266,2863 -3397,2821 -3437,2856 -3456,2858 -3493,3292 -3697,3356 -3621,3480 -3678,3479 -3711,3742 -3800,3853 -3646,4018 -3718,4084 -3642,4123 -3659,4126 -3702,4619 -3925,4642 -3886,4687 -3907,4862 -3631,4864 -3604,4822 -3585,4836 -3358,4506 -3220,4423 -3343,4182 -3240,4200 -3218,4045 -3153,4044 -2865,4030 -2852,4007 -2837,4007 -2811,3620 -2653,3632 -2636,3543 -2597,3517 -2520,3391 -2469,3273 -2491,3230 -2474,3214 -2487,3197 -2481,3191 -2387,2953 -2291))"
			),{'id':'GG'}
		);		
		var feature7 = new OpenLayers.Feature.Vector(
			OpenLayers.Geometry.fromWKT(
				"POLYGON((3164 -1788,3238 -1764,3305 -1736,3362 -1705,3398 -1718,3419 -1698,3478 -1714,3494 -1722,3496 -1737,3526 -1705,3618 -1726,3634 -2064,3906 -2155,3909 -2200,3869 -2248,3853 -2241,3821 -2273,3821 -2438,3739 -2517,3696 -2519,3696 -2548,3680 -2607,3612 -2625,3548 -2599,3518 -2521,3392 -2468,3276 -2489,3253 -2480,3243 -2339,2984 -2252,2982 -2241,3042 -2189,3024 -1950,3020 -1926,3030 -1926,3030 -1909,3078 -1902,3074 -1847,3083 -1837,3168 -1818,3164 -1788))"
			),{'id':'HH'}
		);		
		var feature8 = new OpenLayers.Feature.Vector(
			OpenLayers.Geometry.fromWKT(
				"POLYGON((3233 -407,3161 -432,3090 -471,3011 -558,3010 -1048,3025 -1044,3029 -1203,3864 -1476,3889 -1464,3938 -1412,3954 -1386,3970 -1343,3973 -1258,3984 -1207,4008 -813,3999 -757,3967 -702,3696 -808,3694 -940,3559 -936,3569 -602,3571 -573,3559 -531,3526 -483,3479 -443,3442 -420,3443 -404,3421 -374,3389 -362,3341 -354,3305 -354,3267 -366,3246 -382,3236 -401,3233 -407))"
			),{'id':'II'}
		);

		vectors.addFeatures([feature,feature1,feature2,feature3,feature4,feature5,feature6,feature7,feature8]);

		map.zoomToExtent( mapBounds );
		
		//map.addControl(new OpenLayers.Control.MousePosition());
		
		var highlightCtrl = new OpenLayers.Control.SelectFeature(vectors, {
			hover: true,
			highlightOnly: true,
			renderIntent: "temporary"
		});

		selectCtrl = new OpenLayers.Control.SelectFeature(vectors,
			{clickout: true,onSelect: function(feature){tm.click(feature.attributes.id)}}
		);

		map.addControl(highlightCtrl);
		map.addControl(selectCtrl);

		highlightCtrl.activate();
		selectCtrl.activate();
		
		map.addControl(new OpenLayers.Control.EditingToolbar(vectors));
		map.zoomTo(3);
	}

	this.click=function(id) {
		//alert("选中楼id："+id);
	}
	
	this.unselect=function(){
		selectCtrl.unselectAll();
	}
	
	this.destroy=function(){
		if(map!=null){
			map.destroy();
			map=null;
			mapBounds=null;
			stylemap=null;
		}
	}

	function overlay_getTileURL(bounds) {
		var res = this.map.getResolution();
		var x = Math.round((bounds.left - this.maxExtent.left) / (res * this.tileSize.w));
		var y = Math.round((bounds.bottom - this.maxExtent.bottom) / (res * this.tileSize.h));
		var z = this.map.getZoom();
		if (x >= 0 && y >= 0) {
			return this.url + z + "/" + x + "/" + y + "." + this.type;				
		} else {
			return this.url +"none.png";
		}
	}	
	init();
	//return map;
}