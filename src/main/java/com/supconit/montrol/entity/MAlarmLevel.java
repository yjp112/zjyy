package com.supconit.montrol.entity;

import hc.base.domains.LongId;
import hc.base.domains.PK;

public class MAlarmLevel extends LongId implements PK<Long> {
	private static final long serialVersionUID = 1L;
	private String alarmLevel;
	private String remark;
	private long alarmLNum;
	
	public long getAlarmLNum() {
		return alarmLNum;
	}
	public void setAlarmLNum(long alarmLNum) {
		this.alarmLNum = alarmLNum;
	}
	public String getAlarmLevel() {
		return alarmLevel;
	}
	public void setAlarmLevel(String alarmLevel) {
		this.alarmLevel = alarmLevel;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	
}
