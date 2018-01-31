
package com.supconit.base.entities;


import hc.base.domains.AuditedEntity;



public class DevicePionts extends AuditedEntity{    
	
    	private static final long	serialVersionUID	= 1L;
        
        private Long deviceId;
        private String pointCode;
        private String pointDesc;
        private Long pointType;
        private Long status;
        private Long alarmH;
        private Long alarmHh;
        private Long alarmL;
        private Long alarmLl;
    
        public Long getDeviceId(){
            return deviceId;
        }
        public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	    }        
        public String getPointCode(){
            return pointCode;
        }
        public void setPointCode(String pointCode) {
		this.pointCode = pointCode;
	    }        
        public String getPointDesc(){
            return pointDesc;
        }
        public void setPointDesc(String pointDesc) {
		this.pointDesc = pointDesc;
	    }        
        public Long getPointType(){
            return pointType;
        }
        public void setPointType(Long pointType) {
		this.pointType = pointType;
	    }        
        public Long getStatus(){
            return status;
        }
        public void setStatus(Long status) {
		this.status = status;
	    }        
        public Long getAlarmH(){
            return alarmH;
        }
        public void setAlarmH(Long alarmH) {
		this.alarmH = alarmH;
	    }        
        public Long getAlarmHh(){
            return alarmHh;
        }
        public void setAlarmHh(Long alarmHh) {
		this.alarmHh = alarmHh;
	    }        
        public Long getAlarmL(){
            return alarmL;
        }
        public void setAlarmL(Long alarmL) {
		this.alarmL = alarmL;
	    }        
        public Long getAlarmLl(){
            return alarmLl;
        }
        public void setAlarmLl(Long alarmLl) {
		this.alarmLl = alarmLl;
	    }        
}
