
package com.supconit.base.entities;

import hc.base.domains.AuditedEntity;

public class DeviceCategoryProperty extends AuditedEntity{    
	
    	private static final long	serialVersionUID	= 1L;
        
        private Long propertyId;
        private Long categoryId;
        private Long propertySort;
        private String propertyName;
        public Long getPropertyId(){
            return propertyId;
        }
        public void setPropertyId(Long propertyId) {
		this.propertyId = propertyId;
	    }        
        public Long getCategoryId(){
            return categoryId;
        }
        public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	    }        
        public Long getPropertySort(){
            return propertySort;
        }
        public void setPropertySort(Long propertySort) {
		this.propertySort = propertySort;
	    }
		public String getPropertyName() {
			return propertyName;
		}
		public void setPropertyName(String propertyName) {
			this.propertyName = propertyName;
		}      
        
}

