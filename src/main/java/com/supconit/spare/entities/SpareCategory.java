
package com.supconit.spare.entities;

import java.util.List;

import com.supconit.common.web.entities.AuditExtend;



public class SpareCategory extends AuditExtend{    
	
    	private static final long	serialVersionUID	= 1L;
        
        private String categoryCode;
        private String categoryName;
        private Long parentId;
        private String remark;
        private List<Long> ids;
        
        
        public List<Long> getIds() {
			return ids;
		}
		public void setIds(List<Long> ids) {
			this.ids = ids;
		}
		public String getCategoryCode(){
            return categoryCode;
        }
        public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	    }        
        public String getCategoryName(){
            return categoryName;
        }
        public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	    }        
        public Long getParentId(){
            return parentId;
        }
        public void setParentId(Long parentId) {
		this.parentId = parentId;
	    }        
        public String getRemark(){
            return remark;
        }
        public void setRemark(String remark) {
		this.remark = remark;
	    }  
      //浠ヤ笅瀛楁闈炴暟鎹簱涓殑瀹為檯瀛楁锛岀壒涓烘煡璇㈡坊鍔�       
        private String parentName;
        private List<Long> categoryIds;      
        public String getParentName() {
            return parentName;
        }
        public void setParentName(String parentName) {
            this.parentName = parentName;
        }
        public List<Long> getCategoryIds() {
            return categoryIds;
        }
        public void setCategoryIds(List<Long> categoryIds) {
            this.categoryIds = categoryIds;
        }

}

