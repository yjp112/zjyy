package com.supconit.nhgl.evaluate.entities;

/**
 * Created by zhangzhaoyun on 14-6-23.
 */
public class MonitorObjScore  {

    private String deviceCode;
    private String deviceName;
    private String location;
    private String department;

    private int score;
    private String executeTime;

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(String executeTime) {
        this.executeTime = executeTime;
    }
}
