package com.supconit.nhgl.analyse.gas.entities;

import java.util.Date;

import com.supconit.common.web.entities.AuditExtend;


/**
 * 电表数据实时表
 * @author 高文龙
 */
public class GasMeterRealTime extends AuditExtend{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5287189947670767841L;
	private Long id;
	private String bitNo;//电表位号
	private String total;//电表总度数
	private Date collectTime;//采集时间
	private String endTime;//结束时间
	
	private String collect;
	
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBitNo() {
		return bitNo;
	}
	public void setBitNo(String bitNo) {
		this.bitNo = bitNo;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public Date getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}
	public String getCollect() {
		return collect;
	}
	public void setCollect(String collect) {
		this.collect = collect;
	}
	
	
}
