
package com.supconit.base.entities;

import java.util.Date;

import hc.base.domains.AuditedEntity;


public class DeviceStartStopOldRecords extends AuditedEntity{    
	
    	private static final long	serialVersionUID	= 1L;
        
        private String tagName;
        private String tagValue;
        private String tagInfo;
        private Long tagType;
        private Date changeTime;
        
        //以下字段数据库里没有
        private Long deviceId;
        
        public Long getDeviceId() {
			return deviceId;
		}
		public void setDeviceId(Long deviceId) {
			this.deviceId = deviceId;
		}
		public String getTagName(){
            return tagName;
        }
        public void setTagName(String tagName) {
		this.tagName = tagName;
	    }        
        public String getTagValue(){
            return tagValue;
        }
        public void setTagValue(String tagValue) {
		this.tagValue = tagValue;
	    }        
        public String getTagInfo(){
            return tagInfo;
        }
        public void setTagInfo(String tagInfo) {
		this.tagInfo = tagInfo;
	    }        
        public Long getTagType(){
            return tagType;
        }
        public void setTagType(Long tagType) {
		this.tagType = tagType;
	    }        
        public Date getChangeTime(){
            return changeTime;
        }
        public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	    }        
}

