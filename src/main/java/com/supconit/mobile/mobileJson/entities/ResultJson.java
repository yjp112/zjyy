package com.supconit.mobile.mobileJson.entities;

import javax.json.Json;

/**
 * Created by wangwei on 2016-5-11.
 */
public class ResultJson {

    private Long resultCode;//状态码
    private String resultMsg;//状态信息
    private Object resultContent;//用户信息


    public Long getResultCode() {
        return resultCode;
    }

    public void setResultCode(Long resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public Object getResultContent() {
        return resultContent;
    }

    public void setResultContent(Object resultContent) {
        this.resultContent = resultContent;
    }
}
