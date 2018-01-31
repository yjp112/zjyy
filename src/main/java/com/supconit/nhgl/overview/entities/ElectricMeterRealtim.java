package com.supconit.nhgl.overview.entities;


import java.io.Serializable;
import java.util.Date;


public class ElectricMeterRealtim implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String bitNo;
	
	private Integer total;
	
	private Date collectTime;

	public String getBitNo() {
		return bitNo;
	}

	public void setBitNo(String bitNo) {
		this.bitNo = bitNo;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Date getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}
}
