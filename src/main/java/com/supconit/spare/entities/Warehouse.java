
package com.supconit.spare.entities;


import com.supconit.common.web.entities.AuditExtend;



public class Warehouse extends AuditExtend{    
	
    	private static final long	serialVersionUID	= 1L;
        
        private String warehouseCode;
        private String warehouseName;
        private Long managerId;
        private String managerName;
        private Integer upperQty;
        private Integer safeQty;
        private Integer lowerQty;
        private String remark;
    
        public String getWarehouseCode(){
            return warehouseCode;
        }
        public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	    }        
        public String getWarehouseName(){
            return warehouseName;
        }
        public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	    }        
        public Long getManagerId(){
            return managerId;
        }
        public void setManagerId(Long managerId) {
		this.managerId = managerId;
	    }        
        public String getManagerName(){
            return managerName;
        }
        public void setManagerName(String managerName) {
		this.managerName = managerName;
	    }        
        
        public Integer getUpperQty() {
            return upperQty;
        }
        public void setUpperQty(Integer upperQty) {
            this.upperQty = upperQty;
        }
        public Integer getSafeQty() {
            return safeQty;
        }
        public void setSafeQty(Integer safeQty) {
            this.safeQty = safeQty;
        }
        public Integer getLowerQty() {
            return lowerQty;
        }
        public void setLowerQty(Integer lowerQty) {
            this.lowerQty = lowerQty;
        }
        public String getRemark(){
            return remark;
        }
        public void setRemark(String remark) {
		this.remark = remark;
	    }        
}

