package com.supconit.inspection.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.web.entities.AuditExtend;

/**
 * 巡检项目类
 * @author yuhuan
 */
public class InspectionItem extends AuditExtend{
	private static final long serialVersionUID = 1L;
	
	private Long deviceId;//设备ID
	private String itemContent;//巡检项目内容
	private Date startTime;//巡检开始时间
	private Integer cycleUnit;//周期单位
	private Integer cycle;//巡检周期
	private Long inspectionGroupId;//巡检班组ID
	private String inspectionGroupName;//巡检班组名称
	private String remark;//备注
	
	//扩展----------
	private String deviceName;//设备名称
	private String locationName;//安装位置
	private String deviceCode;//设备编码
	private String deviceSpec;//规格型号
	private String useDepartmentName;//使用部门
	private Date beginTime;//创建时间始
	private Date endTime;//创建时间止
	private String cycleUnits;//周期单位
	private Long categoryId;
	private Long areaId;
	private List<Long> deviceCategoryChildIds;
	private List<Long> geoAreaChildIds;
	private List<InspectionItem> deviceList = new ArrayList<>();
	private List<InspectionItem> inspectionItemList = new ArrayList<>();
	
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	public String getItemContent() {
		return itemContent;
	}
	public void setItemContent(String itemContent) {
		this.itemContent = itemContent;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Integer getCycleUnit() {
		return cycleUnit;
	}
	public void setCycleUnit(Integer cycleUnit) {
		this.cycleUnit = cycleUnit;
	}
	public Integer getCycle() {
		return cycle;
	}
	public void setCycle(Integer cycle) {
		this.cycle = cycle;
	}
	public Long getInspectionGroupId() {
		return inspectionGroupId;
	}
	public void setInspectionGroupId(Long inspectionGroupId) {
		this.inspectionGroupId = inspectionGroupId;
	}
	public String getInspectionGroupName() {
		return inspectionGroupName;
	}
	public void setInspectionGroupName(String inspectionGroupName) {
		this.inspectionGroupName = inspectionGroupName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getDeviceCode() {
		return deviceCode;
	}
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	public String getDeviceSpec() {
		return deviceSpec;
	}
	public void setDeviceSpec(String deviceSpec) {
		this.deviceSpec = deviceSpec;
	}
	public String getUseDepartmentName() {
		return useDepartmentName;
	}
	public void setUseDepartmentName(String useDepartmentName) {
		this.useDepartmentName = useDepartmentName;
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
	public void setCycleUnits(String cycleUnits) {
		this.cycleUnits = cycleUnits;
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
	public List<InspectionItem> getDeviceList() {
		return deviceList;
	}
	public void setDeviceList(List<InspectionItem> deviceList) {
		this.deviceList = deviceList;
	}
	public List<InspectionItem> getInspectionItemList() {
		return inspectionItemList;
	}
	public void setInspectionItemList(List<InspectionItem> inspectionItemList) {
		this.inspectionItemList = inspectionItemList;
	}
	
	//---------------------------------------------------------------------
	public String getCycleUnits() {
		return DictUtils.getDictLabel(DictTypeEnum.INSPECTION_CYCLE_UNIT, this.cycleUnits);
	}
	
}
