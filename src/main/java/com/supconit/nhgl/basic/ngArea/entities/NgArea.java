package com.supconit.nhgl.basic.ngArea.entities;

import java.io.Serializable;
import java.util.List;

import com.supconit.common.web.entities.AuditExtend;

public class NgArea extends AuditExtend implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String areaName;      //区域名称
	private String areaCode;      //区域编码
	private Long areaType;      //区域类型
	private String areaTypeName;
	private Long parentId;         //父区域ID
	private String fullLevelName;   //区域全名
	private Long sort;             //顺序号	
	private String remark;        //备注	
	private Long persons;     //区域人数
	private Double area;         //区域面积
	
	private boolean checked;
    private String parentName;
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public Long getAreaType() {
		return areaType;
	}
	public void setAreaType(Long areaType) {
		this.areaType = areaType;
	}
	public String getAreaTypeName() {
		return areaTypeName;
	}
	public void setAreaTypeName(String areaTypeName) {
		this.areaTypeName = areaTypeName;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getFullLevelName() {
		return fullLevelName;
	}
	public void setFullLevelName(String fullLevelName) {
		this.fullLevelName = fullLevelName;
	}
	public Long getSort() {
		return sort;
	}
	public void setSort(Long sort) {
		this.sort = sort;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getPersons() {
		return persons;
	}
	public void setPersons(Long persons) {
		this.persons = persons;
	}
	public Double getArea() {
		return area;
	}
	public void setArea(Double area) {
		this.area = area;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
    
	
}
