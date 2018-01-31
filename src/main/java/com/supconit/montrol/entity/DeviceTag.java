package com.supconit.montrol.entity;


import hc.base.domains.LongId;

public class DeviceTag extends LongId {
	/**
	 * 字段名称 serialVersionUID
	 * 字段类型 long
	 * 字段描述 
	 */
	private static final long serialVersionUID = -7057120453537963880L;

	/* 监测对象编号 */
	private String monitorID;
	/* 位号名 */
	private String tagName;
	/* 位号类型 */
	private String tagType;
	/*位号类型的文字表述*/
	private String tagTypeStr;
	/* 位号描述 */
	private String tagDesc;
	/* 是否报警点 */
	private Long alarmPoint;
	/*是否报警点的文字表述*/
	private String alarmPonitStr;
	/*是否可写*/
	private Long isWrite;
	/*是否可写文字表述*/
	private String isWriteStr;
	
	private String values;

	/* 扩展1 是否忽略*/
	private String extension1;
	/* 扩展1 */
	private String extension2;
	

	public String getMonitorID() {
		return monitorID;
	}
	public void setMonitorID(String monitorID) {
		this.monitorID = monitorID;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public String getTagType() {
		return tagType;
	}
	public void setTagType(String tagType) {
		this.tagType = tagType;
		if(tagType.equals("13"))
			tagTypeStr = "开关量";
		else if (tagType.equals("12") || tagType.equals("14")) 
			tagTypeStr = "模拟量";
		else 
			tagTypeStr = "字符串";
	}
	public String getTagDesc() {
		return tagDesc;
	}
	public void setTagDesc(String tagDesc) {
		this.tagDesc = tagDesc;
	}
	public Long getAlarmPoint() {
		return alarmPoint;
	}
	public void setAlarmPoint(Long alarmPoint) {
		this.alarmPoint = alarmPoint;
		if(alarmPoint == 1 || alarmPoint.equals("1"))
			this.alarmPonitStr = "报警点";
		else if(alarmPoint == 2 || alarmPoint.equals("2"))
			this.alarmPonitStr = "起停点";
		else if(alarmPoint == 3 || alarmPoint.equals("3"))
			this.alarmPonitStr = "故障点";
		else
			this.alarmPonitStr = "运行点";
	}
	public Long getIsWrite() {
		return isWrite;
	}
	public void setIsWrite(Long isWrite) {
		this.isWrite = isWrite;
		if(isWrite == 1 || isWrite.equals(1))
			isWriteStr = "是";
		else
			isWriteStr = "否";
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
	public String getTagTypeStr() {
		return tagTypeStr;
	}
	public void setTagTypeStr(String tagTypeStr) {
		this.tagTypeStr = tagTypeStr;
	}
	public String getAlarmPonitStr() {
		return alarmPonitStr;
	}
	public void setAlarmPonitStr(String alarmPonitStr) {
		this.alarmPonitStr = alarmPonitStr;
	}
	public String getIsWriteStr() {
		return isWriteStr;
	}
	public void setIsWriteStr(String isWriteStr) {
		this.isWriteStr = isWriteStr;
	}
	public String getValues() {
		return values;
	}
	public void setValues(String values) {
		this.values = values;
	}

}
