package com.supconit.synchronizeData.entities;

public class Device {
	 private static final long	serialVersionUID	= 5225454999469638741L;
	 private Long  deviceId;
	 private String  deviceName;
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
}
