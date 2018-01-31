package com.supconit.montrol.entity;

import hc.base.domains.LongId;

public class HisAlarmDailyReport extends LongId{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**报警时间**/
	private String censusTime;
	/**位号名**/
	private String tagName;
	/**报警数量**/
	private int alarmNum;
	/**位号描述**/
	private String tagDesc;
	/**位号类型**/
	private int tagType;
	/**设备名称**/
	private String deviceName;
	/**设备位置**/
	private String deviceLocation;
	
	
	/** Getter and Setter **/
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCensusTime() {
		return censusTime;
	}
	public void setCensusTime(String censusTime) {
		this.censusTime = censusTime;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public int getAlarmNum() {
		return alarmNum;
	}
	public void setAlarmNum(int alarmNum) {
		this.alarmNum = alarmNum;
	}
	public String getTagDesc() {
		return tagDesc;
	}
	public void setTagDesc(String tagDesc) {
		this.tagDesc = tagDesc;
	}
	public int getTagType() {
		return tagType;
	}
	public void setTagType(int tagType) {
		this.tagType = tagType;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getDeviceLocation() {
		return deviceLocation;
	}
	public void setDeviceLocation(String deviceLocation) {
		this.deviceLocation = deviceLocation;
	}
	
	
	
	
}
