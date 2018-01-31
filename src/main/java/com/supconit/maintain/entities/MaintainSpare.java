package com.supconit.maintain.entities;

import com.supconit.common.web.entities.AuditExtend;


public class MaintainSpare extends AuditExtend{
	private static final long serialVersionUID = 1L;
	
	private String maintainCode;
	private Long spareId;
	private String spareCode;
	private String spareName;
	private String spareSpec;
	private Double qty;
	private Double money;
	private String remark;
	
	
	public String getMaintainCode() {
		return maintainCode;
	}
	public void setMaintainCode(String maintainCode) {
		this.maintainCode = maintainCode;
	}
	public Long getSpareId() {
		return spareId;
	}
	public void setSpareId(Long spareId) {
		this.spareId = spareId;
	}
	public String getSpareCode() {
		return spareCode;
	}
	public void setSpareCode(String spareCode) {
		this.spareCode = spareCode;
	}
	public String getSpareName() {
		return spareName;
	}
	public void setSpareName(String spareName) {
		this.spareName = spareName;
	}
	public String getSpareSpec() {
		return spareSpec;
	}
	public void setSpareSpec(String spareSpec) {
		this.spareSpec = spareSpec;
	}
	public Double getQty() {
		return qty;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
