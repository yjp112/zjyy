package com.supconit.nhgl.base.entities;

import java.util.Date;

import com.supconit.common.domains.TreeNode;

public class NhDevice extends TreeNode<NhDevice> {

	private static final long serialVersionUID = 1L;

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
	private String contractNo;
	private String assetsCode;
	private Double assetsCost;
	
	private Date enableDate;
	private Long status;
	
	private Long parentId;
	private String parentName;
	
	private String discipinesCode;
    private String extended1;//设备位号
    private String extended2;//服务区域 中山医院特有
    private String energyCode;
    private Long gSystemRuleId;
    private String gSystemRuleName;
    private String hpid;
    private Long mapDisplay;
    private Long threeDimdisplay;
    private Long alarmTypeId;
	private String springEl;
	private Long summarized;
	private Long deviceType;
	private Long deviceValue;
	private String repairType;
	
	private Long mainDeviceType;
	
	private Long depreciationAlgorithm;
	private Double depreciationRate;
	private Long depreciationYears;
	private Double assetsResidual;
	private Long serviceAreaId; //服务区域ID
	private String serviceAreaName; //服务区域名称
	private Integer nhtype;
	private String categoryCode;
	private String itemName;
	private String itemCode;
	
	
	
	
	
	
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getDeviceCode() {
		return deviceCode;
	}
	public Integer getNhtype() {
		return nhtype;
	}





	public void setNhtype(Integer nhtype) {
		this.nhtype = nhtype;
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

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getAssetsCode() {
		return assetsCode;
	}

	public void setAssetsCode(String assetsCode) {
		this.assetsCode = assetsCode;
	}

	public Double getAssetsCost() {
		return assetsCost;
	}

	public void setAssetsCost(Double assetsCost) {
		this.assetsCost = assetsCost;
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

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
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

	public String getEnergyCode() {
		return energyCode;
	}

	public void setEnergyCode(String energyCode) {
		this.energyCode = energyCode;
	}

	public Long getgSystemRuleId() {
		return gSystemRuleId;
	}

	public void setgSystemRuleId(Long gSystemRuleId) {
		this.gSystemRuleId = gSystemRuleId;
	}

	public String getgSystemRuleName() {
		return gSystemRuleName;
	}

	public void setgSystemRuleName(String gSystemRuleName) {
		this.gSystemRuleName = gSystemRuleName;
	}

	public String getHpid() {
		return hpid;
	}

	public void setHpid(String hpid) {
		this.hpid = hpid;
	}

	public Long getMapDisplay() {
		return mapDisplay;
	}

	public void setMapDisplay(Long mapDisplay) {
		this.mapDisplay = mapDisplay;
	}

	public Long getThreeDimdisplay() {
		return threeDimdisplay;
	}

	public void setThreeDimdisplay(Long threeDimdisplay) {
		this.threeDimdisplay = threeDimdisplay;
	}

	public Long getAlarmTypeId() {
		return alarmTypeId;
	}

	public void setAlarmTypeId(Long alarmTypeId) {
		this.alarmTypeId = alarmTypeId;
	}

	public String getSpringEl() {
		return springEl;
	}

	public void setSpringEl(String springEl) {
		this.springEl = springEl;
	}

	public Long getSummarized() {
		return summarized;
	}

	public void setSummarized(Long summarized) {
		this.summarized = summarized;
	}

	public Long getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Long deviceType) {
		this.deviceType = deviceType;
	}

	public Long getDeviceValue() {
		return deviceValue;
	}

	public void setDeviceValue(Long deviceValue) {
		this.deviceValue = deviceValue;
	}

	public String getRepairType() {
		return repairType;
	}

	public void setRepairType(String repairType) {
		this.repairType = repairType;
	}

	public Long getMainDeviceType() {
		return mainDeviceType;
	}

	public void setMainDeviceType(Long mainDeviceType) {
		this.mainDeviceType = mainDeviceType;
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

	public Double getAssetsResidual() {
		return assetsResidual;
	}

	public void setAssetsResidual(Double assetsResidual) {
		this.assetsResidual = assetsResidual;
	}

	public Long getServiceAreaId() {
		return serviceAreaId;
	}

	public void setServiceAreaId(Long serviceAreaId) {
		this.serviceAreaId = serviceAreaId;
	}

	public String getServiceAreaName() {
		return serviceAreaName;
	}
	public void setServiceAreaName(String serviceAreaName) {
		this.serviceAreaName = serviceAreaName;
	}
	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	
}
