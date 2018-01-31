package com.supconit.duty.entities;

import java.util.Date;

import javax.validation.constraints.NotNull;

import hc.base.domains.LongId;


public class DutyLogDetail extends LongId {

	private static final long	serialVersionUID	= 1L;

	private long dutyLogId;				//值班ID
	private Date eventTime;				//发生时间
	private String place;				//具体位置
	private String deviceCode;			//报警设备号
	private long aralmType; 			//报警类型    1 入侵    2 误报
	private String result;				//处理结果
	public long getDutyLogId() {
		return dutyLogId;
	}
	public void setDutyLogId(long dutyLogId) {
		this.dutyLogId = dutyLogId;
	}
	public Date getEventTime() {
		return eventTime;
	}
	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getDeviceCode() {
		return deviceCode;
	}
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	public long getAralmType() {
		return aralmType;
	}
	public void setAralmType(long aralmType) {
		this.aralmType = aralmType;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
