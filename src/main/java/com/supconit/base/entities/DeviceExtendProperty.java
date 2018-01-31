
package com.supconit.base.entities;

import hc.base.domains.AuditedEntity;

public class DeviceExtendProperty extends AuditedEntity{    
	
    	private static final long	serialVersionUID	= 1L;
        
        private Long deviceId;
        private Long propertyId;
        private String propertyValue;
        
        public Long getDeviceId(){
            return deviceId;
        }
        public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	    }        
        public Long getPropertyId(){
            return propertyId;
        }
        public void setPropertyId(Long propertyId) {
		this.propertyId = propertyId;
	    }        
        public String getPropertyValue(){
            return propertyValue;
        }
        public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	    }

        
}

