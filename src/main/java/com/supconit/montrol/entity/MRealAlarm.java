package com.supconit.montrol.entity;

import hc.base.domains.LongId;

import java.util.Date;

public class MRealAlarm extends LongId{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date startTime;
	private Date endTime;
	private long deviceId;
	private String deviceName;
    private String deviceCode;
	private long tagId;
    private String tagCode;
	private String alarmTime;
	private String alarmRemark;
	private String alarmLevel;
	private String alarmData;
	private long alarmState;
	private Date changeTime;
	private String processTime;
	private String showAlarmState;
	private long hisAlarmId;
	private String levelIco;
	private long index;
	private String lastAlarmTime;
	private String processPerson;
	private String processType;
	private long processState;
	private String handleTime;
	private long alarmRank;
	private String showProcessState = "未处理";
	private String colorful;
	private long areaId;
	private String areaStr;
    private String handleState;
	
	
	public long getAreaId() {
		return areaId;
	}

	public void setAreaId(long areaId) {
		this.areaId = areaId;
	}

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

	public String getLastAlarmTime() {
		return lastAlarmTime;
	}

	public void setLastAlarmTime(String lastAlarmTime) {
		if((this.alarmState == 2 || this.alarmState == 0)&&lastAlarmTime.length()>19){
			this.lastAlarmTime = lastAlarmTime.substring(0,19);
		}else{
			this.lastAlarmTime = "";
		}
		
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

	public String getHandleTime() {
		return handleTime;
	}

	public void setHandleTime(String handleTime) {
		this.handleTime = handleTime;
	}

	public void setLevelIco(String levelIco) {
		this.levelIco = levelIco;
	}

	public long getIndex() {
		return index;
	}

	public void setIndex(long index) {
		this.index = index;
	}

	public String getLevelIco() {
		return levelIco;
	}

	public long getHisAlarmId() {
		return hisAlarmId;
	}
	public void setHisAlarmId(long hisAlarmId) {
		this.hisAlarmId = hisAlarmId;
	}
	public void setShowAlarmState(String showAlarmState) {
		this.showAlarmState = showAlarmState;
	}
	public String getShowAlarmState() {
		return showAlarmState;
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
	public String getAlarmLevel() {
		return alarmLevel;
	}
	public void setAlarmLevel(String alarmLevel) {
		this.alarmLevel = alarmLevel;

	}
	public String getAlarmData() {
		return alarmData;
	}
	public void setAlarmData(String alarmData) {
		this.alarmData = alarmData;
	}
	public long getAlarmState() {
		return alarmState;
	}
	public void setAlarmState(long alarmState) {
		this.alarmState = alarmState;
		if(this.alarmState == 1)
			this.showAlarmState = "正在报警";
		else
			this.showAlarmState = "已恢复";
		
		if(alarmState != 1){
			this.colorful = "";
		}
		
		if(alarmState == 1){
			this.lastAlarmTime = "";
		}
	}
	public Date getChangeTime() {
		return changeTime;
	}
	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}
	public String getProcessTime() {
		return processTime;
	}
	public void setProcessTime(String processTime) {
		this.processTime = processTime;
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

    public String getTagCode() {
        return tagCode;
    }

    public void setTagCode(String tagCode) {
        this.tagCode = tagCode;
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
