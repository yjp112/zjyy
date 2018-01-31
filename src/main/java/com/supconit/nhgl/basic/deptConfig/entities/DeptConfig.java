package com.supconit.nhgl.basic.deptConfig.entities;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.supconit.common.utils.excel.ExcelAnnotation;
import com.supconit.common.web.entities.AuditExtend;

public class DeptConfig extends AuditExtend{
	private static final long serialVersionUID = 1L;
	
	private Long deviceId;
	private Long deptId;
	@ExcelAnnotation(exportName = "分摊系数")
	private Float ftxs;
	@ExcelAnnotation(exportName = "表位号")
	private String bitNo;
	private Integer ruleFlag;//算法规则标志 1：加 0：减
	private Integer nhType;//能耗种类 1：电 2：水 3：蒸汽 4：能量
	//@ExcelAnnotation(exportName = "备注说明")
	private String description;
	private String memo;
	private Integer isSum;//1：是，默认为1 0：否 该部门值是否通过下级部门汇总生成
	
	private String deptName;
	@ExcelAnnotation(exportName = "部门编码(*)")
	private String deptCode;
	private String extended1;      //设备位号
	private String deviceName;
	private String deviceCode;
	private String locationName;
	private String serviceArea;
	
	private Long	lft;
	private Long	rgt;
	@ExcelAnnotation(exportName = "该部门值是否通过下级部门汇总生成(*)")
	private String isSumValue;
	@ExcelAnnotation(exportName = "算法规则标志")
	private String ruleFlagValue;//算法规则标志 加 减
	//@ExcelAnnotation(exportName = "能耗种类")
	private String nhTypeValue;//能耗种类 电 水 蒸汽 能量
	private String hpId;
	private String categoryCode;
	private Integer sumDevice;
	
	private List<DeptConfig> subLeftConfigList;
	private List<DeptConfig> subRightConfigList;
	
	
	public Integer getSumDevice() {
		return sumDevice;
	}
	public void setSumDevice(Integer sumDevice) {
		this.sumDevice = sumDevice;
	}
	public String getServiceArea() {
		return serviceArea;
	}
	public void setServiceArea(String serviceArea) {
		this.serviceArea = serviceArea;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public Float getFtxs() {
		return ftxs;
	}
	public void setFtxs(Float ftxs) {
		this.ftxs = ftxs;
	}
	public String getBitNo() {
		return bitNo;
	}
	public void setBitNo(String bitNo) {
		this.bitNo = bitNo;
	}
	public Integer getRuleFlag() {
		return ruleFlag;
	}
	public void setRuleFlag(Integer ruleFlag) {
		this.ruleFlag = ruleFlag;
	}
	public Integer getNhType() {
		return nhType;
	}
	public void setNhType(Integer nhType) {
		if(nhType.equals(1)){
			this.nhTypeValue ="电";
		}else if(nhType.equals(2)){
			this.nhTypeValue ="水";
		}else if(nhType.equals(3)){
			this.nhTypeValue ="蒸汽";
		}else if(nhType.equals(4)){
			this.nhTypeValue ="能量";
		}
		this.nhType = nhType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Integer getIsSum() {
		return isSum;
	}
	public void setIsSum(Integer isSum) {
		this.isSum = isSum;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getDeviceCode() {
		return deviceCode;
	}
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public Long getLft() {
		return lft;
	}
	public void setLft(Long lft) {
		this.lft = lft;
	}
	public Long getRgt() {
		return rgt;
	}
	public void setRgt(Long rgt) {
		this.rgt = rgt;
	}
	public String getIsSumValue() {
		return isSumValue;
	}
	public void setIsSumValue(String isSumValue) {
		if(StringUtils.isNotEmpty(isSumValue)){
			if(isSumValue.equals("是")) 
				this.isSum = 1;
			else if(isSumValue.equals("否"))
				this.isSum = 0;
			else
				this.isSum = 0;
		}
		this.isSumValue = isSumValue;
	}
	public List<DeptConfig> getSubLeftConfigList() {
		return subLeftConfigList;
	}
	public void setSubLeftConfigList(List<DeptConfig> subLeftConfigList) {
		this.subLeftConfigList = subLeftConfigList;
	}
	public List<DeptConfig> getSubRightConfigList() {
		return subRightConfigList;
	}
	public void setSubRightConfigList(List<DeptConfig> subRightConfigList) {
		this.subRightConfigList = subRightConfigList;
	}
	public String getExtended1() {
		return extended1;
	}
	public void setExtended1(String extended1) {
		this.extended1 = extended1;
	}
	public String getHpId() {
		return hpId;
	}
	public void setHpId(String hpId) {
		this.hpId = hpId;
	}
	
	public String getRuleFlagValue() {
		return ruleFlagValue;
	}
	public void setRuleFlagValue(String ruleFlagValue) {
		if(StringUtils.isNotEmpty(ruleFlagValue)){
			if(ruleFlagValue.equals("加")) 
				this.ruleFlag = 1;
			else if(ruleFlagValue.equals("减"))
				this.ruleFlag = 0;
		}
		this.ruleFlagValue = ruleFlagValue;
	}
	public String getNhTypeValue() {
		return nhTypeValue;
	}
	
	public void setNhTypeValue(String nhTypeValue) {
	}
	
	@Override
	public boolean equals(Object obj) {
		DeptConfig d = null;
		if(null == obj) return false;
		if(obj instanceof DeptConfig){
			d = (DeptConfig) obj;
		}else{
			return false;
		}
		if(null == d.deviceId){
			return false;
		}else{
			if(this.deptId.longValue() == d.deptId.longValue() 
				&& this.deviceId.longValue()==d.deviceId.longValue()){
				return true;
			}else{
				return false;
			}
		}
		
	}
	
	@Override
	public int hashCode() {
		if(null == this.deviceId){
			return this.deptId.intValue()*10000 +this.nhType.intValue();
		}else{
			return this.deptId.intValue()*10000 + this.deviceId.intValue();
		}
	}
}
