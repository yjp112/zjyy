使用ECHARTS封装须知：
  ##引入对应的echart文件
    <script type="text/javascript" src="../esl.js"></script>
    <script type="text/javascript" src="../echartsUtil.js"></script>

  ## 配置全局变量 var contextPath = $!rc.contextPath;

  ##配置图标 option ，调用 initEcharts 方法，方法说明见echartsUtil.js。

  ##如果有多个图标，获取各个图标echarts实例的方法 option.echart

  ##web环境下需要将echartsUtil.js 中的offLine参数改为false


## demo演示的是两个图表的换肤