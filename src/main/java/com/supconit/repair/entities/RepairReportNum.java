package com.supconit.repair.entities;

import com.supconit.common.web.entities.AuditExtend;

/**
 * 维修次数统计类
 * @author yuhuan
 */
public class RepairReportNum extends AuditExtend{
	private static final long serialVersionUID = 1L;
	
	private Integer repairTimes;//维修次数
	private Integer repairFinishTimes;//完成维修次数
	private String  repairYear;//年份
	private String  repairMonth;//月份
	private String  repairDay;//天
	
	public Integer getRepairTimes() {
		return repairTimes;
	}
	public void setRepairTimes(Integer repairTimes) {
		this.repairTimes = repairTimes;
	}
	public Integer getRepairFinishTimes() {
		return repairFinishTimes;
	}
	public void setRepairFinishTimes(Integer repairFinishTimes) {
		this.repairFinishTimes = repairFinishTimes;
	}
	public String getRepairYear() {
		return repairYear;
	}
	public void setRepairYear(String repairYear) {
		this.repairYear = repairYear;
	}
	public String getRepairMonth() {
		return repairMonth;
	}
	public void setRepairMonth(String repairMonth) {
		this.repairMonth = repairMonth;
	}
	public String getRepairDay() {
		return repairDay;
	}
	public void setRepairDay(String repairDay) {
		this.repairDay = repairDay;
	}
	
}
