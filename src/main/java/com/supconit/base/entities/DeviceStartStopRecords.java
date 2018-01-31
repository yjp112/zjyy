
package com.supconit.base.entities;

import java.util.Date;

import hc.base.domains.AuditedEntity;


public class DeviceStartStopRecords extends AuditedEntity{    
	
    	private static final long	serialVersionUID	= 1L;
        
        private Long deviceId;
        private Date startTime;
        private Date stopTime;
        private Double runTimes;
        private Date updateTime;
    
        public Long getDeviceId(){
            return deviceId;
        }
        public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	    }        
        public Date getStartTime(){
            return startTime;
        }
        public void setStartTime(Date startTime) {
		this.startTime = startTime;
	    }        
        public Date getStopTime(){
            return stopTime;
        }
        public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	    }        
        public Double getRunTimes(){
            return runTimes;
        }
        public void setRunTimes(Double runTimes) {
		this.runTimes = runTimes;
	    }        
        public Date getUpdateTime(){
            return updateTime;
        }
        public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	    }        
}

