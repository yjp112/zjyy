/**
 * 
 */
package com.supconit.nhgl.schedule.entites;
												

/**
 * @author 
 * @create 2014-06-16 18:01:23 
 * @since 
 * 
 */
public class MonitorObjScore extends hc.base.domains.LongId{

	private static final long	serialVersionUID	= 1L;
		
			
	private String taskCode;		
	private String deviceId;		
	private String value;		
	private String score;		
	private String crCondition;		
		
	
	public String getTaskCode() {
		return taskCode;
	}
	
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
				
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
				
	public String getScore() {
		return score;
	}
	
	public void setScore(String score) {
		this.score = score;
	}
				
	public String getCrCondition() {
		return crCondition;
	}
	
	public void setCrCondition(String crCondition) {
		this.crCondition = crCondition;
	}
	
	///////////////
	private String hpId;	
	private String deviceName;
	private String locationName;//安装位置
	public String getHpId() {
		return hpId;
	}

	public void setHpId(String hpId) {
		this.hpId = hpId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
			
	
}
