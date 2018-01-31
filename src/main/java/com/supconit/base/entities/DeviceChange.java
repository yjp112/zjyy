
package com.supconit.base.entities;

import java.util.Date;

import com.supconit.common.web.entities.AuditExtend;

public class DeviceChange extends AuditExtend{    
	
    	private static final long	serialVersionUID	= 1L;
        
        private Long deviceId;
        private String changeProperty;
        private String oldValue;
        private String newValue;
        private String statusName;
        private String deviceName;
        private String deviceCode;
        private String categoryName;
        private Date changeDate;//数据库没有      
        public Date getChangeDate() {
			return changeDate;
		}
		public void setChangeDate(Date changeDate) {
			this.changeDate = changeDate;
		}
		private Date changeDateFrom;//数据库没有
        private Date changeDateTo;//数据库没有
        
        public Long getDeviceId(){
            return deviceId;
        }
        public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	    }        
        public String getChangeProperty(){
            return changeProperty;
        }
        public void setChangeProperty(String changeProperty) {
		this.changeProperty = changeProperty;
	    }        
        public String getOldValue(){
            return oldValue;
        }
        public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	    }        
        public String getNewValue(){
            return newValue;
        }
        public void setNewValue(String newValue) {
		this.newValue = newValue;
	    }        


		public String getStatusName() {
			return statusName;
		}
		public void setStatusName(String statusName) {
			this.statusName = statusName;
		}
		public String getDeviceName() {
			return deviceName;
		}
		public void setDeviceName(String deviceName) {
			this.deviceName = deviceName;
		}
		public String getCategoryName() {
			return categoryName;
		}
		public void setCategoryName(String categoryName) {
			this.categoryName = categoryName;
		}
		public String getDeviceCode() {
			return deviceCode;
		}
		public void setDeviceCode(String deviceCode) {
			this.deviceCode = deviceCode;
		}
		public Date getChangeDateFrom() {
			return changeDateFrom;
		}
		public void setChangeDateFrom(Date changeDateFrom) {
			this.changeDateFrom = changeDateFrom;
		}
		public Date getChangeDateTo() {
			return changeDateTo;
		}
		public void setChangeDateTo(Date changeDateTo) {
			this.changeDateTo = changeDateTo;
		}   
        
}

