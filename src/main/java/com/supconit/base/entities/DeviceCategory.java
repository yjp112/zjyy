
package com.supconit.base.entities;

import java.util.List;

import com.supconit.common.web.entities.AuditExtend;

public class DeviceCategory extends AuditExtend{    
	
    	private static final long	serialVersionUID	= 1L;
        
        private String categoryCode;
        private String categoryName;
        private Long parentId;
        private String remark;
        private Long lastNode;
        private String parentName;
        private Long sortIndex;
        private String fullLevelName;
        private List<Long> childIds;
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
		public String getParentName() {
			return parentName;
		}
		public void setParentName(String parentName) {
			this.parentName = parentName;
		}
		public Long getLastNode() {
			return lastNode;
		}
		public void setLastNode(Long lastNode) {
			this.lastNode = lastNode;
		}
		public List<Long> getChildIds() {
			return childIds;
		}
		public void setChildIds(List<Long> childIds) {
			this.childIds = childIds;
		}
		public Long getSortIndex() {
			return sortIndex;
		}
		public void setSortIndex(Long sortIndex) {
			this.sortIndex = sortIndex;
		}
		public String getFullLevelName() {
			return fullLevelName;
		}
		public void setFullLevelName(String fullLevelName) {
			this.fullLevelName = fullLevelName;
		}
        
}

