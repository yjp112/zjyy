package com.supconit.base.domains;

import hc.base.domains.AuditedEntity;

public class Organization extends AuditedEntity {

	private static final long serialVersionUID = 1L;

	private Long personId;
	private String personName;
	private Long deptId;
	private String deptName;
	private String fullDeptName;
	private String personCode;
	private String positionCode;
	private Long postId;
	private Long leaderId;
	private Integer lunch;
	private Long pId;
	private String deptParentName;

	public Long getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(Long leaderId) {
		this.leaderId = leaderId;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
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

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getFullDeptName() {
		return fullDeptName;
	}

	public void setFullDeptName(String fullDeptName) {
		this.fullDeptName = fullDeptName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPersonCode() {
		return personCode;
	}

	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}

	public String getPositionCode() {
		return positionCode;
	}

	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}

	public Integer getLunch() {
		return lunch;
	}

	public void setLunch(Integer lunch) {
		this.lunch = lunch;
	}

	public Long getpId() {
		return pId;
	}

	public void setpId(Long pId) {
		this.pId = pId;
	}
	
	public String getDeptParentName() {
		return deptParentName;
	}

	public void setDeptParentName(String deptParentName) {
		this.deptParentName = deptParentName;
	}

	@Override
	public String toString() {
		return "Organization [personId=" + personId + ", personName="
				+ personName + ", deptId=" + deptId + ", deptName=" + deptName
				+ ", fullDeptName=" + fullDeptName + ", personCode=" + personCode 
				+", positionCode=" + positionCode  +", postId=" + postId 
				+", lunch=" + lunch +", pId=" + pId +",deptParentName="+deptParentName
				+"]";
	}

}
