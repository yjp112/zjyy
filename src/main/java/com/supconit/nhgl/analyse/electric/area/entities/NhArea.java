package com.supconit.nhgl.analyse.electric.area.entities;

import com.supconit.common.web.entities.AuditExtend;

public class NhArea  extends AuditExtend{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer persons;//区域人数
	private Integer area;//区域面积
    private String areaCode;
    private String areaName;
    private Long areaType;
    private String areaTypeName;

	private Long parentId;
    private String fullLevelName;
    private Long sort;
    private String remark;
    private boolean checked;
    private String parentName;
    
    private String total;//总电量
	private int tb=2;//同比1：上升，0：持平，-1：下降
	private String electricTotalBefore;//存储去年耗电量
	private String waterTotalBefore;//存储去年耗水量
	private Float percent;//标识上涨率
	private String name;
	private Integer nhType;//能耗种类
	
	
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
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
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public int getTb() {
		return tb;
	}
	public void setTb(int tb) {
		this.tb = tb;
	}
	public String getElectricTotalBefore() {
		return electricTotalBefore;
	}
	public void setElectricTotalBefore(String electricTotalBefore) {
		this.electricTotalBefore = electricTotalBefore;
	}
	public String getWaterTotalBefore() {
		return waterTotalBefore;
	}
	public void setWaterTotalBefore(String waterTotalBefore) {
		this.waterTotalBefore = waterTotalBefore;
	}
	public Float getPercent() {
		return percent;
	}
	public void setPercent(Float percent) {
		this.percent = percent;
	}
	public Integer getPersons() {
		return persons;
	}
	public void setPersons(Integer persons) {
		this.persons = persons;
	}
	public Integer getArea() {
		return area;
	}
	public void setArea(Integer area) {
		this.area = area;
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
}
