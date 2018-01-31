package com.supconit.base.entities;

import java.util.Date;
import java.util.List;

import com.supconit.common.web.entities.AuditExtend;

public class DeviceSpareOut extends AuditExtend{
	private Long spareId;
	private String getSum;
	private Date getDate;
	private String getor;
	private String affirmor;
	private String remark;
	private List<SubDeviceSpareOut> deviceSpareOutlst;
	
	
	
	public List<SubDeviceSpareOut> getDeviceSpareOutlst() {
		return deviceSpareOutlst;
	}
	public void setDeviceSpareOutlst(List<SubDeviceSpareOut> deviceSpareOutlst) {
		this.deviceSpareOutlst = deviceSpareOutlst;
	}
	public Long getSpareId() {
		return spareId;
	}
	public void setSpareId(Long spareId) {
		this.spareId = spareId;
	}
	public String getGetSum() {
		return getSum;
	}
	public void setGetSum(String getSum) {
		this.getSum = getSum;
	}
	public Date getGetDate() {
		return getDate;
	}
	public void setGetDate(Date getDate) {
		this.getDate = getDate;
	}
	public String getGetor() {
		return getor;
	}
	public void setGetor(String getor) {
		this.getor = getor;
	}
	public String getAffirmor() {
		return affirmor;
	}
	public void setAffirmor(String affirmor) {
		this.affirmor = affirmor;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}
