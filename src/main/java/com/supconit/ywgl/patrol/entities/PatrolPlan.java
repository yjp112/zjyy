package com.supconit.ywgl.patrol.entities;

import java.util.Date;

import com.supconit.common.web.entities.AuditExtend;

public class PatrolPlan extends AuditExtend {

	private static final long	serialVersionUID	= 1L;

	
	
	private String planCode;      //PLAN_CODE	计划编号		VARCHAR(20)
	private String planNmae;      //PLAN_NAME	计划任务名称		VARCHAR(30)
	private Long lineId;          //LINE_ID	巡更路线ID		BIGINT
	private Long personId;        //PERSON_ID	巡更人ID		BIGINT
	private Date startDate;       //START_DATE	开始时间		DATETIME
	private Date endDate;         //END_DATE	结束时间		DATETIME
	private String remark;        //REMARK	备注		VARCHAR(200)
	public String getPlanCode() {
		return planCode;
	}
	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}
	public String getPlanNmae() {
		return planNmae;
	}
	public void setPlanNmae(String planNmae) {
		this.planNmae = planNmae;
	}
	public Long getLineId() {
		return lineId;
	}
	public void setLineId(Long lineId) {
		this.lineId = lineId;
	}
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	 
}
