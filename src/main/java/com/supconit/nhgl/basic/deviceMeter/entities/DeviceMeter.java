package com.supconit.nhgl.basic.deviceMeter.entities;

import java.io.Serializable;

import com.supconit.common.web.entities.AuditExtend;


public class DeviceMeter extends AuditExtend implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6599180678699381921L;
	private Long deviceId;
	private Long deviceMeterId;
	private String shareProportion;
	private Long isValid;
	private String invalidDate;
	private String linkTime;
	
	//虚拟字段
	private String electricCategoryCode;//电设别类别编码
	private String waterCategoryCode;//水设别类别编码
	private String gasCategoryCode;//气设别类别编码
	private String electricCode;//电表编码
	private String electricName;//电表名称
	private String deviceCode;//设备编码
	private String deviceName;//设备名称
	private String locationName;//测量设备位置
	private Long locationId;//测量设备位置
	private String useDepartMent;//使用部门
	private String manageDepartMent;
	private String subCode;
	
	public String getManageDepartMent() {
		return manageDepartMent;
	}
	public void setManageDepartMent(String manageDepartMent) {
		this.manageDepartMent = manageDepartMent;
	}
	public Long getLocationId() {
		return locationId;
	}
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	public String getSubCode() {
		return subCode;
	}
	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}
	public String getElectricCode() {
		return electricCode;
	}
	public void setElectricCode(String electricCode) {
		this.electricCode = electricCode;
	}
	public String getElectricName() {
		return electricName;
	}
	public void setElectricName(String electricName) {
		this.electricName = electricName;
	}
	public String getDeviceCode() {
		return deviceCode;
	}
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getUseDepartMent() {
		return useDepartMent;
	}
	public void setUseDepartMent(String useDepartMent) {
		this.useDepartMent = useDepartMent;
	}
	public String getElectricCategoryCode() {
		return electricCategoryCode;
	}
	public void setElectricCategoryCode(String electricCategoryCode) {
		this.electricCategoryCode = electricCategoryCode;
	}
	public String getWaterCategoryCode() {
		return waterCategoryCode;
	}
	public void setWaterCategoryCode(String waterCategoryCode) {
		this.waterCategoryCode = waterCategoryCode;
	}
	public String getGasCategoryCode() {
		return gasCategoryCode;
	}
	public void setGasCategoryCode(String gasCategoryCode) {
		this.gasCategoryCode = gasCategoryCode;
	}
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	public Long getDeviceMeterId() {
		return deviceMeterId;
	}
	public void setDeviceMeterId(Long deviceMeterId) {
		this.deviceMeterId = deviceMeterId;
	}
	public String getShareProportion() {
		return shareProportion;
	}
	public void setShareProportion(String shareProportion) {
		this.shareProportion = shareProportion;
	}
	public Long getIsValid() {
		return isValid;
	}
	public void setIsValid(Long isValid) {
		this.isValid = isValid;
	}
	public String getInvalidDate() {
		return invalidDate;
	}
	public void setInvalidDate(String invalidDate) {
		this.invalidDate = invalidDate;
	}
	public String getLinkTime() {
		return linkTime;
	}
	public void setLinkTime(String linkTime) {
		this.linkTime = linkTime;
	}
}

