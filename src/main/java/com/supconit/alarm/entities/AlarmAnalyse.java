package com.supconit.alarm.entities;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.supconit.common.web.entities.AuditExtend;

public class AlarmAnalyse extends AuditExtend{
	private static final long serialVersionUID = 1L;
	
	private String alarmMonth;//报警月份
	private String alarmMonths;//报警月份
	private Long alarmNum;//报警数量
	private Date startMonth;//开始月份
	private String startMonths;//开始月份
	private Date endMonth;//结束月份
	private String endMonths;//结束月份
	private String alarmYear;//报警年份
	private String alarmArea;//报警区域
	private String alarmDept;//报警部门
	
	private String name;
	private Long num;
	private Long deviceId;
	private Long areaId;
	private Long deptId;
	
	private List<Long> deviceIds;
	private List<Long> areaIds;
	private List<Long> deptIds;
	
	
	public void setAlarmMonth(String alarmMonth) {
		this.alarmMonth = alarmMonth;
	}
	public Long getAlarmNum() {
		return alarmNum;
	}
	public String getAlarmMonths() {
		return alarmMonths;
	}
	public void setAlarmMonths(String alarmMonths) {
		this.alarmMonths = alarmMonths;
	}
	public void setAlarmNum(Long alarmNum) {
		this.alarmNum = alarmNum;
	}
	public Date getStartMonth() {
		return startMonth;
	}
	public void setStartMonth(Date startMonth) {
		this.startMonth = startMonth;
	}
	public String getStartMonths() {
		return startMonths;
	}
	public void setStartMonths(String startMonths) {
		this.startMonths = startMonths;
	}
	public Date getEndMonth() {
		return endMonth;
	}
	public void setEndMonth(Date endMonth) {
		this.endMonth = endMonth;
	}
	public String getEndMonths() {
		return endMonths;
	}
	public void setEndMonths(String endMonths) {
		this.endMonths = endMonths;
	}
	public String getAlarmYear() {
		return alarmYear;
	}
	public void setAlarmYear(String alarmYear) {
		this.alarmYear = alarmYear;
	}
	public String getAlarmArea() {
		return alarmArea;
	}
	public void setAlarmArea(String alarmArea) {
		this.alarmArea = alarmArea;
	}
	public String getAlarmDept() {
		return alarmDept;
	}
	public void setAlarmDept(String alarmDept) {
		this.alarmDept = alarmDept;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getNum() {
		return num;
	}
	public void setNum(Long num) {
		this.num = num;
	}
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	public Long getAreaId() {
		return areaId;
	}
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public List<Long> getDeviceIds() {
		return deviceIds;
	}
	public void setDeviceIds(List<Long> deviceIds) {
		this.deviceIds = deviceIds;
	}
	public List<Long> getAreaIds() {
		return areaIds;
	}
	public void setAreaIds(List<Long> areaIds) {
		this.areaIds = areaIds;
	}
	public List<Long> getDeptIds() {
		return deptIds;
	}
	public void setDeptIds(List<Long> deptIds) {
		this.deptIds = deptIds;
	}
	
	//转义--------------------------------------
	public String getAlarmMonth() {
		if(StringUtils.isEmpty(alarmMonth)){
			return "";
		}
		String arr[] = alarmMonth.split("-");
		alarmMonth = arr[0] + (arr[1].length()==1? "0"+arr[1]:arr[1]);
		return alarmMonth;
	}
}
