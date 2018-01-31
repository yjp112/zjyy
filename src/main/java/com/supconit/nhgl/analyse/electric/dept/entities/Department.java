package com.supconit.nhgl.analyse.electric.dept.entities;

import com.supconit.common.web.entities.AuditExtend;


public class Department extends AuditExtend{

	private static final long serialVersionUID = 8002136432361007254L;

//	private Long id;
	private String code;//编码
	private String name;//部门名称
	private String description;//描述
	private String companyCode;//公司code
	private Long pId;//父节点
	private Long lef;
	private Long rgt;
	
	//虚拟字段
	private String total;//用电总量
	private String electricTotalBefore;//存储去年耗电量
	private String waterTotalBefore;//存储去年耗水量
	private float percent;
	private int tb;//同比1：上升，0：持平，-1：下降
	
	
	public String getWaterTotalBefore() {
		return waterTotalBefore;
	}
	public void setWaterTotalBefore(String waterTotalBefore) {
		this.waterTotalBefore = waterTotalBefore;
	}
	public float getPercent() {
		return percent;
	}
	public void setPercent(float percent) {
		this.percent = percent;
	}
	public String getElectricTotalBefore() {
		return electricTotalBefore;
	}
	public void setElectricTotalBefore(String electricTotalBefore) {
		this.electricTotalBefore = electricTotalBefore;
	}
//	public Long getId() {
//		return id;
//	}
//	public void setId(Long id) {
//		this.id = id;
//	}
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public Long getpId() {
		return pId;
	}
	public void setpId(Long pId) {
		this.pId = pId;
	}
	public Long getLef() {
		return lef;
	}
	public void setLef(Long lef) {
		this.lef = lef;
	}
	public Long getRgt() {
		return rgt;
	}
	public void setRgt(Long rgt) {
		this.rgt = rgt;
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
	
	
}
