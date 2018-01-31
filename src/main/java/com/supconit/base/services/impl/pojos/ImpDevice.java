package com.supconit.base.services.impl.pojos;


import com.supconit.common.utils.excel.ExcelAnnotation;

import java.math.BigDecimal;
import java.util.Date;

public class ImpDevice {
    @ExcelAnnotation(exportName = "hpid")
    private String hpid;

    @ExcelAnnotation(exportName = "上级hpid")
    private String lastHpid;

	@ExcelAnnotation(exportName = "设备名称")
    private String deviceName;

	@ExcelAnnotation(exportName = "设备类别")
    private String categoryName;

    private Long categoryId;

    @ExcelAnnotation(exportName = "安装位置")
    private String locationName;

    private Long locationId;

    @ExcelAnnotation(exportName = "是否在地图上显示")
    private String mapDisplayName;

	@ExcelAnnotation(exportName = "是否在三维上显示")
    private String threeDimDisplayName;

	@ExcelAnnotation(exportName = "设备编码")
    private String deviceCode;

	@ExcelAnnotation(exportName = "设备规格")
    private String deviceSpec;
	
	@ExcelAnnotation(exportName = "资产编码")
    private String assetsCode;
	
	@ExcelAnnotation(exportName = "设备条码")
    private String barcode;
	
	@ExcelAnnotation(exportName = "管理人NAMES")
    private String managePersonName;

    @ExcelAnnotation(exportName = "管理人IDS")
    private String managePersonIds;

    @ExcelAnnotation(exportName = "使用部门ID")
    private Long useDepartmentId;

    @ExcelAnnotation(exportName = "管理部门ID")
    private Long manageDepartmentId;

    @ExcelAnnotation(exportName = "系统命名规约表id")
    private String ruleId;

    @ExcelAnnotation(exportName = "排序")
    private String sort;
    
    @ExcelAnnotation(exportName = "分项")
    private String discipinesCode;
    
    @ExcelAnnotation(exportName = "能耗编码")
    private String energyCode;

    @ExcelAnnotation(exportName = "服务区域")
    private String serviceArea;
    
    private Long serviceAreaId;
    
    @ExcelAnnotation(exportName = "EXTENDED1")
    private String extended1;
    
    @ExcelAnnotation(exportName = "EXTENDED2")
    private String extended2;


    private Long id;

    private Long  parentId;


    private Long supplierId;

    private Integer maitainCycle;

    private Integer timeAfterMaintain;

    private BigDecimal totalRunningTime;

    private Integer specialStatus;

    private Date prduceDate;

    private Date enableDate;

    private Integer status;

    private BigDecimal assetsCost;

    private BigDecimal assetsResidual;

    private Integer depreciationAlgorithm;

    private BigDecimal depreciationRate;

    private Integer depreciationYears;

    private Long createId;

    private String creator;

    private Date createDate;

    private Long updateId;

    private String updator;

    private Date updateDate;

    private Integer mapDisplay;

    private Integer threeDimDisplay;

    private Long alarmTypeId;

    private Long gSystemRuleId;

    private Integer summarized;


    private String gSystemRuleCode;
    

    private Long areaId;

    private String importNum;

    private Long lft;
    private Long rgt;
    private Integer lvl;
    private Integer sortIdx;

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode == null ? null : deviceCode.trim();
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName == null ? null : deviceName.trim();
    }

    public String getDeviceSpec() {
        return deviceSpec;
    }

    public void setDeviceSpec(String deviceSpec) {
        this.deviceSpec = deviceSpec == null ? null : deviceSpec.trim();
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
        this.locationName = locationName == null ? null : locationName.trim();
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
        this.managePersonIds = managePersonIds == null ? null : managePersonIds.trim();
    }

    public String getManagePersonName() {
        return managePersonName;
    }

    public void setManagePersonName(String managePersonName) {
        this.managePersonName = managePersonName == null ? null : managePersonName.trim();
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getMaitainCycle() {
        return maitainCycle;
    }

    public void setMaitainCycle(Integer maitainCycle) {
        this.maitainCycle = maitainCycle;
    }

    public Integer getTimeAfterMaintain() {
        return timeAfterMaintain;
    }

    public void setTimeAfterMaintain(Integer timeAfterMaintain) {
        this.timeAfterMaintain = timeAfterMaintain;
    }

    public BigDecimal getTotalRunningTime() {
        return totalRunningTime;
    }

    public void setTotalRunningTime(BigDecimal totalRunningTime) {
        this.totalRunningTime = totalRunningTime;
    }

    public Integer getSpecialStatus() {
        return specialStatus;
    }

    public void setSpecialStatus(Integer specialStatus) {
        this.specialStatus = specialStatus;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode == null ? null : barcode.trim();
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
        this.assetsCode = assetsCode == null ? null : assetsCode.trim();
    }

    public Date getEnableDate() {
        return enableDate;
    }

    public void setEnableDate(Date enableDate) {
        this.enableDate = enableDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getAssetsCost() {
        return assetsCost;
    }

    public void setAssetsCost(BigDecimal assetsCost) {
        this.assetsCost = assetsCost;
    }

    public BigDecimal getAssetsResidual() {
        return assetsResidual;
    }

    public void setAssetsResidual(BigDecimal assetsResidual) {
        this.assetsResidual = assetsResidual;
    }

    public Integer getDepreciationAlgorithm() {
        return depreciationAlgorithm;
    }

    public void setDepreciationAlgorithm(Integer depreciationAlgorithm) {
        this.depreciationAlgorithm = depreciationAlgorithm;
    }

    public BigDecimal getDepreciationRate() {
        return depreciationRate;
    }

    public void setDepreciationRate(BigDecimal depreciationRate) {
        this.depreciationRate = depreciationRate;
    }

    public Integer getDepreciationYears() {
        return depreciationYears;
    }

    public void setDepreciationYears(Integer depreciationYears) {
        this.depreciationYears = depreciationYears;
    }


    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator == null ? null : updator.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getHpid() {
        return hpid;
    }

    public void setHpid(String hpid) {
        this.hpid = hpid == null ? null : hpid.trim();
    }

    public Integer getMapDisplay() {
        return mapDisplay;
    }

    public void setMapDisplay(Integer mapDisplay) {
        this.mapDisplay = mapDisplay;
    }

    public Integer getThreeDimDisplay() {
        return threeDimDisplay;
    }

    public void setThreeDimDisplay(Integer threeDimDisplay) {
        this.threeDimDisplay = threeDimDisplay;
    }

    public Long getAlarmTypeId() {
        return alarmTypeId;
    }

    public void setAlarmTypeId(Long alarmTypeId) {
        this.alarmTypeId = alarmTypeId;
    }

    public Long getgSystemRuleId() {
        return gSystemRuleId;
    }

    public void setgSystemRuleId(Long gSystemRuleId) {
        this.gSystemRuleId = gSystemRuleId;
    }


    public Integer getSummarized() {
        return summarized;
    }

    public void setSummarized(Integer summarized) {
        this.summarized = summarized;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    public String getgSystemRuleCode() {
        return gSystemRuleCode;
    }

    public void setgSystemRuleCode(String gSystemRuleCode) {
        this.gSystemRuleCode = gSystemRuleCode == null ? null : gSystemRuleCode.trim();
    }

    public String getMapDisplayName() {
        return mapDisplayName;
    }

    public void setMapDisplayName(String mapDisplayName) {
        this.mapDisplayName = mapDisplayName == null ? null : mapDisplayName.trim();
    }

    public String getThreeDimDisplayName() {
        return threeDimDisplayName;
    }

    public void setThreeDimDisplayName(String threeDimDisplayName) {
        this.threeDimDisplayName = threeDimDisplayName == null ? null : threeDimDisplayName.trim();
    }



    public String getDiscipinesCode() {
        return discipinesCode;
    }

    public void setDiscipinesCode(String discipinesCode) {
        this.discipinesCode = discipinesCode == null ? null : discipinesCode.trim();
    }


    public String getEnergyCode() {
        return energyCode;
    }

    public void setEnergyCode(String energyCode) {
        this.energyCode = energyCode == null ? null : energyCode.trim();
    }

    public String getServiceArea() {
        return serviceArea;
    }

    public void setServiceArea(String serviceArea) {
        this.serviceArea = serviceArea == null ? null : serviceArea.trim();
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getImportNum() {
        return importNum;
    }

    public void setImportNum(String importNum) {
        this.importNum = importNum == null ? null : importNum.trim();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastHpid() {
        return lastHpid;
    }

    public void setLastHpid(String lastHpid) {
        this.lastHpid = lastHpid;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
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

    public Integer getLvl() {
        return lvl;
    }

    public void setLvl(Integer lvl) {
        this.lvl = lvl;
    }

    public Integer getSortIdx() {
        return sortIdx;
    }

    public void setSortIdx(Integer sortIdx) {
        this.sortIdx = sortIdx;
    }

	public Long getServiceAreaId() {
		return serviceAreaId;
	}

	public void setServiceAreaId(Long serviceAreaId) {
		this.serviceAreaId = serviceAreaId;
	}

	public String getExtended1() {
		return extended1;
	}

	public void setExtended1(String extended1) {
		this.extended1 = extended1;
	}

	public String getExtended2() {
		return extended2;
	}

	public void setExtended2(String extended2) {
		this.extended2 = extended2;
	}
}