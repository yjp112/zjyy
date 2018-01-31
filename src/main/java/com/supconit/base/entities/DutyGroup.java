
package com.supconit.base.entities;

import com.supconit.common.web.entities.AuditExtend;

public class DutyGroup extends AuditExtend{    
	
    	private static final long	serialVersionUID	= 1L;
        
        private String groupCode;
        private String groupName;
        private String remark;
        private Long parentId;
        private String parentName;
        private Long depId;
        private String depName;
        private String postName;
    
        public String getGroupCode(){
            return groupCode;
        }
        public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	    }        
        public String getGroupName(){
            return groupName;
        }
        public void setGroupName(String groupName) {
		this.groupName = groupName;
	    }        
        public String getRemark(){
            return remark;
        }
        public void setRemark(String remark) {
		this.remark = remark;
	    }
		public Long getParentId() {
			return parentId;
		}
		public void setParentId(Long parentId) {
			this.parentId = parentId;
		}
		public String getParentName() {
			return parentName;
		}
		public void setParentName(String parentName) {
			this.parentName = parentName;
		}

        public Long getDepId() {
            return depId;
        }

        public void setDepId(Long depId) {
            this.depId = depId;
        }

        public String getDepName() {
            return depName;
        }

        public void setDepName(String depName) {
            this.depName = depName;
        }

        public String getPostName() {
            return postName;
        }

        public void setPostName(String postName) {
            this.postName = postName;
        }
}

