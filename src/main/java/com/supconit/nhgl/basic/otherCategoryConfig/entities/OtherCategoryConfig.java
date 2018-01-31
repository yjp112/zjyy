package com.supconit.nhgl.basic.otherCategoryConfig.entities;


import java.util.List;

import com.supconit.common.web.entities.AuditExtend;

public class OtherCategoryConfig extends AuditExtend{

	private static final long serialVersionUID = 1L;
	
	private Long categoryId;    //类别ID
	private Long deviceId;      //设备ID
	private Float ftxs;     	//分摊系统
	private String bitNo;   	//电表位号
	private Integer ruleFlag;	//算法规则标志 1：加 0：减
	private Integer nhType;		//能耗种类 1：电 2：水 3：蒸汽 4：能量
	private String description;	//配置描述(通过编号)
	private String memo;		//备注说明
	private Integer isSum;		//1：是，默认为1 0：否 该部门值是否通过下级部门汇总生成
	
	private String name;
	private String code;		
	private String deviceName;     //设备名称
	private String deviceCode;     //设备编码
	private String extended1;      //设备位号
	private String locationName;   //安装位置
	
	private String isSumNmae;      //是否通过下级生成
	private String description1;	//配置描述(通过名称)
	
	private List<OtherCategoryConfig> subLeftOtherCategoryConfigList;
	private List<OtherCategoryConfig> subRightOtherCategoryConfigList;
	
	
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	public String getExtended1() {
		return extended1;
	}
	public void setExtended1(String extended1) {
		this.extended1 = extended1;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getIsSumNmae() {
		if(this.isSum.equals(1)){
			return "是";
		}
		return "否";
	}
	public void setIsSumNmae(String isSumNmae) {
		this.isSumNmae = isSumNmae;
	}
	public String getDescription1() {
		return description1;
	}
	public void setDescription1(String description1) {
		this.description1 = description1;
	}
	public List<OtherCategoryConfig> getSubLeftOtherCategoryConfigList() {
		return subLeftOtherCategoryConfigList;
	}
	public void setSubLeftOtherCategoryConfigList(
			List<OtherCategoryConfig> subLeftOtherCategoryConfigList) {
		this.subLeftOtherCategoryConfigList = subLeftOtherCategoryConfigList;
	}
	public List<OtherCategoryConfig> getSubRightOtherCategoryConfigList() {
		return subRightOtherCategoryConfigList;
	}
	public void setSubRightOtherCategoryConfigList(
			List<OtherCategoryConfig> subRightOtherCategoryConfigList) {
		this.subRightOtherCategoryConfigList = subRightOtherCategoryConfigList;
	}
	
}
