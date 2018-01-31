package com.supconit.base.entities;

import java.util.Date;
import java.util.List;

import com.supconit.common.utils.Constant;
import com.supconit.common.web.entities.AuditExtend;

public class DeviceSpareIn extends AuditExtend{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String deviceId;
	private Date checkDate;
	private String storeMan;
	private String assetmanager;
	private Long deptId;
	
	private String deviceName;
	private String deviceCode;
	private String spareName;
	private String total;
	private String remainder;
	private Long locationId;
	private Long categoryId;
	private String fileName;
	private String storePath;
	private String used;
	private String supplierName;
	private List<Long> lstCategoryId;
	private List<Long> lstLocationId;
	private String deptName;
	private String spareDevice=Constant.SPARE_DEVICE; 
	
	
	
	public String getSpareDevice() {
		return spareDevice;
	}
	public void setSpareDevice(String spareDevice) {
		this.spareDevice = spareDevice;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public String getDeviceCode() {
		return deviceCode;
	}
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	public List<Long> getLstCategoryId() {
		return lstCategoryId;
	}
	public void setLstCategoryId(List<Long> lstCategoryId) {
		this.lstCategoryId = lstCategoryId;
	}
	public List<Long> getLstLocationId() {
		return lstLocationId;
	}
	public void setLstLocationId(List<Long> lstLocationId) {
		this.lstLocationId = lstLocationId;
	}
	public String getUsed() {
		return used;
	}
	public void setUsed(String used) {
		this.used = used;
	}
	public String getSpareName() {
		return spareName;
	}
	public void setSpareName(String spareName) {
		this.spareName = spareName;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getRemainder() {
		return remainder;
	}
	public void setRemainder(String remainder) {
		this.remainder = remainder;
	}
	public Long getLocationId() {
		return locationId;
	}
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	public String getStoreMan() {
		return storeMan;
	}
	public void setStoreMan(String storeMan) {
		this.storeMan = storeMan;
	}
	public String getAssetmanager() {
		return assetmanager;
	}
	public void setAssetmanager(String assetmanager) {
		this.assetmanager = assetmanager;
	}
	public String getStorePath() {
		return storePath;
	}
	public void setStorePath(String storePath) {
		this.storePath = storePath;
	}
	
	
}
