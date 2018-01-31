package com.supconit.inspection.entities;

import java.util.Date;
import java.util.List;

import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.web.entities.AuditExtend;

public class InspectionPlan  extends AuditExtend{
	private static final long serialVersionUID = 1L;

	private Long id;
    private Long deviceId;
    private Long inspectionContentId;
    private Date planDate;
    private Integer year;
    private Integer month;
    private Integer week;
    
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
	private String inspectionGroupName;
	private String cycle;//周期
	private String cycleUnit;//周期单位
	private String remark;
	private Integer firstWeek;
	private Integer secondWeek;
	private Integer thirdWeek;
	private Integer fourWeek;
	private List<String> monthReportList;
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
	public Long getInspectionContentId() {
		return inspectionContentId;
	}
	public void setInspectionContentId(Long inspectionContentId) {
		this.inspectionContentId = inspectionContentId;
	}
	public Date getPlanDate() {
		return planDate;
	}
	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public Integer getWeek() {
		return week;
	}
	public void setWeek(Integer week) {
		this.week = week;
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
	public String getInspectionGroupName() {
		return inspectionGroupName;
	}
	public void setInspectionGroupName(String inspectionGroupName) {
		this.inspectionGroupName = inspectionGroupName;
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
	public Integer getFirstWeek() {
		return firstWeek;
	}
	public void setFirstWeek(Integer firstWeek) {
		this.firstWeek = firstWeek;
	}
	public Integer getSecondWeek() {
		return secondWeek;
	}
	public void setSecondWeek(Integer secondWeek) {
		this.secondWeek = secondWeek;
	}
	public Integer getThirdWeek() {
		return thirdWeek;
	}
	public void setThirdWeek(Integer thirdWeek) {
		this.thirdWeek = thirdWeek;
	}
	public Integer getFourWeek() {
		return fourWeek;
	}
	public void setFourWeek(Integer fourWeek) {
		this.fourWeek = fourWeek;
	}
	public List<String> getMonthReportList() {
		return monthReportList;
	}
	public void setMonthReportList(List<String> monthReportList) {
		this.monthReportList = monthReportList;
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
		return DictUtils.getDictLabel(DictTypeEnum.INSPECTION_CYCLE_UNIT, this.cycleUnit);
	}
}