package com.supconit.nhgl.basic.discipine.water.entities;

import java.io.Serializable;
import java.util.List;

import com.supconit.common.web.entities.AuditExtend;


public class WaterSubSystemInfo extends AuditExtend implements Serializable {

	private static final long serialVersionUID = -492673754158316385L;
	
	private Long id;
	private String code;//编码
	private String parentId;
	private String name;//名称
	private String remark; //备注
	private String standardCode;
	//虚拟字段
	private String dayofElectricTotal;//耗电总量
	private String electricTotal;//耗电总量
	private String dayofWaterTotal;//耗水总量
	private String waterTotal;//耗水总量
	private Integer tb;
	private String percent;
	private String total;
	
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getDayofElectricTotal() {
		return dayofElectricTotal;
	}
	public void setDayofElectricTotal(String dayofElectricTotal) {
		this.dayofElectricTotal = dayofElectricTotal;
	}
	public String getDayofWaterTotal() {
		return dayofWaterTotal;
	}
	public void setDayofWaterTotal(String dayofWaterTotal) {
		this.dayofWaterTotal = dayofWaterTotal;
	}
	public String getWaterTotal() {
		return waterTotal;
	}
	public void setWaterTotal(String waterTotal) {
		this.waterTotal = waterTotal;
	}
	public String getPercent() {
		return percent;
	}
	public void setPercent(String percent) {
		this.percent = percent;
	}
	public Integer getTb() {
		return tb;
	}
	public void setTb(Integer tb) {
		this.tb = tb;
	}
	public String getElectricTotal() {
		return electricTotal;
	}
	public void setElectricTotal(String electricTotal) {
		this.electricTotal = electricTotal;
	}

	private List<String> codes;
	
	public List<String> getCodes() {
		return codes;
	}
	public void setCodes(List<String> codes) {
		this.codes = codes;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getStandardCode() {
		return standardCode;
	}
	public void setStandardCode(String standardCode) {
		this.standardCode = standardCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	private List<String> standardCodes;
	private String parentName;

	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public List<String> getStandardCodes() {
		return standardCodes;
	}
	public void setStandardCodes(List<String> standardCodes) {
		this.standardCodes = standardCodes;
	}
	
	
}

