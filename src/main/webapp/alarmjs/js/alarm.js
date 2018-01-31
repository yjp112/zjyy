function m_round(){
	return new Date().getTime();
	
}
function m_newDialog(id, url, title, isLock, width, height, callback) {
    if (dialog.list[id ? id : "dialog_id"]) {
        dialog.list[id?id:"dialog_id"].show();
        return;
    }
    if(callback){
        dialog({
            id: id ? id : "dialog_id",
            content: 'load:'+url,
            title: title ? title : "标题",
            lock: isLock,
            width: width ? width : 300,
            height: height ? height : 500,
            ok: callback,
            okVal: '确定'

        });
    }else{
        dialog({
            id: id ? id : "dialog_id",
            content: 'load:'+url,
            title: title ? title : "标题",
            lock: isLock,
            width: width ? width : 300,
            height: height ? height : 500

        });
    }

}




function m_validateCallback(response, dataGridId) {
	alert("m_validatecallback");
    if (response.status == "success") {
        if (response.data.next) {
            if (response.data.next == ".") {//刷新datagrid
                if ($("#" + dataGridId)) {//操作datagrid
                    $("#" + dataGridId).flexReload();
                }

            } else if (response.data.next == "x") {//关闭dialog并刷新datagrid
            	alert("X");
                if (parent) {//dialog中有iframe
                    if (parent.$("#" + dataGridId)) {
                        parent.$("#" + dataGridId).flexReload();
                    }
                } else if ($("#" + dataGridId)) {
                    $("#" + dataGridId).flexReload();
                }
                closeDialog();//关闭dialog
            } if(response.data.next == 'r' ){

            } else {//关闭dialog并刷新页面

                if (parent) {//dialog中有iframe
                    if (parent.$(".mainContent")) {
                        parent.$(".mainContent").load(response.data.next);
                    }
                } else if ($(".mainContent")) {
                    $(".mainContent").load(response.data.next);
                }
                closeDialog();//关闭dialog
            }
        }
    }
}

function m_closeDialog() {
	dialog.focus&&dialog.focus.close();
}

var currRealAlarmID = 0;

//决定是否有新的报警信息进入,是否需要弹框
function m_alarmWarm(url,alarmfile,interval){
	$.ajax({
		type:'post',
		url:url + "/hl/montrol/alarm/realalarm/getRealAlarmMessage?currRealAlarmID="+currRealAlarmID,
		dataType:'json',
		success:function(msg){
			if(msg.isShow == 1){
				if(dialog.list["warmMessage"] != null){
					dialog.list["warmMessage"].close();
				}
				var containerId='';
				if(alarmfile){
					containerId=soundPlay(alarmfile)
				}
			    dialog({
			        content: 'load:'+url + "/montrol/alarm/realalarm/showWarmPage",
			        id:"warmMessage",
			        title: '报警',
			        skin: 'warn',
			        left: '100%',
			        top: '100%',
			        lock: false,
			        fixed: true,
			        max : false,
			        width: 330,
			        height: 250,
			        cache : false,
			        close: function(){
						stopFechAlarm();
						if(alarmfile){soundStop(containerId)};
							m_alarmWarm(url,alarmfile,interval);
						}
			    });
			}else{
				window.setTimeout(function(){m_alarmWarm(url,alarmfile,interval)},interval);
			}
		}
	});

}


function m_formatMessage(message, status) {
    if (status == 'ok') {
        return '<div class="ui-message-content"><p class="ui-tiptext ui-tiptext-success"><i class="iconfont icon-ok-sign"></i>成功</p><p class="ui-message-text">' + message + '</p></div>';
    } else {
        return '<div class="ui-message-content"><p class="ui-tiptext ui-tiptext-error"><i class="iconfont icon-remove-sign"></i>失败</p><p class="ui-message-text">' + message + '</p></div><div class="ui-message-btn"><button class="btn" type="button">确定</button></div>';
    }
}
function m_showSuccessMsg(msg){
$.message(m_formatMessage(msg,'ok'), $.message.TYPE_OK, 2, true,true);
}

function m_showErrorMsg(msg){
$.message(m_formatMessage(msg,'error'), $.message.TYPE_ERROR, 2, false,true);
}




function soundPlay(soundfile){
    var bkcolor = "000000";
    var playerId="player_"+new Date().getTime();
    var playerContainer="container_"+new Date().getTime();
    var playerHtml=[];
    if ( navigator.userAgent.toLowerCase().indexOf( "msie" ) != -1 ) {
        playerHtml.push('<bgsound id="'+playerId+'" src="'+soundfile+'" loop="-1">');
    }else if ( navigator.userAgent.toLowerCase().indexOf( "firefox" ) != -1 ) {
        playerHtml.push('<object id="'+playerId+'" data="'+soundfile+'" type="application/x-mplayer2" width="0" height="0">');
        playerHtml.push('<param name="filename" value="'+soundfile+'">');
        playerHtml.push('<param name="autostart" value="1">');
        playerHtml.push('<param name="playcount" value="infinite">');
        playerHtml.push('</object>');
    }
    else {
        playerHtml.push('<audio id="'+playerId+'" src="'+soundfile+'" autoplay="autoplay" loop="loop">');
        playerHtml.push('<object data="'+soundfile+'" type="application/x-mplayer2" width="0" height="0">');
        playerHtml.push('<param name="filename" value="'+soundfile+'">');
        playerHtml.push('<param name="autostart" value="1">');
        playerHtml.push('<embed height="2" width="2" src="'+soundfile+'" pluginspage="http://www.apple.com/quicktime/download/" type="video/quicktime" controller="false" controls="false" autoplay="true" autostart="true" loop="true" bgcolor="#'+bkcolor+'"><br>');
        playerHtml.push('</embed></object>');
        playerHtml.push('</audio>');
    }
    $('<span id="'+playerContainer+'" style="display:none">'+playerHtml.join('')+'</span>').appendTo('body');
    return playerContainer;
}
function soundStop(playerContainerId){
    $('#'+playerContainerId).remove();
}