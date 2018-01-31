package com.supconit.base.entities;

import com.supconit.common.web.entities.AuditExtend;


public class Department extends AuditExtend{
	private static final long serialVersionUID = 1L;
	
	private String code;//编码
	private String name;//部门名称
	private String description;//描述
	private String companyCode;//公司code
	private Long pId;//父节点
	private Long lef;
	private Long rgt;
	
	
	
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
	
	
}
