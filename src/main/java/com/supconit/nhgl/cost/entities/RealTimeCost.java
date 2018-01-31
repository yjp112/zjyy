package com.supconit.nhgl.cost.entities;

import java.util.Date;

/**
 * 实时数据统计报表
 * @author yuhaun
 */
public class RealTimeCost {
	private String bitNo;//电表位号
	private Double lastTotal;//电表上次总度数
	private Date lastCollectTime;//上次采集时间
	private Double currentTotal;//电表本次总度数
	private Date currentCollectTime;//本次采集时间
	private Double usedKwh;//用电Kwh
	private String startTime;
	private String startTime1;
	private String endTime;
	private String endTime1;
	private String deptName;//部门名称
	private String box;//电箱
	private String purpose;//用途
	
	public String getBitNo() {
		return bitNo;
	}
	public void setBitNo(String bitNo) {
		this.bitNo = bitNo;
	}
	public Double getLastTotal() {
		return lastTotal;
	}
	public void setLastTotal(Double lastTotal) {
		this.lastTotal = lastTotal;
	}
	public Date getLastCollectTime() {
		return lastCollectTime;
	}
	public void setLastCollectTime(Date lastCollectTime) {
		this.lastCollectTime = lastCollectTime;
	}
	public Double getCurrentTotal() {
		return currentTotal;
	}
	public void setCurrentTotal(Double currentTotal) {
		this.currentTotal = currentTotal;
	}
	public Date getCurrentCollectTime() {
		return currentCollectTime;
	}
	public void setCurrentCollectTime(Date currentCollectTime) {
		this.currentCollectTime = currentCollectTime;
	}
	public Double getUsedKwh() {
		return usedKwh;
	}
	public void setUsedKwh(Double usedKwh) {
		this.usedKwh = usedKwh;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getStartTime1() {
		return startTime1;
	}
	public void setStartTime1(String startTime1) {
		this.startTime1 = startTime1;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getEndTime1() {
		return endTime1;
	}
	public void setEndTime1(String endTime1) {
		this.endTime1 = endTime1;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getBox() {
		return box;
	}
	public void setBox(String box) {
		this.box = box;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	
}
