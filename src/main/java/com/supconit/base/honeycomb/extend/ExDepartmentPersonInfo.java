package com.supconit.base.honeycomb.extend;

import com.supconit.honeycomb.business.organization.entities.Department;
import com.supconit.honeycomb.business.organization.entities.DepartmentPersonInfo;

public class ExDepartmentPersonInfo extends DepartmentPersonInfo<ExPerson,Department> {

	private static final long serialVersionUID = 3626979171622896083L;
	
	private String positionCode;
	
	private Integer postId;//职位 级别
	
	public String getPositionCode() {
		return positionCode;
	}

	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}
	
	/***************下面几个属性仅作查询用，未进行扩展配置*******************************/
	private String departmentCode;
	private String departmentName;
	private String personName;     
	private String positionName; //岗位名称
	private Long positionId;    // 岗位id
	private Long lunch;           //工作餐审批权限 0：无权限 1：有权限
	private Long leaderId;         //上级领导
	private String leaderName;

	
	

	public Long getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(Long leaderId) {
		this.leaderId = leaderId;
	}

	public String getLeaderName() {
		return leaderName;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public String getPersonName() {
		return personName;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public Long getLunch() {
		return lunch;
	}

	public void setLunch(Long lunch) {
		this.lunch = lunch;
	}

	public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((departmentId == null) ? 0 : departmentId.hashCode());
		result = prime * result + ((personId == null) ? 0 : personId.hashCode());
		result = prime * result + ((positionCode == null) ? 0 : positionCode.hashCode());
		return result;
	}
	
}
