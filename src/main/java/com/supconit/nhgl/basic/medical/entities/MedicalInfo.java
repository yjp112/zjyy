package com.supconit.nhgl.basic.medical.entities;


import com.supconit.common.web.entities.AuditExtend;


public class MedicalInfo extends AuditExtend{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long typeId;
	private String monthKey;
	private Integer outpatient;
	private Integer inpatient;
	private Integer operation;
	private String avgTemperataure;
	private Integer runDevice;
	private String start;
	private String end;
	
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getAvgTemperataure() {
		return avgTemperataure;
	}
	public void setAvgTemperataure(String avgTemperataure) {
		this.avgTemperataure = avgTemperataure;
	}
	public Integer getRunDevice() {
		return runDevice;
	}
	public void setRunDevice(Integer runDevice) {
		this.runDevice = runDevice;
	}
	public Long getTypeId() {
		return typeId;
	}
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	public String getMonthKey() {
		return monthKey;
	}
	public void setMonthKey(String monthKey) {
		this.monthKey = monthKey;
	}
	public Integer getOutpatient() {
		return outpatient;
	}
	public void setOutpatient(Integer outpatient) {
		this.outpatient = outpatient;
	}
	public Integer getInpatient() {
		return inpatient;
	}
	public void setInpatient(Integer inpatient) {
		this.inpatient = inpatient;
	}
	public Integer getOperation() {
		return operation;
	}
	public void setOperation(Integer operation) {
		this.operation = operation;
	}
}

