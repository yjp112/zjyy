package com.supconit.montrol.entity;


import hc.base.domains.LongId;

public class DeviceAlarmLevel extends LongId {

	/**
	 * 字段名称 serialVersionUID
	 * 字段类型 long
	 * 字段描述 
	 */
	private static final long serialVersionUID = 5750671063554910877L;
	/*位号ID*/
	private Long tagId;
	/*报警类别*/
	private String alarmType;
	/*报警等级*/
	private Long alarmLevelId;
	/* 扩展1 */
	private String extension1;
	/* 扩展1 */
	private String extension2;
	public Long getTagId() {
		return tagId;
	}
	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}
	public String getAlarmType() {
		return alarmType;
	}
	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}
	public Long getAlarmLevelId() {
		return alarmLevelId;
	}
	public void setAlarmLevelId(Long alarmLevelId) {
		this.alarmLevelId = alarmLevelId;
	}
	public String getExtension1() {
		return extension1;
	}
	public void setExtension1(String extension1) {
		this.extension1 = extension1;
	}
	public String getExtension2() {
		return extension2;
	}
	public void setExtension2(String extension2) {
		this.extension2 = extension2;
	}
	
}
