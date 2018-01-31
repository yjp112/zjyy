
package com.supconit.base.entities;


import java.util.List;

import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.web.entities.AuditExtend;

public class GeoArea extends AuditExtend{    
	
    	private static final long	serialVersionUID	= 1L;
    	
        private String areaCode;
        private String areaName;
        private Long areaType;
        private String areaTypeName;

		private Long parentId;
        private String fullLevelName;
        private Long sort;
        private String remark;
        private boolean checked;
        private String parentName;
        
        private String total;//总电量
    	private int tb=2;//同比1：上升，0：持平，-1：下降
    	private String electricTotalBefore;//存储去年耗电量
    	private String waterTotalBefore;//存储去年耗水量
    	private Float percent;//标识上涨率
    	private String name;
     
		public String getTotal() {
			return total;
		}
		public Long getAreaType() {
			return areaType;
		}
		public void setAreaType(Long areaType) {
			this.areaType = areaType;
		}
		public String getAreaTypeName() {
			return DictUtils.getDictLabel(DictTypeEnum.AREA_TYPE, this.areaType.toString());		
		}
		public void setTotal(String total) {
			this.total = total;
		}
		public int getTb() {
			return tb;
		}
		public void setTb(int tb) {
			this.tb = tb;
		}
		public String getElectricTotalBefore() {
			return electricTotalBefore;
		}
		public void setElectricTotalBefore(String electricTotalBefore) {
			this.electricTotalBefore = electricTotalBefore;
		}
		public String getWaterTotalBefore() {
			return waterTotalBefore;
		}
		public void setWaterTotalBefore(String waterTotalBefore) {
			this.waterTotalBefore = waterTotalBefore;
		}
		public Float getPercent() {
			return percent;
		}
		public void setPercent(Float percent) {
			this.percent = percent;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		private List<Long> subGeoAreaList;
    
        public GeoArea() {
        }
        public GeoArea(Long id) {
			this.id=id;
		}
		public String getAreaCode(){
            return areaCode;
        }
        public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	    }        
        public String getAreaName(){
            return areaName;
        }
        public void setAreaName(String areaName) {
		this.areaName = areaName;
	    }        
        public Long getParentId(){
            return parentId;
        }
        public void setParentId(Long parentId) {
		this.parentId = parentId;
	    }        
        public String getFullLevelName(){
            return fullLevelName;
        }
        public void setFullLevelName(String fullLevelName) {
		this.fullLevelName = fullLevelName;
	    }        
        public Long getSort(){
            return sort;
        }
        public void setSort(Long sort) {
		this.sort = sort;
	    }        
        public String getRemark(){
            return remark;
        }
        public void setRemark(String remark) {
		this.remark = remark;
	    }
		public List<Long> getSubGeoAreaList() {
			return subGeoAreaList;
		}
		public void setSubGeoAreaList(List<Long> subGeoAreaList) {
			this.subGeoAreaList = subGeoAreaList;
		}
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
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = super.hashCode();
			result = prime * result + ((id == null) ? 0 : id.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!super.equals(obj))
				return false;
			if (getClass() != obj.getClass())
				return false;
			GeoArea other = (GeoArea) obj;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			return true;
		}
		@Override
		public String toString() {
			return "GeoArea [areaCode=" + areaCode + ", areaName=" + areaName
					+ ", areaType=" + areaType + ", parentId=" + parentId + ", fullLevelName="
					+ fullLevelName + ", sort=" + sort + ", remark=" + remark
					+ ", parentName=" + parentName + ", subGeoAreaList="
					+ subGeoAreaList + "]";
		}
		
		
}

