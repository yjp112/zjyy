package com.supconit.repair.entities;

import java.util.Date;

import com.supconit.common.web.entities.AuditExtend;

public class RepairReportBySpare extends AuditExtend{
	private Long qty;
	private String spareName;
	private Date startTime;
	private Date endTime;
	private Long categoryId;
	private Integer repairMode;
	
	
	
	public Integer getRepairMode() {
		return repairMode;
	}
	public void setRepairMode(Integer repairMode) {
		this.repairMode = repairMode;
	}
	public Long getQty() {
		return qty;
	}
	public void setQty(Long qty) {
		this.qty = qty;
	}
	public String getSpareName() {
		return spareName;
	}
	public void setSpareName(String spareName) {
		this.spareName = spareName;
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
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	
	
}
