package com.supconit.emergency.entities;

import java.util.List;

import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.web.entities.AuditExtend;

public class EmergencyFacility extends AuditExtend{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long areaId;
	private String areaName;
	private String facilityCode;
	private String facilityName;
	private Integer facilityType;
	private String facilityModel;
	private String location;
	private Long keeperId;
	private String keeperName;
	private String remark;
	
	private  List<Long> areaIds;
	private String facilityTypeName;
	
	public Long getAreaId() {
		return areaId;
	}
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
	
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getFacilityCode() {
		return facilityCode;
	}
	public void setFacilityCode(String facilityCode) {
		this.facilityCode = facilityCode;
	}
	public String getFacilityName() {
		return facilityName;
	}
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	public Integer getFacilityType() {
		return facilityType;
	}
	public void setFacilityType(Integer facilityType) {
		this.facilityType = facilityType;
	}
	public String getFacilityModel() {
		return facilityModel;
	}
	public void setFacilityModel(String facilityModel) {
		this.facilityModel = facilityModel;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Long getKeeperId() {
		return keeperId;
	}
	public void setKeeperId(Long keeperId) {
		this.keeperId = keeperId;
	}
	public String getKeeperName() {
		return keeperName;
	}
	public void setKeeperName(String keeperName) {
		this.keeperName = keeperName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<Long> getAreaIds() {
		return areaIds;
	}
	public void setAreaIds(List<Long> areaIds) {
		this.areaIds = areaIds;
	}
	public String getFacilityTypeName() {
		return DictUtils.getDictLabel(DictTypeEnum.FACILITY_TYPE, this.facilityType.toString());
	}
	public void setFacilityTypeName(String facilityTypeName) {
		this.facilityTypeName = facilityTypeName;
	}
	
}
