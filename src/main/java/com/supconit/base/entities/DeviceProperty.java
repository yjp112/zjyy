
package com.supconit.base.entities;

import com.supconit.common.web.entities.AuditExtend;

public class DeviceProperty extends AuditExtend{    
	
    	private static final long	serialVersionUID	= 1L;
        
        private String propertyCode;
        private String propertyName;
        private Long inputType;//1:文本输入框,2:日期选择框
        private String enumValues;
        private Long status;
        private String remark;
        private String statusName;
        private String inputTypeName;
        private String  propertyValue;
        public String getPropertyCode(){
            return propertyCode;
        }
        public void setPropertyCode(String propertyCode) {
		this.propertyCode = propertyCode;
	    }        
        public String getPropertyName(){
            return propertyName;
        }
        public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	    }        
        public Long getInputType(){
            return inputType;
        }
        public void setInputType(Long inputType) {
		this.inputType = inputType;
	    }        
        public String getEnumValues(){
            return enumValues;
        }
        public void setEnumValues(String enumValues) {
		this.enumValues = enumValues;
	    }        
        public Long getStatus(){
            return status;
        }
        public void setStatus(Long status) {
		this.status = status;
	    }        
        public String getRemark(){
            return remark;
        }
        public void setRemark(String remark) {
		this.remark = remark;
	    }
		public String getStatusName() {
			return  statusName;
		}
		public void setStatusName(String statusName) {
			this.statusName = statusName;
		}
		public String getInputTypeName() {
			return inputTypeName;
		}
		public void setInputTypeName(String inputTypeName) {
			this.inputTypeName = inputTypeName;
		}
		public String getPropertyValue() {
			return propertyValue;
		}
		public void setPropertyValue(String propertyValue) {
			this.propertyValue = propertyValue;
		}   
        
}

