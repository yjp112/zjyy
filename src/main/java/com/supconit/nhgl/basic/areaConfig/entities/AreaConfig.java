package com.supconit.nhgl.basic.areaConfig.entities;


import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.supconit.common.utils.excel.ExcelAnnotation;
import com.supconit.common.web.entities.AuditExtend;

public class AreaConfig extends AuditExtend{

	private static final long serialVersionUID = 1L;
	
	private Long deviceId;      //设备ID
	private Long areaId;      	//区域ID
	@ExcelAnnotation(exportName = "分摊系数")
	private Float ftxs;     	//分摊系统
	@ExcelAnnotation(exportName = "表位号")
	private String bitNo;   	//电表位号
	private Integer ruleFlag;	//算法规则标志 1：加 0：减
	private Integer nhType;		//能耗种类 1：电 2：水 3：蒸汽 4：能量
	//@ExcelAnnotation(exportName = "备注说明")
	private String description;	//配置描述(通过编号)
	private String memo;		//备注说明
	private Integer isSum;		//1：是，默认为1 0：否 该部门值是否通过下级部门汇总生成
	
	private String areaName;
	@ExcelAnnotation(exportName = "区域编码(*)")
	private String areaCode;		
	private String deviceName;     //设备名称
	private String deviceCode;     //设备编码
	private String extended1;      //设备位号
	private String locationName;   //安装位置
	
	@ExcelAnnotation(exportName = "该区域值是否通过下级区域汇总生成(*)")
	private String isSumValue;      //是否通过下级生成
	@ExcelAnnotation(exportName = "算法规则标志")
	private String ruleFlagValue;//算法规则标志 加 减
	//@ExcelAnnotation(exportName = "能耗种类")
	private String nhTypeValue;//能耗种类 电 水 蒸汽 能量
	private String description1;	//配置描述(通过名称)
	private String hpId;
	private String serviceArea;
	private Integer sumDevice;
	private String categoryCode;
	private List<AreaConfig> subLeftConfigList;
	private List<AreaConfig> subRightConfigList;
	
	public String getServiceArea() {
		return serviceArea;
	}
	public void setServiceArea(String serviceArea) {
		this.serviceArea = serviceArea;
	}
	public Integer getSumDevice() {
		return sumDevice;
	}
	public void setSumDevice(Integer sumDevice) {
		this.sumDevice = sumDevice;
	}
	public List<AreaConfig> getSubLeftConfigList() {
		return subLeftConfigList;
	}
	public void setSubLeftConfigList(List<AreaConfig> subLeftConfigList) {
		this.subLeftConfigList = subLeftConfigList;
	}
	public List<AreaConfig> getSubRightConfigList() {
		return subRightConfigList;
	}
	public void setSubRightConfigList(List<AreaConfig> subRightConfigList) {
		this.subRightConfigList = subRightConfigList;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	public Long getAreaId() {
		return areaId;
	}
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
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
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
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
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
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
	public String getDescription1() {
		return description1;
	}
	public void setDescription1(String description1) {
		this.description1 = description1;
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
	
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	@Override
	public boolean equals(Object obj) {
		AreaConfig a = null;
		if(null == obj) return false;
		if(obj instanceof AreaConfig){
			a = (AreaConfig) obj;
		}else{
			return false;
		}
		if(this.areaId.longValue() == a.areaId.longValue() 
				&& this.deviceId.longValue()==a.deviceId.longValue()){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return this.areaId.intValue()*10000 + this.deviceId.intValue();
	}
	
	
}
