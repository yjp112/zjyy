package com.supconit.maintain.entities;

import java.util.Date;
import java.util.List;

import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.web.entities.AuditExtend;

public class MaintainPlan  extends AuditExtend{
	private static final long serialVersionUID = 1L;

	private Long id;
    private Long deviceId;
    private Long maintainContentId;
    private Date planDate;
    private Long year;
    
    //扩展
    private String deviceName;
    private String deviceCode;
    private String locationName;
    private String itemContent;
    private Date beginTime;//计划时间始
	private Date endTime;//计划时间止
	private Long categoryId;
	private Long areaId;
	private String planDates;
	private String maintainGroupName;
	private String cycle;//周期
	private String cycleUnit;//周期单位
	private String remark;
	private Integer jan;
	private Integer feb;
	private Integer mar;
	private Integer apr;
	private Integer may;
	private Integer jun;
	private Integer jul;
	private Integer aug;
	private Integer sep;
	private Integer oct;
	private Integer nov;
	private Integer dec;
	private List<String> yearReportList;
	private List<Long> deviceCategoryChildIds;
	private List<Long> geoAreaChildIds;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	public Long getMaintainContentId() {
		return maintainContentId;
	}
	public void setMaintainContentId(Long maintainContentId) {
		this.maintainContentId = maintainContentId;
	}
	public Date getPlanDate() {
		return planDate;
	}
	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}
	public Long getYear() {
		return year;
	}
	public void setYear(Long year) {
		this.year = year;
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
	public String getItemContent() {
		return itemContent;
	}
	public void setItemContent(String itemContent) {
		this.itemContent = itemContent;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public Long getAreaId() {
		return areaId;
	}
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
	public String getPlanDates() {
		return planDates;
	}
	public void setPlanDates(String planDates) {
		this.planDates = planDates;
	}
	public String getMaintainGroupName() {
		return maintainGroupName;
	}
	public void setMaintainGroupName(String maintainGroupName) {
		this.maintainGroupName = maintainGroupName;
	}
	public String getCycle() {
		return cycle;
	}
	public void setCycle(String cycle) {
		this.cycle = cycle;
	}
	public void setCycleUnit(String cycleUnit) {
		this.cycleUnit = cycleUnit;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getJan() {
		return jan;
	}
	public void setJan(Integer jan) {
		this.jan = jan;
	}
	public Integer getFeb() {
		return feb;
	}
	public void setFeb(Integer feb) {
		this.feb = feb;
	}
	public Integer getMar() {
		return mar;
	}
	public void setMar(Integer mar) {
		this.mar = mar;
	}
	public Integer getApr() {
		return apr;
	}
	public void setApr(Integer apr) {
		this.apr = apr;
	}
	public Integer getMay() {
		return may;
	}
	public void setMay(Integer may) {
		this.may = may;
	}
	public Integer getJun() {
		return jun;
	}
	public void setJun(Integer jun) {
		this.jun = jun;
	}
	public Integer getJul() {
		return jul;
	}
	public void setJul(Integer jul) {
		this.jul = jul;
	}
	public Integer getAug() {
		return aug;
	}
	public void setAug(Integer aug) {
		this.aug = aug;
	}
	public Integer getSep() {
		return sep;
	}
	public void setSep(Integer sep) {
		this.sep = sep;
	}
	public Integer getOct() {
		return oct;
	}
	public void setOct(Integer oct) {
		this.oct = oct;
	}
	public Integer getNov() {
		return nov;
	}
	public void setNov(Integer nov) {
		this.nov = nov;
	}
	public Integer getDec() {
		return dec;
	}
	public void setDec(Integer dec) {
		this.dec = dec;
	}
	public List<String> getYearReportList() {
		return yearReportList;
	}
	public void setYearReportList(List<String> yearReportList) {
		this.yearReportList = yearReportList;
	}
	public List<Long> getDeviceCategoryChildIds() {
		return deviceCategoryChildIds;
	}
	public void setDeviceCategoryChildIds(List<Long> deviceCategoryChildIds) {
		this.deviceCategoryChildIds = deviceCategoryChildIds;
	}
	public List<Long> getGeoAreaChildIds() {
		return geoAreaChildIds;
	}
	public void setGeoAreaChildIds(List<Long> geoAreaChildIds) {
		this.geoAreaChildIds = geoAreaChildIds;
	}
	
	
	//-------------------------------------------------------------------------------
	public String getCycleUnit() {
		return DictUtils.getDictLabel(DictTypeEnum.MAINTAIN_CYCLE_UNIT, this.cycleUnit);
	}
}