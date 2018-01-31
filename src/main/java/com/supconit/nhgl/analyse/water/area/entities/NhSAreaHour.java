package com.supconit.nhgl.analyse.water.area.entities;

import java.math.BigDecimal;
import java.util.Date;

import com.supconit.common.web.entities.AuditExtend;
import com.supconit.nhgl.domain.CollectHourable;

public class NhSAreaHour extends AuditExtend implements CollectHourable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer areaId;//区域id
	private String itemCode;//分项编码
	private BigDecimal total;//电表读数 
	private BigDecimal incremental;//用电量
	private Date collectDate;//采集日期
	private Integer collectHour;//小时
	private Date collectTime;//采集时间
	
	
	private String areaName;
	private Integer parentId;//区域父id
	private Integer nhType;//分项类别
	private String parentCode;//分项父编码
	private String standardCode;//分项编码
	private Integer start=0;//开始的小时数
	private Integer end;//结束的小时数
	private Date now;//当前时间
	private Date lastDay;//上一天
	private String name;//区域名
	private float percent;//总体概况百分比
	private Integer tb;//标识同比昨日是否上涨
	private String startTime;//开始的时间
	private String endTime;//结束的时间
	private String sTime;
	private BigDecimal lastTotal;
	
	
	
	public BigDecimal getLastTotal() {
		return lastTotal;
	}
	public void setLastTotal(BigDecimal lastTotal) {
		this.lastTotal = lastTotal;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getsTime() {
		return sTime;
	}
	public void setsTime(String sTime) {
		this.sTime = sTime;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Integer getNhType() {
		return nhType;
	}
	public void setNhType(Integer nhType) {
		this.nhType = nhType;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public String getStandardCode() {
		return standardCode;
	}
	public void setStandardCode(String standardCode) {
		this.standardCode = standardCode;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getEnd() {
		return end;
	}
	public void setEnd(Integer end) {
		this.end = end;
	}
	public Date getNow() {
		return now;
	}
	public void setNow(Date now) {
		this.now = now;
	}
	public Date getLastDay() {
		return lastDay;
	}
	public void setLastDay(Date lastDay) {
		this.lastDay = lastDay;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getPercent() {
		return percent;
	}
	public void setPercent(float percent) {
		this.percent = percent;
	}
	public Integer getTb() {
		return tb;
	}
	public void setTb(Integer tb) {
		this.tb = tb;
	}
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public BigDecimal getIncremental() {
		return incremental;
	}
	public void setIncremental(BigDecimal incremental) {
		this.incremental = incremental;
	}
	public Date getCollectDate() {
		return collectDate;
	}
	public void setCollectDate(Date collectDate) {
		this.collectDate = collectDate;
	}
	public Integer getCollectHour() {
		return collectHour;
	}
	public void setCollectHour(Integer collectHour) {
		this.collectHour = collectHour;
	}
	public Date getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}
	

}
