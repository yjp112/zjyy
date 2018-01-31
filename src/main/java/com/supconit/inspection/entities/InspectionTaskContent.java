package com.supconit.inspection.entities;

import com.supconit.common.web.entities.AuditExtend;

public class InspectionTaskContent  extends AuditExtend{
    private static final long serialVersionUID = 1L;

    private String inspectionCode;
    private Long planId;
    private Long deviceId;
    private String itemContent;
    private Integer result;
    private String descripton;
    
    //扩展
    private String deviceName;
    private String locationName;
    private Integer rowNum;
	
	public String getInspectionCode() {
		return inspectionCode;
	}
	public void setInspectionCode(String inspectionCode) {
		this.inspectionCode = inspectionCode;
	}
	public Long getPlanId() {
		return planId;
	}
	public void setPlanId(Long planId) {
		this.planId = planId;
	}
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
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
	public String getDescripton() {
		return descripton;
	}
	public void setDescripton(String descripton) {
		this.descripton = descripton;
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
	public Integer getRowNum() {
		return rowNum;
	}
	public void setRowNum(Integer rowNum) {
		this.rowNum = rowNum;
	}
}