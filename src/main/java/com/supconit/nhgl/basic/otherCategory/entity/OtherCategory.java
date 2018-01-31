package com.supconit.nhgl.basic.otherCategory.entity;

import java.util.List;

import com.supconit.common.web.entities.AuditExtend;

public class OtherCategory extends AuditExtend {
	private static final long serialVersionUID = 1L;
	private String code;
	private String name;
	private Integer nhType;
	private Long parentId;
	private String parentName;
	private String fullLevelName;
	private Integer sort;
	private String remark;
	private Integer persons;
	private Double area;
	private List<Long> subOtherCategoryList;
	
	public OtherCategory() {
		// TODO Auto-generated constructor stub
	}
	public OtherCategory(Long id) { 
		this.id=id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getNhType() {
		return nhType;
	}
	public void setNhType(Integer nhType) {
		this.nhType = nhType;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getFullLevelName() {
		return fullLevelName;
	}
	public void setFullLevelName(String fullLevelName) {
		this.fullLevelName = fullLevelName;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getPersons() {
		return persons;
	}
	public void setPersons(Integer persons) {
		this.persons = persons;
	}
	public Double getArea() {
		return area;
	}
	public void setArea(Double area) {
		this.area = area;
	}
	public List<Long> getSubOtherCategoryList() {
		return subOtherCategoryList;
	}
	public void setSubOtherCategoryList(List<Long> subOtherCategoryList) {
		this.subOtherCategoryList = subOtherCategoryList;
	}

}
