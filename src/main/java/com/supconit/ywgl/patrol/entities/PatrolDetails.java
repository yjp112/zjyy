package com.supconit.ywgl.patrol.entities;

import java.util.Date;

import com.supconit.common.web.entities.AuditExtend;

public class PatrolDetails extends AuditExtend {

	private static final long	serialVersionUID	= 1L;
	
	
	private Long personId;          //PERSON_ID	巡更人ID		BIGINT
	private String personName;      //PERSON_NAME	巡更人姓名		VARCHAR(30)
	private Long pointId;           //POINT_ID	巡更点ID		BIGINT
	private String pointName;       //POINT_NAME	巡更点名称		VARCHAR(30)
	private Date patrolTime;        //PATROL_TIME	巡更时间		DATETIME
	
	private Date startTime;
	private Date endTime;
	
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
	public Long getPointId() {
		return pointId;
	}
	public void setPointId(Long pointId) {
		this.pointId = pointId;
	}
	public String getPointName() {
		return pointName;
	}
	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
	public Date getPatrolTime() {
		return patrolTime;
	}
	public void setPatrolTime(Date patrolTime) {
		this.patrolTime = patrolTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
}
