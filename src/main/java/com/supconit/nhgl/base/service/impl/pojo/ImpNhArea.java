package com.supconit.nhgl.base.service.impl.pojo;

import java.util.List;

import com.supconit.common.utils.excel.ExcelAnnotation;

public class ImpNhArea {
	private Long id;
	@ExcelAnnotation(exportName = "区域名称")
	private String areaName;
	@ExcelAnnotation(exportName = "上级区域")
	private String parentAreaName;
	@ExcelAnnotation(exportName = "区域编码")
	private String areaCode;
	@ExcelAnnotation(exportName = "序号")
	private Integer sort;
	@ExcelAnnotation(exportName = "区域人数")
	private Integer persons;
	@ExcelAnnotation(exportName = "区域面积")
	private Double area;
	@ExcelAnnotation(exportName = "备注")
	private String remark;
	
	private Long parentId;
	
	private List<ImpNhArea> children;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getParentAreaName() {
		return parentAreaName;
	}
	public void setParentAreaName(String parentAreaName) {
		this.parentAreaName = parentAreaName;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public List<ImpNhArea> getChildren() {
		return children;
	}
	public void setChildren(List<ImpNhArea> children) {
		this.children = children;
	}
	
	
}
