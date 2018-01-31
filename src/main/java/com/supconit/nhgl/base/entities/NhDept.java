package com.supconit.nhgl.base.entities;

import java.util.Date;

import com.supconit.honeycomb.business.organization.entities.Department;
import com.supconit.nhgl.utils.OrganizationUtils;

public class NhDept extends Department{
	private String name;
	private String code;
	private Long pId;
	private String persons;
	private String area;
	private Integer nhType;
	private String deptAddr;
	private Date deptBuildDate;
	private String deptFaxes;
	private String description;
	private Integer enabled;
	private String deptTell;
	private Date recordBuildDate;
	private Long recordBuilder;
	private Long compId;
	private String companyCode;
	private Long locationId;
	
	
	public String getDeptAddr() {
		return deptAddr;
	}
	public void setDeptAddr(String deptAddr) {
		this.deptAddr = deptAddr;
	}
	public Date getDeptBuildDate() {
		return deptBuildDate;
	}
	public void setDeptBuildDate(Date deptBuildDate) {
		this.deptBuildDate = deptBuildDate;
	}
	public String getDeptFaxes() {
		return deptFaxes;
	}
	public void setDeptFaxes(String deptFaxes) {
		this.deptFaxes = deptFaxes;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getEnabled() {
		return enabled;
	}
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	public String getDeptTell() {
		return deptTell;
	}
	public void setDeptTell(String deptTell) {
		this.deptTell = deptTell;
	}
	public Date getRecordBuildDate() {
		return recordBuildDate;
	}
	public void setRecordBuildDate(Date recordBuildDate) {
		this.recordBuildDate = recordBuildDate;
	}
	public Long getRecordBuilder() {
		return recordBuilder;
	}
	public void setRecordBuilder(Long recordBuilder) {
		this.recordBuilder = recordBuilder;
	}
	public Long getCompId() {
		return compId;
	}
	public void setCompId(Long compId) {
		this.compId = compId;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public Long getLocationId() {
		return locationId;
	}
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	public Integer getNhType() {
		return nhType;
	}
	public void setNhType(Integer nhType) {
		this.nhType = nhType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Long getpId() {
		return pId;
	}
	public void setpId(Long pId) {
		this.pId = pId;
	}
	
	public String getPersons() {
		return persons;
	}
	public void setPersons(String persons) {
		this.persons = persons;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getDeptFullName() {
		if(id==null){
			return "";
		}
		return OrganizationUtils.getFullDeptNameByDeptId(id);
	}
}
