package com.supconit.base.entities;

import java.util.ArrayList;
import java.util.List;

import com.supconit.common.web.entities.AuditExtend;

public class DeviceSpareInDetail extends AuditExtend{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long deviceSpareInId;
	private String spareName;
	private String spareSpac;
	private String spareMaker;
	private String purpose;
	private String price;
	private String total;
	private String remainder;
	private String sortIdx;
	
	
	private String spareImg;
	private String used;
	private List<DeviceSpareOut> deviceSpareOutList=new ArrayList<DeviceSpareOut>();
	
	
	
	public List<DeviceSpareOut> getDeviceSpareOutList() {
		return deviceSpareOutList;
	}
	public void setDeviceSpareOutList(List<DeviceSpareOut> deviceSpareOutList) {
		this.deviceSpareOutList = deviceSpareOutList;
	}
	public String getUsed() {
		return used;
	}
	public void setUsed(String used) {
		this.used = used;
	}
	public String getSpareImg() {
		return spareImg;
	}
	public void setSpareImg(String spareImg) {
		this.spareImg = spareImg;
	}
	public Long getDeviceSpareInId() {
		return deviceSpareInId;
	}
	public void setDeviceSpareInId(Long deviceSpareInId) {
		this.deviceSpareInId = deviceSpareInId;
	}
	public String getSpareName() {
		return spareName;
	}
	public void setSpareName(String spareName) {
		this.spareName = spareName;
	}
	public String getSpareSpac() {
		return spareSpac;
	}
	public void setSpareSpac(String spareSpac) {
		this.spareSpac = spareSpac;
	}
	public String getSpareMaker() {
		return spareMaker;
	}
	public void setSpareMaker(String spareMaker) {
		this.spareMaker = spareMaker;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getRemainder() {
		return remainder;
	}
	public void setRemainder(String remainder) {
		this.remainder = remainder;
	}
	public String getSortIdx() {
		return sortIdx;
	}
	public void setSortIdx(String sortIdx) {
		this.sortIdx = sortIdx;
	}
	
	
}
