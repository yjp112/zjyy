package com.supconit.nhgl.alarm.device.entities;

import java.util.Date;
import java.util.List;

import hc.base.domains.LongId;


public class IRealAlarm extends LongId{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> roleAttention;
	private Date startTime;
	private Date endTime;
	private long objectType;
	private String objectId;
	private long indicatorId;
	private String itemId;
	private long tagId;
	private Date alarmTime;
	private String alarmRemark;
	private long alarmLevel;
	private String alarmData;
	private long alarmState;
	private Date changeTime;
	private String processTime;
	private String showAlarmState;
	private String showAlarmObject;
	private long hisAlarmId;
	private String levelIco;
	private long index;
	private Date lastAlarmTime;
	private String attention;
	private String processPerson;
	private String processType;
	private long processState;
	private String handleTime;
	private long alarmRank;
	private String showProcessState = "未处理";
	private String colorful;
	private long areaId;
	private String showAreaStr;
	private String alarmType;
	private long typeId;
	private long parentType;
	
	
	public long getParentType() {
		return parentType;
	}

	public void setParentType(long parentType) {
		this.parentType = parentType;
	}

	public String getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}

	public long getTypeId() {
		return typeId;
	}

	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}

	public long getAreaId() {
		return areaId;
	}

	public void setAreaId(long areaId) {
		this.areaId = areaId;
	}

	public String getShowAreaStr() {
		return showAreaStr;
	}

	public void setShowAreaStr(String showAreaStr) {
		this.showAreaStr = showAreaStr;
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
			this.levelIco = "<i class=\"iconfont icon-info-sign info-sign1\"></i>";
			if(this.alarmState == 1 || this.alarmState == 0)
			this.colorful = "alarm-level-colorful-sign1";
		}else if(alarmRank == 2){
			this.levelIco = "<i class=\"iconfont icon-info-sign info-sign2\"></i>";
			if(this.alarmState == 1 || this.alarmState == 0)
			this.colorful = "alarm-level-colorful-sign2";
		}else if(alarmRank == 3){
			this.levelIco = "<i class=\"iconfont icon-info-sign info-sign3\"></i>";
			if(this.alarmState == 1 || this.alarmState == 0)
			this.colorful = "alarm-level-colorful-sign3";
		}else{
			this.levelIco = "<i class=\"iconfont icon-info-sign info-sign4\"></i>";
			if(this.alarmState == 1 || this.alarmState == 0)
			this.colorful = "alarm-level-colorful-sign4";
		}
			
	}

	public String getShowProcessState() {
		return showProcessState;
	}

	public Date getLastAlarmTime() {
		return lastAlarmTime;
	}

	public void setLastAlarmTime(Date lastAlarmTime) {
		if(this.alarmState == 2 || this.alarmState == 0){
			this.lastAlarmTime = lastAlarmTime;
		}else{
			this.lastAlarmTime = null;
		}
		
	}

	public String getAttention() {
		return attention;
	}

	public void setAttention(String attention) {
		this.attention = attention;
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

	public List<String> getRoleAttention() {
		return roleAttention;
	}
	public void setRoleAttention(List<String> roleAttention) {
		this.roleAttention = roleAttention;
	}
	public long getHisAlarmId() {
		return hisAlarmId;
	}
	public void setHisAlarmId(long hisAlarmId) {
		this.hisAlarmId = hisAlarmId;
	}
	public String getShowAlarmObject() {
		return showAlarmObject;
	}
	public void setShowAlarmObject(String showAlarmObject) {
		this.showAlarmObject = showAlarmObject;
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

	public void setShowProcessState(String showProcessState) {
		this.showProcessState = showProcessState;
	}

	public long getObjectType() {
		return objectType;
	}
	public void setObjectType(long objectType) {
		this.objectType = objectType;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public long getIndicatorId() {
		return indicatorId;
	}
	public void setIndicatorId(long indicatorId) {
		this.indicatorId = indicatorId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public long getTagId() {
		return tagId;
	}
	public void setTagId(long tagId) {
		this.tagId = tagId;
	}
	public Date getAlarmTime() {
		return alarmTime;
	}
	public void setAlarmTime(Date alarmTime) {
		this.alarmTime = alarmTime;
	}
	public long getAlarmLevel() {
		return alarmLevel;
	}
	public void setAlarmLevel(long alarmLevel) {
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
			this.lastAlarmTime = null;
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

}
