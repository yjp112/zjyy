package com.supconit.emergency.entities;

import com.supconit.common.web.entities.AuditExtend;

public class EmergencyGroup extends AuditExtend{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long areaId;
	private String groupCode;
	private String groupName;
	private Integer emergencyType;
	private String remark;
	
	private Long parentId;
    private String parentName;
	private String areaName;
	public Long getAreaId() {
		return areaId;
	}
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
	
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public Integer getEmergencyType() {
		return emergencyType;
	}
	public void setEmergencyType(Integer emergencyType) {
		this.emergencyType = emergencyType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Long getParentId() {
		return parentId;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	
}
