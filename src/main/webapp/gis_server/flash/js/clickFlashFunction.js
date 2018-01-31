function clickFlash(btnName){
    if("${type}"=="MF"){
        var p=window;while(p!=p.parent){p=p.parent; }
        p.newDialog("mf", "load:$!{rc.contextPath}/base/mortuary/edit?code="+encodeURIComponent(btnName), btnName+"号冷冻柜", false,600,500);
    }else{
        var n="";
        if("${type}"=="CH"){
            n="CH_CC.swf";
        }else if("${type}"=="GL"){
            n="GL_HB.swf";
        }
        window.location.href="${rc.contextPath}/gis/indexFlash/${type}?flashFileName="+n;
    }
}
