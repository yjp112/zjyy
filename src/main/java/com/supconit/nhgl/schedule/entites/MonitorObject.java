/**
 * 
 */
package com.supconit.nhgl.schedule.entites;

/**
 * @author 
 * @create 2014-06-16 18:01:30 
 * @since 
 * 
 */
public class MonitorObject extends hc.base.domains.LongId{

	private static final long	serialVersionUID	= 1L;
		
			
	private String taskCode;		
	private String deviceCode;	
	
	private Long deviceId;
	private String deviceName;		
	private Long catagoryId	;
	private String catagoryCode;
	private Long locationId;
	private Long departmentId;
					
	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public String getTaskCode() {
		return taskCode;
	}
	
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
				
	public String getDeviceCode() {
		return deviceCode;
	}
	
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public Long getCatagoryId() {
		return catagoryId;
	}

	public void setCatagoryId(Long catagoryId) {
		this.catagoryId = catagoryId;
	}

	public String getCatagoryCode() {
		return catagoryCode;
	}

	public void setCatagoryCode(String catagoryCode) {
		this.catagoryCode = catagoryCode;
	}

	
}
