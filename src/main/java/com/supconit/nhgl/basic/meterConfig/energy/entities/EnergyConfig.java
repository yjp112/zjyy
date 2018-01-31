package com.supconit.nhgl.basic.meterConfig.energy.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.supconit.base.entities.SubDevice;
import com.supconit.common.web.entities.AuditExtend;

public class EnergyConfig extends AuditExtend{

	private String deviceCode;
    private String deviceName;
    private String deviceSpec;
    private Long categoryId;
    private Long locationId;
    private String locationName;
    private Long useDepartmentId;
    private Long manageDepartmentId;
    private String managePersonIds;
    private String managePersonName;
    private Long supplierId;
    private Long maitainCycle;
    private Long timeAfterMaintain;
    private Double totalRunningTime;
    private Long specialStatus;
    private String barcode;
    private Date prduceDate;
    private String assetsCode;
    private Date enableDate;
    private Long status;
    private Double assetsCost;
    private Double assetsResidual;
    private Long depreciationAlgorithm;
    private Double depreciationRate;
    private Long depreciationYears;
    private Long mapDisplay;
    private String smallType;
    private String hpid;
    private String discipinesCode;
    private String extended1;
    private List<SubDevice> subDeviceList=new ArrayList<SubDevice>();
    
    private String subName;
    private Double lastTotal;
    private Double total;
    private Double incremental;
    private Date collectTime;
    private String subCode;
    private Date startTime;
    private Date endTime;
    private List<Long> lstLocationId;
    private String useDepartmentName;
    private String categoryCode;
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
	public String getDeviceSpec() {
		return deviceSpec;
	}
	public void setDeviceSpec(String deviceSpec) {
		this.deviceSpec = deviceSpec;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public Long getLocationId() {
		return locationId;
	}
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public Long getUseDepartmentId() {
		return useDepartmentId;
	}
	public void setUseDepartmentId(Long useDepartmentId) {
		this.useDepartmentId = useDepartmentId;
	}
	public Long getManageDepartmentId() {
		return manageDepartmentId;
	}
	public void setManageDepartmentId(Long manageDepartmentId) {
		this.manageDepartmentId = manageDepartmentId;
	}
	public String getManagePersonIds() {
		return managePersonIds;
	}
	public void setManagePersonIds(String managePersonIds) {
		this.managePersonIds = managePersonIds;
	}
	public String getManagePersonName() {
		return managePersonName;
	}
	public void setManagePersonName(String managePersonName) {
		this.managePersonName = managePersonName;
	}
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	public Long getMaitainCycle() {
		return maitainCycle;
	}
	public void setMaitainCycle(Long maitainCycle) {
		this.maitainCycle = maitainCycle;
	}
	public Long getTimeAfterMaintain() {
		return timeAfterMaintain;
	}
	public void setTimeAfterMaintain(Long timeAfterMaintain) {
		this.timeAfterMaintain = timeAfterMaintain;
	}
	public Double getTotalRunningTime() {
		return totalRunningTime;
	}
	public void setTotalRunningTime(Double totalRunningTime) {
		this.totalRunningTime = totalRunningTime;
	}
	public Long getSpecialStatus() {
		return specialStatus;
	}
	public void setSpecialStatus(Long specialStatus) {
		this.specialStatus = specialStatus;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public Date getPrduceDate() {
		return prduceDate;
	}
	public void setPrduceDate(Date prduceDate) {
		this.prduceDate = prduceDate;
	}
	public String getAssetsCode() {
		return assetsCode;
	}
	public void setAssetsCode(String assetsCode) {
		this.assetsCode = assetsCode;
	}
	public Date getEnableDate() {
		return enableDate;
	}
	public void setEnableDate(Date enableDate) {
		this.enableDate = enableDate;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Double getAssetsCost() {
		return assetsCost;
	}
	public void setAssetsCost(Double assetsCost) {
		this.assetsCost = assetsCost;
	}
	public Double getAssetsResidual() {
		return assetsResidual;
	}
	public void setAssetsResidual(Double assetsResidual) {
		this.assetsResidual = assetsResidual;
	}
	public Long getDepreciationAlgorithm() {
		return depreciationAlgorithm;
	}
	public void setDepreciationAlgorithm(Long depreciationAlgorithm) {
		this.depreciationAlgorithm = depreciationAlgorithm;
	}
	public Double getDepreciationRate() {
		return depreciationRate;
	}
	public void setDepreciationRate(Double depreciationRate) {
		this.depreciationRate = depreciationRate;
	}
	public Long getDepreciationYears() {
		return depreciationYears;
	}
	public void setDepreciationYears(Long depreciationYears) {
		this.depreciationYears = depreciationYears;
	}
	public Long getMapDisplay() {
		return mapDisplay;
	}
	public void setMapDisplay(Long mapDisplay) {
		this.mapDisplay = mapDisplay;
	}
	public String getSmallType() {
		return smallType;
	}
	public void setSmallType(String smallType) {
		this.smallType = smallType;
	}
	public String getHpid() {
		return hpid;
	}
	public void setHpid(String hpid) {
		this.hpid = hpid;
	}
	public String getDiscipinesCode() {
		return discipinesCode;
	}
	public void setDiscipinesCode(String discipinesCode) {
		this.discipinesCode = discipinesCode;
	}
	public String getExtended1() {
		return extended1;
	}
	public void setExtended1(String extended1) {
		this.extended1 = extended1;
	}
	public List<SubDevice> getSubDeviceList() {
		return subDeviceList;
	}
	public void setSubDeviceList(List<SubDevice> subDeviceList) {
		this.subDeviceList = subDeviceList;
	}
	public String getSubName() {
		return subName;
	}
	public void setSubName(String subName) {
		this.subName = subName;
	}
	public Double getLastTotal() {
		return lastTotal;
	}
	public void setLastTotal(Double lastTotal) {
		this.lastTotal = lastTotal;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Double getIncremental() {
		return incremental;
	}
	public void setIncremental(Double incremental) {
		this.incremental = incremental;
	}
	public Date getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}
	public String getSubCode() {
		return subCode;
	}
	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public List<Long> getLstLocationId() {
		return lstLocationId;
	}
	public void setLstLocationId(List<Long> lstLocationId) {
		this.lstLocationId = lstLocationId;
	}
	public String getUseDepartmentName() {
		return useDepartmentName;
	}
	public void setUseDepartmentName(String useDepartmentName) {
		this.useDepartmentName = useDepartmentName;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
    
    
}
