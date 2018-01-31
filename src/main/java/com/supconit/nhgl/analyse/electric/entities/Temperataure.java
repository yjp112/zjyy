package com.supconit.nhgl.analyse.electric.entities;

import java.util.Date;

import com.supconit.common.web.entities.AuditExtend;


public class Temperataure extends AuditExtend{

	private static final long serialVersionUID = -5464766305021678355L;

	private Long id;
	
	private Date updateDate;//时间
	private String maxTemp;//最高温度
	private String mintTemp;//最低温度
	private String description;//描述
	private Date temperataureDate;//日期
	
	private String avgTemp;
	//虚拟字段
	
	private String start;
	private String end;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getMaxTemp() {
		return maxTemp;
	}
	public void setMaxTemp(String maxTemp) {
		this.maxTemp = maxTemp;
	}
	public String getMintTemp() {
		return mintTemp;
	}
	public void setMintTemp(String mintTemp) {
		this.mintTemp = mintTemp;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getTemperataureDate() {
		return temperataureDate;
	}
	public void setTemperataureDate(Date temperataureDate) {
		this.temperataureDate = temperataureDate;
	}
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
	public String getAvgTemp() {
		return avgTemp;
	}
	public void setAvgTemp(String avgTemp) {
		this.avgTemp = avgTemp;
	}
	
	
	
	
	
	
	
	
}
