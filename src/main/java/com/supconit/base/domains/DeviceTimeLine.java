package com.supconit.base.domains;

import java.io.Serializable;
import java.util.List;

/**
 * 设备时间轴
 * @author yuhuan
 * @日期 2015/09
 */
public class DeviceTimeLine implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String year;
	private String monthAndDay;
	private String content;
	private String url;
	private List<DeviceTimeLine> deviceTimeLineList;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonthAndDay() {
		return monthAndDay;
	}
	public void setMonthAndDay(String monthAndDay) {
		this.monthAndDay = monthAndDay;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<DeviceTimeLine> getDeviceTimeLineList() {
		return deviceTimeLineList;
	}
	public void setDeviceTimeLineList(List<DeviceTimeLine> deviceTimeLineList) {
		this.deviceTimeLineList = deviceTimeLineList;
	}
	
}
