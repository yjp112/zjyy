package com.supconit.repair.entities;

import java.util.Date;
import java.util.List;

import com.supconit.common.web.entities.AuditExtend;


public class RepairEvtCategory extends AuditExtend{    
	
    	private static final long	serialVersionUID	= 1L;
        
        //private String categoryCode;
        private String categoryName;
		private Long parentId;
		private String parentName;
        private double finishtime;
        private Integer emergency;
        private String remark;
        private Integer sortidx;
        private Long creatid;
        private String creator;
        private String createdate;
        private Long updateid;

		private String updator;
        private Date updatedate;
		private List<Long> childIds;
		private  String emergencyName;
		private boolean checked;
        private String fullLevelName;
		public boolean isChecked() {
			return checked;
		}
		public void setChecked(boolean checked) {
			this.checked = checked;
		}
		public String getParentName() {
			return parentName;
		}
		public void setParentName(String parentName) {
			this.parentName = parentName;
		}
		public String getEmergencyName() {
			return emergencyName;
		}
		public void setEmergencyName(String emergencyName) {
			this.emergencyName = emergencyName;
		}
		
		public List<Long> getChildIds() {
				return childIds;
			}
			public void setChildIds(List<Long> childIds) {
				this.childIds = childIds;
			}
        
        public String getCategoryName() {
			return categoryName;
		}
		public void setCategoryName(String categoryName) {
			this.categoryName = categoryName;
		}
		public Long getParentId() {
			return parentId;
		}
		public void setParentId(Long parentId) {
			this.parentId = parentId;
		}
		public double getFinishtime() {
			return finishtime;
		}
		public void setFinishtime(double finishtime) {
			this.finishtime = finishtime;
		}
		public Integer getEmergency() {
			return emergency;
		}
		public void setEmergency(Integer emergency) {
			this.emergency = emergency;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		public Integer getSortidx() {
			return sortidx;
		}
		public void setSortidx(Integer sortidx) {
			this.sortidx = sortidx;
		}
		public Long getCreatid() {
			return creatid;
		}
		public void setCreatid(Long creatid) {
			this.creatid = creatid;
		}
		public String getCreator() {
			return creator;
		}
		public void setCreator(String creator) {
			this.creator = creator;
		}
		public String getCreatedate() {
			return createdate;
		}
		public void setCreatedate(String createdate) {
			this.createdate = createdate;
		}
		public Long getUpdateid() {
			return updateid;
		}
		public void setUpdateid(Long updateid) {
			this.updateid = updateid;
		}
		public String getUpdator() {
			return updator;
		}
		public void setUpdator(String updator) {
			this.updator = updator;
		}
		public Date getUpdatedate() {
			return updatedate;
		}
		public void setUpdatedate(Date updatedate) {
			this.updatedate = updatedate;
		}
        public String getFullLevelName() {
			return fullLevelName;
		}
		public void setFullLevelName(String fullLevelName) {
			this.fullLevelName = fullLevelName;
		}
}

