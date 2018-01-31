package com.supconit.meeting.entities;

import java.util.Date;

import com.supconit.common.web.entities.AuditExtend;

/**
 * 门禁出入记录类
 * @author yuhuan
 */
public class AccessRecord extends AuditExtend{
	private static final long serialVersionUID = 1L;
	
	private Long deviceId;
	private String cardNo;
	private Long personId;
	private String personName;
	private Long eventTime;
	
	private Date startTime;
	private Date endTime;
	private Long startTimes;
	private Long endTimes;
	
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public Long getEventTime() {
		return eventTime;
	}
	public void setEventTime(Long eventTime) {
		this.eventTime = eventTime;
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
	public Long getStartTimes() {
		return startTimes;
	}
	public void setStartTimes(Long startTimes) {
		this.startTimes = startTimes;
	}
	public Long getEndTimes() {
		return endTimes;
	}
	public void setEndTimes(Long endTimes) {
		this.endTimes = endTimes;
	}
	

}
