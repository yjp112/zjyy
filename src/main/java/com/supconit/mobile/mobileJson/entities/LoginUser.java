package com.supconit.mobile.mobileJson.entities;

/**
 * Created by wangwei on 2016-5-11.
 */
public class LoginUser {

    private boolean useNumberPsd;
    private String token;
    private Long userId;
    private Long personId;

    public boolean isUseNumberPsd() {
        return useNumberPsd;
    }

    public void setUseNumberPsd(boolean useNumberPsd) {
        this.useNumberPsd = useNumberPsd;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }
}
