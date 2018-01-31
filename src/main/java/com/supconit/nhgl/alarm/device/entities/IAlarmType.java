package com.supconit.nhgl.alarm.device.entities;


import java.util.ArrayList;
import java.util.List;

import hc.base.domains.LongId;


public class IAlarmType extends LongId{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 类别名称 **/
	private String alarmType;
	/** 父类别 **/
	private long parentType;
	/** 备注说明 **/
	private String remark;
	private String lowMessage;
	private long lowDay;
	private long lowHouse;
	// 1 采集 0 计算
	private long collectAlarm;
//	private List<MRelevanceTL> relevanceTL;
	private List<IAlarmType> children;
	private long areaId;
	private String areaName;
	
	private String parentTypeStr;
	
	public long getAreaId() {
		return areaId;
	}
	public void setAreaId(long areaId) {
		this.areaId = areaId;
	}
	public long getCollectAlarm() {
		return collectAlarm;
	}
	public void setCollectAlarm(long collectAlarm) {
		this.collectAlarm = collectAlarm;
	}
	public String getLowMessage() {
		return lowMessage;
	}
	public void setLowMessage(String lowMessage) {
		this.lowMessage = lowMessage;
		if(null != lowMessage && !lowMessage.equals("") && !lowMessage.trim().equals("0")){
			String[] pars = lowMessage.split(":");
			this.lowDay = Long.valueOf(pars[0]);
			this.lowHouse = Long.valueOf(pars[1]);
		}
	}
	public long getLowDay() {
		return lowDay;
	}
	public void setLowDay(long lowDay) {
		this.lowDay = lowDay;
		this.lowMessage = lowDay + ":"+lowHouse;
	}
	public long getLowHouse() {
		return lowHouse;
	}
	public void setLowHouse(long lowHouse) {
		this.lowHouse = lowHouse;
		this.lowMessage = lowDay + ":"+lowHouse;
	}
	public String getAlarmType() {
		return alarmType;
	}
	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}
	public long getParentType() {
		return parentType;
	}
	public void setParentType(long parentType) {
		this.parentType = parentType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
//	public List<MRelevanceTL> getRelevanceTL() {
//		return relevanceTL;
//	}
//	public void setRelevanceTL(List<MRelevanceTL> relevanceTL) {
//		this.relevanceTL = relevanceTL;
//	}
	public List<IAlarmType> getChildren() {
		return children;
	}
	
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public void setChildren(List<IAlarmType> children) {
		this.children = children;
	}

	
	public void addChildren(IAlarmType type){
		if(children == null)
			children = new ArrayList<IAlarmType>();
		children.add(type);
	}
	public String getParentTypeStr() {
		return parentTypeStr;
	}
	public void setParentTypeStr(String parentTypeStr) {
		this.parentTypeStr = parentTypeStr;
	}

	
	
}
