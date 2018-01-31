package com.supconit.repair.entities;

import java.io.Serializable;

import com.supconit.common.web.entities.AuditExtend;

/**
 * @文件名: RepairReportByCategory
 * @创建日期: 13-9-9
 * @版权: Copyright(c)2013
 * @开发人员:
 * @版本:
 * @描述:
 */
public class RepairReportByHours extends AuditExtend implements Serializable {
	private int		repairTimes;
	private Long    workHours;
	private Long    avgHours;
	private String  repairMonth;
	private Integer 	repairMode;


	
	
	public Integer getRepairMode() {
		return repairMode;
	}

	public void setRepairMode(Integer repairMode) {
		this.repairMode = repairMode;
	}

	public Long getAvgHours() {
		return avgHours;
	}

	public void setAvgHours(Long avgHours) {
		this.avgHours = avgHours;
	}

	public String getRepairMonth() {
		return repairMonth;
	}

	public void setRepairMonth(String repairMonth) {
		this.repairMonth = repairMonth;
	}

	public Long getWorkHours() {
		return workHours;
	}

	public void setWorkHours(Long workHours) {
		this.workHours = workHours;
	}

	public int getRepairTimes() {
		return repairTimes;
	}

	public void setRepairTimes(int repairTimes) {
		this.repairTimes = repairTimes;
	}
}
