/**
 * 
 */
package com.supconit.nhgl.alarm.energy.entities;
import java.util.Date;

import com.supconit.common.web.entities.AuditExtend;



/**
 * @author 
 * @create 2014-06-16 18:01:16 
 * @since 
 * 
 */
public class EnergyAlarm extends AuditExtend{

	private static final long	serialVersionUID	= 1L;
		
	private String deviceId;		
	private String deviceCode;		
	private String alarmTime;		
	private String content;	
	
	private Date start;
	private Date end;
	
		
					
	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceCode() {
		return deviceCode;
	}
	
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
				
	public String getAlarmTime() {
		return alarmTime;
	}
	
	public void setAlarmTime(String alarmTime) {
		this.alarmTime = alarmTime;
	}
				
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
			
	
}
