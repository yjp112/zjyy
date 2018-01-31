package com.supconit.nhgl.basic.areaConfig.entities;

/**
 * 用于导出到Excel文件中的区域能耗配置
 * 
 * @author dragon
 *
 */
public class ExportAreaConfig {
	private String areaName = "";
	private String areaCode = "";
	private String isSum = "";
	private String bitNo = ""; // 电表位号
	private String deviceName;
	private Float ftxs; // 分摊系统
	private String ruleFlag = ""; // 算法规则标志 加 减
	private String nhType = ""; // 能耗种类 电 水 蒸汽 能量
	private String memo = ""; // 备注说明
	
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

	public String getIsSum() {
		return isSum;
	}

	public void setIsSum(String isSum) {
		this.isSum = isSum;
	}

	public String getBitNo() {
		return bitNo;
	}

	public void setBitNo(String bitNo) {
		this.bitNo = bitNo;
	}

	public Float getFtxs() {
		return ftxs;
	}

	public void setFtxs(Float ftxs) {
		this.ftxs = ftxs;
	}

	public String getRuleFlag() {
		return ruleFlag;
	}

	public void setRuleFlag(String ruleFlag) {
		this.ruleFlag = ruleFlag;
	}

	public String getNhType() {
		return nhType;
	}

	public void setNhType(String nhType) {
		this.nhType = nhType;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

}
