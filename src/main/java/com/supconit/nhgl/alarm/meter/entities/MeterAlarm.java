package com.supconit.nhgl.alarm.meter.entities;

import java.util.Date;

import hc.base.domains.LongId;

public class MeterAlarm extends LongId{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Long hourMeterId;
    public String meterType;
    public int status;

    public Date alarmTime;

	public Long getHourMeterId() {
		return hourMeterId;
	}

	public void setHourMeterId(Long hourMeterId) {
		this.hourMeterId = hourMeterId;
	}

	public String getMeterType() {
		return meterType;
	}

	public void setMeterType(String meterType) {
		this.meterType = meterType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(Date alarmTime) {
		this.alarmTime = alarmTime;
	}
    
    
}
