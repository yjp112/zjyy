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
public class RepairReportByCategory extends AuditExtend implements Serializable {
	private Long	categoryId;
	private String	categoryName;
	private int		repairTimes;
	private Long    workHours;

	private String	categoryIds;
	private String	beginDate;
	private String	endDate;
	private Integer repairMode;

	
	
	
	public Integer getRepairMode() {
		return repairMode;
	}

	public void setRepairMode(Integer repairMode) {
		this.repairMode = repairMode;
	}

	public Long getWorkHours() {
		return workHours;
	}

	public void setWorkHours(Long workHours) {
		this.workHours = workHours;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getRepairTimes() {
		return repairTimes;
	}

	public void setRepairTimes(int repairTimes) {
		this.repairTimes = repairTimes;
	}


	public String getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(String categoryIds) {
		this.categoryIds = categoryIds;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
