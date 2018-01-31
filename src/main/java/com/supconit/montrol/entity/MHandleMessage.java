package com.supconit.montrol.entity;

public class MHandleMessage {
    private long alarmId;
    private String handlePerson;
    private String handleType;
    private Integer handleState;

    public long getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(long alarmId) {
        this.alarmId = alarmId;
    }

    public String getHandlePerson() {
        return handlePerson;
    }

    public void setHandlePerson(String handlePerson) {
        this.handlePerson = handlePerson;
    }

    public String getHandleType() {
        return handleType;
    }

    public void setHandleType(String handleType) {
        this.handleType = handleType;
    }

    public Integer getHandleState() {
        return handleState;
    }

    public void setHandleState(Integer handleState) {
        this.handleState = handleState;
    }
}
