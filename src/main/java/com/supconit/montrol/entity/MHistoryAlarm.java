package com.supconit.montrol.entity;

import java.util.Date;

import hc.base.domains.LongId;

public class MHistoryAlarm extends LongId{
	private static final long serialVersionUID = 1L;
	private Date startTime;
	private Date endTime;
    private long deviceId;
    private String deviceName;
    private String deviceCode;
	private long tagId;
	private String alarmTime;
	private long alarmLevel;
	private String alarmData;
	private String ackTime;
	private String ignoreTime;
	private Date processTime;
	private String disappearTime;
	private String alarmRemark;
	private String showAlarmObject;
	private String levelIco;
	private long alarmState;
	private long alarmRank;
	private String showAlarmState;
	private String processPerson;
	private String processType;
	private long processState;
	private String showProcessState = "未处理"; 
	private long areaId;
    private String areaStr;
    private String handleState;
	
	public long getAreaId() {
		return areaId;
	}

	public void setAreaId(long areaId) {
		this.areaId = areaId;
	}


	private String colorful;
	public long getAlarmRank() {
		return alarmRank;
	}

	public String getColorful() {
		return colorful;
	}

	public void setColorful(String colorful) {
		this.colorful = colorful;
	}

	public void setAlarmRank(long alarmRank) {
        this.alarmRank = alarmRank;
        if(alarmRank == 1){
            this.levelIco = "color: #ff0000;";
            if(this.alarmState == 1 || this.alarmState == 0)
                this.colorful = "alarm-level-colorful-sign1";
        }else if(alarmRank == 2){
            this.levelIco = "color:#ffa500;";
            if(this.alarmState == 1 || this.alarmState == 0)
                this.colorful = "alarm-level-colorful-sign2";
        }else if(alarmRank == 3){
            this.levelIco = "color:#ffff00;";
            if(this.alarmState == 1 || this.alarmState == 0)
                this.colorful = "alarm-level-colorful-sign3";
        }else{
            this.levelIco = "color: #008000;";
            if(this.alarmState == 1 || this.alarmState == 0)
                this.colorful = "alarm-level-colorful-sign4";
        }
	}

	public String getShowProcessState() {
		return showProcessState;
	}

	public long getAlarmState() {
		return alarmState;
	}
	public void setAlarmState(long alarmState) {
		this.alarmState = alarmState;
		if(this.alarmState == 1)
			this.showAlarmState = "正在报警";
		else if(this.alarmState == 2)
			this.showAlarmState = "已恢复";
		else
			this.showAlarmState = "已消失";
		
		
		
		if(alarmState != 1){
			this.colorful = "";
		}
	}
	public String getShowAlarmState() {
		return showAlarmState;
	}
	public void setShowAlarmState(String showAlarmState) {
		this.showAlarmState = showAlarmState;
	}
	public String getProcessPerson() {
		return processPerson;
	}
	public void setProcessPerson(String processPerson) {
		this.processPerson = processPerson;
	}
	public String getProcessType() {
		return processType;
	}
	public void setProcessType(String processType) {
		this.processType = processType;
	}
	public long getProcessState() {
		return processState;
	}
	public void setProcessState(long processState) {
		this.processState = processState;
		if(processState == 1){
			this.showProcessState = "未处理";
		}else if(processState == 2){
			
			this.showProcessState = "处理中";
		}else if(processState == 3){
			
			this.showProcessState = "处理完成";
		}
	}
	public void setLevelIco(String levelIco) {
		this.levelIco = levelIco;
	}
	public String getLevelIco() {
		return levelIco;
	}
	public String getAlarmRemark() {
		return alarmRemark;
	}
	public void setAlarmRemark(String alarmRemark) {
		if(alarmRemark == null || alarmRemark.trim().equals("") || alarmRemark.trim().equals("null"))
			this.alarmRemark = "无";
		else
			this.alarmRemark = alarmRemark;
	}
	
	public void setAlarmRemark(String alarmRemark,boolean falg) {
			this.alarmRemark = alarmRemark;
	}
	public String getShowAlarmObject() {
		return showAlarmObject;
	}
	public void setShowAlarmObject(String showAlarmObject) {
		this.showAlarmObject = showAlarmObject;
	}

	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public long getTagId() {
		return tagId;
	}
	public void setTagId(long tagId) {
		this.tagId = tagId;
	}
	public String getAlarmTime() {
		return alarmTime;
	}
	public void setAlarmTime(String alarmTime) {
		this.alarmTime = alarmTime;
	}
	public long getAlarmLevel() {
		return alarmLevel;
	}
	public void setAlarmLevel(long alarmLevel) {
		this.alarmLevel = alarmLevel;
		if(alarmLevel == 73)
            this.levelIco = "color: #ff0000;";
		else if(alarmLevel == 74)
            this.levelIco = "color:#ffa500;";
		else if(alarmLevel == 75)
            this.levelIco = "color:#ffff00;";
		else
            this.levelIco = "color: #008000;";
	}
	public String getAlarmData() {
		return alarmData;
	}
	public void setAlarmData(String alarmData) {
		this.alarmData = alarmData;
	}
	public String getAckTime() {
		return ackTime;
	}
	public void setAckTime(String ackTime) {
		this.ackTime = ackTime;
	}
	public String getIgnoreTime() {
		return ignoreTime;
	}
	public void setIgnoreTime(String ignoreTime) {
		this.ignoreTime = ignoreTime;
	}
	public Date getProcessTime() {
		return processTime;
	}
	public void setProcessTime(Date processTime) {
		this.processTime = processTime;
	}
	public String getDisappearTime() {
		return disappearTime;
	}
	public void setDisappearTime(String disappearTime) {
		this.disappearTime = disappearTime;
	}

    public long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public void setShowProcessState(String showProcessState) {
        this.showProcessState = showProcessState;
    }

    public String getAreaStr() {
        return areaStr;
    }

    public void setAreaStr(String areaStr) {
        this.areaStr = areaStr;
    }

    public String getHandleState() {
        return handleState;
    }

    public void setHandleState(String handleState) {
        this.handleState = handleState;
    }
}
