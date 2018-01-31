
package com.supconit.base.entities;


import hc.base.domains.AuditedEntity;


public class SubDevice extends AuditedEntity{    
	
    	private static final long	serialVersionUID	= 1L;
        
        private Long deviceId;
        private String subDeviceCode;
        private String subDeviceName;
        private String subDeviceSpec;
        private Long num;
        private String remark;
    
        public Long getDeviceId(){
            return deviceId;
        }
        public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	    }        
        public String getSubDeviceCode(){
            return subDeviceCode;
        }
        public void setSubDeviceCode(String subDeviceCode) {
		this.subDeviceCode = subDeviceCode;
	    }        
        public String getSubDeviceName(){
            return subDeviceName;
        }
        public void setSubDeviceName(String subDeviceName) {
		this.subDeviceName = subDeviceName;
	    }        
        public String getSubDeviceSpec(){
            return subDeviceSpec;
        }
        public void setSubDeviceSpec(String subDeviceSpec) {
		this.subDeviceSpec = subDeviceSpec;
	    }        
        public Long getNum(){
            return num;
        }
        public void setNum(Long num) {
		this.num = num;
	    }        
        public String getRemark(){
            return remark;
        }
        public void setRemark(String remark) {
		this.remark = remark;
	    }        
}
