package com.supconit.nhgl.basic.meterConfig.electic.entities;

import com.supconit.common.web.entities.AuditExtend;


public class ConfigManager extends AuditExtend{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5019967229834917327L;
	private String typeCode;
	private String typeName;
	private String code;
	private String name;
	private String configValue;
	private String remark;
	private Integer flag;
	private Integer order;
	private String startTime;
	private String endTime;
	
	
	//虚拟字段
	private String total;//今年节假日耗电总量
	private String lastYearTotal;//去年节假日耗电量
	private String precent;//同比去年
	private String dayTimeMin;
	private String dayTimeMax;
	private String nightMin;
	private String nightMax;
	
	
	public String getDayTimeMin() {
		return dayTimeMin;
	}
	public void setDayTimeMin(String dayTimeMin) {
		this.dayTimeMin = dayTimeMin;
	}
	public String getDayTimeMax() {
		return dayTimeMax;
	}
	public void setDayTimeMax(String dayTimeMax) {
		this.dayTimeMax = dayTimeMax;
	}
	public String getNightMin() {
		return nightMin;
	}
	public void setNightMin(String nightMin) {
		this.nightMin = nightMin;
	}
	public String getNightMax() {
		return nightMax;
	}
	public void setNightMax(String nightMax) {
		this.nightMax = nightMax;
	}
	public String getPrecent() {
		return precent;
	}
	public void setPrecent(String precent) {
		this.precent = precent;
	}
	public String getLastYearTotal() {
		return lastYearTotal;
	}
	public void setLastYearTotal(String lastYearTotal) {
		this.lastYearTotal = lastYearTotal;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getConfigValue() {
		return configValue;
	}
	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	
	
}
