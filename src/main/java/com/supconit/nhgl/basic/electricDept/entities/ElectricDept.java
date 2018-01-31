package com.supconit.nhgl.basic.electricDept.entities;

import java.io.Serializable;
import java.util.List;

import com.supconit.common.web.entities.AuditExtend;


public class ElectricDept extends AuditExtend implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6599180678699381921L;
	private Long deviceId;
	private Long deptId;
	private Float ftxs;
	private List<Long> deptIds;
	private String deviceName;
	private String deptName;
	private Long categoryId;
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public List<Long> getDeptIds() {
		return deptIds;
	}
	public void setDeptIds(List<Long> deptIds) {
		this.deptIds = deptIds;
	}
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public Float getFtxs() {
		return ftxs;
	}
	public void setFtxs(Float ftxs) {
		this.ftxs = ftxs;
	}
	
}

