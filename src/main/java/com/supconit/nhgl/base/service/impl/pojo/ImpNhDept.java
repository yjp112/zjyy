package com.supconit.nhgl.base.service.impl.pojo;

import java.util.List;

import com.supconit.common.utils.excel.ExcelAnnotation;

public class ImpNhDept {
	private Long id;
	@ExcelAnnotation(exportName = "部门名称")
	private String name;
	@ExcelAnnotation(exportName = "上级部门")
	private String parentDeptName;
	@ExcelAnnotation(exportName = "部门编码")
	private String code;
	@ExcelAnnotation(exportName = "部门人数")
	private Integer persons;
	@ExcelAnnotation(exportName = "部门面积")
	private Double area;
	@ExcelAnnotation(exportName = "备注")
	private String description;
	
	private Long parentId;
	
	private List<ImpNhDept> children;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentDeptName() {
		return parentDeptName;
	}
	public void setParentDeptName(String parentDeptName) {
		this.parentDeptName = parentDeptName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public List<ImpNhDept> getChildren() {
		return children;
	}
	public void setChildren(List<ImpNhDept> children) {
		this.children = children;
	}
	
	
}
