package com.supconit.nhgl.analyse.electric.entities;

import com.supconit.common.web.entities.AuditExtend;


/**
 * 时间维度表
 * @author zk
 *
 */
public class StatisticalPropDay extends AuditExtend{

	private static final long serialVersionUID = 7193980609039229909L;
	
	private Long id;//主键
	private Integer dayOfWeekKey;
	private String dayOfWeekValue1;
	private String dayOfWeekValue2;
	private String dayOfMonthKey;
	private String dayOfMonthValue1;
	private String dayOfMonthValue2;
	private String monthKey;
	private String monthValue1;
	private String monthValue2;
	private Integer yearKey;
	private String yearValue1;
	private String yearValue2;
	private Integer quarterKey;
	private String quarterValue1;
	private String quarterValue2;
	private Integer flag;//0:工作日；1：周末；2：节假日
	private String falgValue;
	private Integer weekOfYearKey;
	private String weekOfYearValue1;
	private String weekOfYearValue2;
	
	//虚拟字段
	private String start;//开始日期
	private String end;//结束日期
	private String startTime;//开始日期
	private String endTime;//结束日期
	
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getDayOfWeekKey() {
		return dayOfWeekKey;
	}
	public void setDayOfWeekKey(Integer dayOfWeekKey) {
		this.dayOfWeekKey = dayOfWeekKey;
	}
	public String getDayOfWeekValue1() {
		return dayOfWeekValue1;
	}
	public void setDayOfWeekValue1(String dayOfWeekValue1) {
		this.dayOfWeekValue1 = dayOfWeekValue1;
	}
	public String getDayOfWeekValue2() {
		return dayOfWeekValue2;
	}
	public void setDayOfWeekValue2(String dayOfWeekValue2) {
		this.dayOfWeekValue2 = dayOfWeekValue2;
	}
	
	public String getDayOfMonthKey() {
		return dayOfMonthKey;
	}
	public void setDayOfMonthKey(String dayOfMonthKey) {
		this.dayOfMonthKey = dayOfMonthKey;
	}
	public String getDayOfMonthValue1() {
		return dayOfMonthValue1;
	}
	public void setDayOfMonthValue1(String dayOfMonthValue1) {
		this.dayOfMonthValue1 = dayOfMonthValue1;
	}
	public String getDayOfMonthValue2() {
		return dayOfMonthValue2;
	}
	public void setDayOfMonthValue2(String dayOfMonthValue2) {
		this.dayOfMonthValue2 = dayOfMonthValue2;
	}
	public String getMonthKey() {
		return monthKey;
	}
	public void setMonthKey(String monthKey) {
		this.monthKey = monthKey;
	}
	public String getMonthValue1() {
		return monthValue1;
	}
	public void setMonthValue1(String monthValue1) {
		this.monthValue1 = monthValue1;
	}
	public String getMonthValue2() {
		return monthValue2;
	}
	public void setMonthValue2(String monthValue2) {
		this.monthValue2 = monthValue2;
	}
	public Integer getYearKey() {
		return yearKey;
	}
	public void setYearKey(Integer yearKey) {
		this.yearKey = yearKey;
	}
	public String getYearValue1() {
		return yearValue1;
	}
	public void setYearValue1(String yearValue1) {
		this.yearValue1 = yearValue1;
	}
	public String getYearValue2() {
		return yearValue2;
	}
	public void setYearValue2(String yearValue2) {
		this.yearValue2 = yearValue2;
	}
	public Integer getQuarterKey() {
		return quarterKey;
	}
	public void setQuarterKey(Integer quarterKey) {
		this.quarterKey = quarterKey;
	}
	public String getQuarterValue1() {
		return quarterValue1;
	}
	public void setQuarterValue1(String quarterValue1) {
		this.quarterValue1 = quarterValue1;
	}
	public String getQuarterValue2() {
		return quarterValue2;
	}
	public void setQuarterValue2(String quarterValue2) {
		this.quarterValue2 = quarterValue2;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public String getFalgValue() {
		return falgValue;
	}
	public void setFalgValue(String falgValue) {
		this.falgValue = falgValue;
	}
	public Integer getWeekOfYearKey() {
		return weekOfYearKey;
	}
	public void setWeekOfYearKey(Integer weekOfYearKey) {
		this.weekOfYearKey = weekOfYearKey;
	}
	public String getWeekOfYearValue1() {
		return weekOfYearValue1;
	}
	public void setWeekOfYearValue1(String weekOfYearValue1) {
		this.weekOfYearValue1 = weekOfYearValue1;
	}
	public String getWeekOfYearValue2() {
		return weekOfYearValue2;
	}
	public void setWeekOfYearValue2(String weekOfYearValue2) {
		this.weekOfYearValue2 = weekOfYearValue2;
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
	
	
}
