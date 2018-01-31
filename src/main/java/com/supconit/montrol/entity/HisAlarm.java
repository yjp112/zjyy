package com.supconit.montrol.entity;

import hc.base.domains.LongId;

public class HisAlarm extends LongId {
	/**
	 * 字段名称 serialVersionUID
	 * 字段类型 long
	 * 字段描述 
	 */
	private static final long serialVersionUID = 6492900551400884100L;
	private Long id;
	/**报警时间**/
	private String alarmTimeDate;
	/**位号名**/
	private String tagName;
	/**位号描述**/
	private String tagDesc;
	/** 确认状态 **/
	private int ackState;
	/**位号类型**/
	private int tagType;
	/**报警描述**/
	private String alarmDesc;
	/**报警类型描述 HH、H、LL、L、PRIN、NRIN**/
	private String alarmTypeDesc;
	/**优先级**/
	private int wServerity;
	/** 报警分区 **/
	private int fieldID;
	/** 报警分组 **/
	private int groupID;
	/** 消失标志 **/
	private int disappFlag;
	/**确认时间**/
	private String confirmTime;
	/**消失时间**/
	private String disappearTime;
	/**报警起始时间**/
	private String alarmStartTime;
	/**报警结束时间**/
	private String alarmEndTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAlarmTimeDate() {
		return alarmTimeDate;
	}
	public void setAlarmTimeDate(String alarmTimeDate) {
		this.alarmTimeDate = alarmTimeDate;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public String getTagDesc() {
		return tagDesc;
	}
	public void setTagDesc(String tagDesc) {
		this.tagDesc = tagDesc;
	}
	public int getAckState() {
		return ackState;
	}
	public void setAckState(int ackState) {
		this.ackState = ackState;
	}
	public int getTagType() {
		return tagType;
	}
	public void setTagType(int tagType) {
		this.tagType = tagType;
	}
	public String getAlarmDesc() {
		return alarmDesc;
	}
	public void setAlarmDesc(String alarmDesc) {
		this.alarmDesc = alarmDesc;
	}
	public String getAlarmTypeDesc() {
		return alarmTypeDesc;
	}
	public void setAlarmTypeDesc(String alarmTypeDesc) {
		this.alarmTypeDesc = alarmTypeDesc;
	}
	public int getwServerity() {
		return wServerity;
	}
	public void setwServerity(int wServerity) {
		this.wServerity = wServerity;
	}
	public int getFieldID() {
		return fieldID;
	}
	public void setFieldID(int fieldID) {
		this.fieldID = fieldID;
	}
	public int getGroupID() {
		return groupID;
	}
	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}
	public int getDisappFlag() {
		return disappFlag;
	}
	public void setDisappFlag(int disappFlag) {
		this.disappFlag = disappFlag;
	}
	public String getConfirmTime() {
		return confirmTime;
	}
	public void setConfirmTime(String confirmTime) {
		this.confirmTime = confirmTime;
	}
	public String getDisappearTime() {
		return disappearTime;
	}
	public void setDisappearTime(String disappearTime) {
		this.disappearTime = disappearTime;
	}
	public String getAlarmStartTime() {
		return alarmStartTime;
	}
	public void setAlarmStartTime(String alarmStartTime) {
		this.alarmStartTime = alarmStartTime;
	}
	public String getAlarmEndTime() {
		return alarmEndTime;
	}
	public void setAlarmEndTime(String alarmEndTime) {
		this.alarmEndTime = alarmEndTime;
	}	
}
