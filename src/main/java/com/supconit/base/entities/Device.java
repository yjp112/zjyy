package com.supconit.base.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.supconit.common.domains.TreeNode;
import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;

public class Device extends TreeNode<Device> {

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
    private String categoryName;
    private String categoryNames;
	private String statusName;
	private String useDepartmentName;
	private String manageDepartmentName;
	private String supplierName;
	private String update;
	private String gisSort;
	private List<DeviceExtendProperty> extendPropertyList = new ArrayList<DeviceExtendProperty>();
	private List<SubDevice> subDeviceList = new ArrayList<SubDevice>();

	private List<Long> lstCategoryId;
	private List<Long> lstLocationId;
	private List<String> lstDeviceCode;
	
	private Long gSystemRuleId;
	private Long threeDimdisplay;
	private Long alarmTypeId;
	private String springEl;
	
	private String gSystemRuleName;
	private String alarmTypeName;
	private String contractNo;
	// -----数据库没有，查询条件用
	
	private Long useYears;
	private Date enableDateStart;
	private Date enableDateEnd;
	private String openCloseStatus;
	private String imgPath;
	private Double runningTime;
	private String locationCode;
	private String categoryCode;

	private List<String> hpidlst;

    private Long parentId;
    private String parentName;
	

	// -----
	// -----****************
    private String discipinesCode;
    private String energyCode;
    private String extended1;
	private String extended2;
    
    private String subName;
    private Double lastTotal;
    private Double total;
    private Double incremental;
    private Date collectTime;
    private String subCode;
    private Date startTime;
    private Date endTime;
    private String managerDepartmentName;
    private String electricCategoryCode;//电设备类Code
    private String waterCategoryCode;//水设备类别Code
    private String gasCategoryCode;//气设备类别Code
    
    private Long serviceAreaId; //服务区域ID
    
	private Long deviceType;
	private String repairType;
	
	private Boolean search;
	
    private  String  statusString;
    
    private Long deviceCount;//设备数量
    private Long alarmDeviceCount;//报警设备数量

	/** 手机端使用 **/
	private long deviceTotal;
	private long deviceId;
	private String deviceSearch;
	private long locationParentId;
	private long categoryParentId;

    public Long getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Long deviceType) {
		this.deviceType = deviceType;
	}

	public Long getServiceAreaId() {
		return serviceAreaId;
	}

	public void setServiceAreaId(Long serviceAreaId) {
		this.serviceAreaId = serviceAreaId;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
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

	public String getManagerDepartmentName() {
		return managerDepartmentName;
	}

	public void setManagerDepartmentName(String managerDepartmentName) {
		this.managerDepartmentName = managerDepartmentName;
	}


	public List<Long> getDeviceIds() {
		return deviceIds;
	}

	public void setDeviceIds(List<Long> deviceIds) {
		this.deviceIds = deviceIds;
	}

	private List<Long> deviceIds;//设备ids
    
    
	
	
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

	public String getDeviceCode() {
		return deviceCode;
	}

	public List<String> getHpidlst() {
		return hpidlst;
	}

	public void setHpidlst(List<String> hpidlst) {
		this.hpidlst = hpidlst;
	}

	public String getAlarmTypeName() {
		return alarmTypeName;
	}

	public void setAlarmTypeName(String alarmTypeName) {
		this.alarmTypeName = alarmTypeName;
	}

	public String getgSystemRuleName() {
		return gSystemRuleName;
	}

	public void setgSystemRuleName(String gSystemRuleName) {
		this.gSystemRuleName = gSystemRuleName;
	}

	public Long getgSystemRuleId() {
		return gSystemRuleId;
	}

	public void setgSystemRuleId(Long gSystemRuleId) {
		this.gSystemRuleId = gSystemRuleId;
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

	public String getUpdate()
    {
        return update;
    }

    public void setUpdate(String update)
    {
        this.update = update;
    }

    public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public Double getRunningTime() {
		return runningTime;
	}

	public void setRunningTime(Double runningTime) {
		this.runningTime = runningTime;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getOpenCloseStatus() {
		return openCloseStatus;
	}

	public void setOpenCloseStatus(String openCloseStatus) {
		this.openCloseStatus = openCloseStatus;
	}

	public List<String> getLstDeviceCode() {
		return lstDeviceCode;
	}

	public void setLstDeviceCode(List<String> lstDeviceCode) {
		this.lstDeviceCode = lstDeviceCode;
	}

	public Long getUseYears() {
		return useYears;
	}

	public void setUseYears(Long useYears) {
		this.useYears = useYears;
	}

	public Date getEnableDateStart() {
		return enableDateStart;
	}

	public void setEnableDateStart(Date enableDateStart) {
		this.enableDateStart = enableDateStart;
	}

	public Date getEnableDateEnd() {
		return enableDateEnd;
	}

	public void setEnableDateEnd(Date enableDateEnd) {
		this.enableDateEnd = enableDateEnd;
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

	public List<SubDevice> getSubDeviceList() {
		return subDeviceList;
	}

	public void setSubDeviceList(List<SubDevice> subDeviceList) {
		this.subDeviceList = subDeviceList;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getStatusName() {
		if(this.status != null){
			return DictUtils.getDictLabel(DictTypeEnum.DEVICE_STATUS, this.status.toString());
		}else{
			return "";
		}
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getUseDepartmentName() {
		return useDepartmentName;
	}

	public void setUseDepartmentName(String useDepartmentName) {
		this.useDepartmentName = useDepartmentName;
	}

	public String getManageDepartmentName() {
		return manageDepartmentName;
	}

	public void setManageDepartmentName(String manageDepartmentName) {
		this.manageDepartmentName = manageDepartmentName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public List<DeviceExtendProperty> getExtendPropertyList() {
		return extendPropertyList;
	}

	public void setExtendPropertyList(
			List<DeviceExtendProperty> extendPropertyList) {
		this.extendPropertyList = extendPropertyList;
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

	public String getGisSort() {
		return gisSort;
	}

	public void setGisSort(String gisSort) {
		this.gisSort = gisSort;
	}

    public String getRepairType() {
		return repairType;
	}

	public void setRepairType(String repairType) {
		this.repairType = repairType;
	}

	@Override
	public String toString() {
		return "Device [deviceCode=" + deviceCode + ", deviceName="
				+ deviceName + ", hpid=" + hpid + ", categoryId=" + categoryId
				+ ", locationId=" + locationId + ", locationName="
				+ locationName + ", deviceSpec=" + deviceSpec
				+ ", useDepartmentId=" + useDepartmentId
				+ ", manageDepartmentId=" + manageDepartmentId
				+ ", managePersonIds=" + managePersonIds
				+ ", managePersonName=" + managePersonName + ", supplierId="
				+ supplierId + ", maitainCycle=" + maitainCycle
				+ ", timeAfterMaintain=" + timeAfterMaintain
				+ ", totalRunningTime=" + totalRunningTime + ", specialStatus="
				+ specialStatus + ", barcode=" + barcode + ", prduceDate="
				+ prduceDate + ", assetsCode=" + assetsCode + ", enableDate="
				+ enableDate + ", status=" + status + ", assetsCost="
				+ assetsCost + ", assetsResidual=" + assetsResidual
				+ ", depreciationAlgorithm=" + depreciationAlgorithm
				+ ", depreciationRate=" + depreciationRate
				+ ", depreciationYears=" + depreciationYears + ", mapDisplay="
				+ mapDisplay + ", smallType=" + smallType + ", categoryName="
				+ categoryName + ", statusName=" + statusName
				+ ", useDepartmentName=" + useDepartmentName
				+ ", manageDepartmentName=" + manageDepartmentName
				+ ", supplierName=" + supplierName + ", gisSort=" + gisSort
				+ ", extendPropertyList=" + extendPropertyList
				+ ", subDeviceList=" + subDeviceList + ", lstCategoryId="
				+ lstCategoryId + ", lstLocationId=" + lstLocationId
				+ ", lstDeviceCode=" + lstDeviceCode + ", useYears=" + useYears
				+ ", enableDateStart=" + enableDateStart + ", enableDateEnd="
				+ enableDateEnd + ", openCloseStatus=" + openCloseStatus
				+ ", imgPath=" + imgPath + ", runningTime=" + runningTime
				+ ", locationCode=" + locationCode + ", categoryCode="
				+ categoryCode + ",repairType="+repairType+ "]";
	}

	public Boolean getSearch() {
		return search;
	}

	public void setSearch(Boolean search) {
		this.search = search;
	}

	public String getStatusString() {
		return statusString;
	}

	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}

	public long getDeviceTotal() {
		return deviceTotal;
	}

	public void setDeviceTotal(long deviceTotal) {
		this.deviceTotal = deviceTotal;
	}

	public long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(long deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceSearch() {
		return deviceSearch;
	}

	public void setDeviceSearch(String deviceSearch) {
		this.deviceSearch = deviceSearch;
	}

	public long getCategoryParentId() {
		return categoryParentId;
	}

	public void setCategoryParentId(long categoryParentId) {
		this.categoryParentId = categoryParentId;
	}

	public long getLocationParentId() {
		return locationParentId;
	}

	public void setLocationParentId(long locationParentId) {
		this.locationParentId = locationParentId;
	}
	
	 public String getEnergyCode() {
			return energyCode;
	}

	public void setEnergyCode(String energyCode) {
		this.energyCode = energyCode;
	}

	public String getExtended2() {
		return extended2;
	}

	public void setExtended2(String extended2) {
		this.extended2 = extended2;
	}

	public Long getDeviceCount() {
		return deviceCount;
	}

	public void setDeviceCount(Long deviceCount) {
		this.deviceCount = deviceCount;
	}

	public Long getAlarmDeviceCount() {
		return alarmDeviceCount;
	}

	public void setAlarmDeviceCount(Long alarmDeviceCount) {
		this.alarmDeviceCount = alarmDeviceCount;
	}
	
	public String getCategoryNames() {
		return categoryNames;
	}

	public void setCategoryNames(String categoryNames) {
		this.categoryNames = categoryNames;
	}
	
}
