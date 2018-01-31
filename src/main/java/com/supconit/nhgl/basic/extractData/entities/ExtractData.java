package com.supconit.nhgl.basic.extractData.entities;

import java.util.Date;

import com.supconit.jobschedule.entities.ScheduleLog;

public class ExtractData extends ScheduleLog{
	private static final long serialVersionUID = 1L;

	private Integer nhType;//能耗类型
	private String nhTypes;//能耗类型
	private Integer weidu;//统计纬度
	private String weidus;//统计纬度
	private Date startTime;//开始时间
	private Date endTime;//结束时间
	private String relation;//关系
	private String enumText;//job名称
	
	public Integer getNhType() {
		return nhType;
	}
	public void setNhType(Integer nhType) {
		this.nhType = nhType;
	}
	public Integer getWeidu() {
		return weidu;
	}
	public void setWeidu(Integer weidu) {
		this.weidu = weidu;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public String getEnumText() {
		return enumText;
	}
	public void setEnumText(String enumText) {
		this.enumText = enumText;
	}
	public String getNhTypes() {
		return nhTypes;
	}
	public void setNhTypes(String nhTypes) {
		this.nhTypes = nhTypes;
	}
	public String getWeidus() {
		return weidus;
	}
	public void setWeidus(String weidus) {
		this.weidus = weidus;
	}
	
}
