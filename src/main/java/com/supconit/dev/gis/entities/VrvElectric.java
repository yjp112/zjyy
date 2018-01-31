
package com.supconit.dev.gis.entities;

import java.util.Date;

import com.supconit.common.utils.DateUtils;
import com.supconit.common.web.entities.AuditExtend;

public class VrvElectric extends AuditExtend {
	
    	private static final long	serialVersionUID	= 1L;
        
		private Long deviceId;
		private Date dayMonth;
		private Double electricValue;
		private String startTime;
		private String endTime;
		private String days;
		public String getDays() {
			return DateUtils.formatYyyyMMdd(this.dayMonth);
		}
		public void setDays(String days) {
			this.days = days;
		}
		public Long getDeviceId() {
			return deviceId;
		}
		public void setDeviceId(Long deviceId) {
			this.deviceId = deviceId;
		}
		public Date getDayMonth() {
			return dayMonth;
		}
		public void setDayMonth(Date dayMonth) {
			this.dayMonth = dayMonth;
		}
		public Double getElectricValue() {
			return electricValue;
		}
		public void setElectricValue(Double electricValue) {
			this.electricValue = electricValue;
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
		
}