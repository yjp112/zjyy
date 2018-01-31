package com.supconit.nhgl.basic.discipine.gas.entities;

import java.io.Serializable;
import java.util.List;

import com.supconit.common.web.entities.AuditExtend;


public class GasSubSystemInfo extends AuditExtend implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;//编码
	private String parentId;
	private String name;//名称
	private String remark; //备注
	private String standardCode;
	
	private List<String> standardCodes;
	private String parentName;
	private String dayofGasTotal;
	private Integer tb;
	
	
	public Integer getTb() {
		return tb;
	}
	public void setTb(Integer tb) {
		this.tb = tb;
	}
	public String getDayofGasTotal() {
		return dayofGasTotal;
	}
	public void setDayofGasTotal(String dayofGasTotal) {
		this.dayofGasTotal = dayofGasTotal;
	}
	public List<String> getStandardCodes() {
		return standardCodes;
	}
	public void setStandardCodes(List<String> standardCodes) {
		this.standardCodes = standardCodes;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStandardCode() {
		return standardCode;
	}
	public void setStandardCode(String standardCode) {
		this.standardCode = standardCode;
	}
	
	
}
