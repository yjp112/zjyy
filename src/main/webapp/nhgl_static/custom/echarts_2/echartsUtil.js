var echartsHome;
var offLine = false;
var contextPath = GlobalVars.webCtx;
if (offLine) {
    echartsHome = "..";
} else {
    echartsHome = contextPath + "/nhgl_static/custom/echarts_2";
}

echartsLocation = echartsHome + "/echarts";
themeLocation = echartsHome + "/theme";
require.config({
    paths: {
        echarts: echartsLocation,
        'echarts/chart/line': echartsLocation,
        'echarts/chart/bar': echartsLocation,
        'echarts/chart/scatter': echartsLocation,
        'echarts/chart/k': echartsLocation,
        'echarts/chart/pie': echartsLocation,
        'echarts/chart/radar': echartsLocation,
        'echarts/chart/map': echartsLocation,
        'echarts/chart/chord': echartsLocation,
        'echarts/chart/force': echartsLocation,
        'echarts/chart/gauge': echartsLocation,
        'echarts/chart/funnel': echartsLocation,
        'theme': themeLocation
    }
});
/**
 * 相关样式
 * @type {{MACARONS: string, INFOGRAPHIC: string, SHINE: string, DARK: string, BLUE: string, GREEN: string, RED: string, GRAY: string, DEFAULT: string}}
 */
var THEME = {
    MACARONS: "macarons",
    INFOGRAPHIC: "infographic",
    SHINE: "shine",
    DARK: "dark",
    BLUE: "blue",
    GREEN: "green",
    RED: "red",
    GRAY: "gray",
    CUSTOM_DARK:"custom_dark",
    DEFAULT: "default"
};
/**
 * 想要获取初始化后的echarts可以通过option.echart获取
 * @param divId  展示图形报表的divId
 * @param option 图形报表相关参数
 * @param theme  图形报表的样式，默认可以忽略，类型为THEME
 */
function initEcharts(divId, option,callbackFun ,theme) {
   
	 if(typeof theme =="undefined"){
        theme = THEME.CUSTOM_DARK;
    }
    require(
        [
            'echarts',
            'theme/'+theme,
            'echarts/chart/line',
            'echarts/chart/bar',
            'echarts/chart/scatter',
            'echarts/chart/k',
            'echarts/chart/pie',
            'echarts/chart/radar',
            'echarts/chart/force',
            'echarts/chart/chord',
            'echarts/chart/gauge',
            'echarts/chart/funnel'
        ],
        function (echarts, defaultTheme) {
           chart = echarts.init(document.getElementById(divId), defaultTheme);
            chart.setOption(option, true);
//            if("mixzm" == divId.substring(0,5))
//            	chart.on('click', eConsole);
            //option.echart = chart;
            if(callbackFun){
            	callbackFun(chart,option);
            }
        }
    );
   // return charts;

    //setTimeout(function(){},500);

   // return require('echarts').init(document.getElementById(divId));

}
/**
 * 更改样式
 * @param echarts
 * @param value
 */
function changeTheme(echarts, value) {
    var theme = value;
    require(["theme/" + theme], function (tarTheme) {
        setTimeout(function () {
            echarts.hideLoading();
            echarts.setTheme(tarTheme);
        }, 500);
    });
}

