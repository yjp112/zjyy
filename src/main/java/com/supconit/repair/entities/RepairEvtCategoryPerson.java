package com.supconit.repair.entities;

import java.util.List;

import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.OrganizationUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.web.entities.AuditExtend;


public class RepairEvtCategoryPerson extends AuditExtend{    
	
    	private static final long	serialVersionUID	= 1L;
        
        private Long categoryId;//事件分类ID
		private Long parentId;
        private Long areaId;//地理区域ID
        private Long personId;//主管工程师或维修负责人ID
		private String categoryName;
		private String personName;
		private Long departmentId;//部门ID
		private Integer repairMode;//维修方式 0：委外 1：自修
		private String departmentName;//服务人员部门名称
		private String telephone;//服务人员电话
		private String areaName;//区域名称
		private Long repairGroupId;//维修班组ID
		private String repairGroupName;//维修班组名称
		private Integer categoryType;//分类类别 1：维修事件 2 ：设备类别

		private String repairModeName;//维修班组名称
		private List<Long> lstLocationId; 
		private List<Long> lstCategoryId;
		private List<Long> deptId;
		private Long oldCategoryId;
		private Long oldPersonId;
		private Integer oldRepairMode;
		private Long oldRepairGroupId;
		private Integer oldCategoryType;
		
		public RepairEvtCategoryPerson() {
		}
		
		public RepairEvtCategoryPerson(RepairEvtCategoryPerson repairevtcategoryperson) {
			
			this.categoryType=repairevtcategoryperson.getCategoryType();
			this.categoryId=repairevtcategoryperson.getCategoryId();
			this.parentId=repairevtcategoryperson.getParentId();
		    this.areaId=repairevtcategoryperson.getAreaId();
		    this.personId=repairevtcategoryperson.getPersonId();
			this.categoryName=repairevtcategoryperson.getCategoryName();
			this.personName=repairevtcategoryperson.getPersonName();
			this.departmentId=repairevtcategoryperson.getDepartmentId();
			this.departmentName=repairevtcategoryperson.getDepartmentName();
			this.telephone=repairevtcategoryperson.getTelephone();
			this.areaName=repairevtcategoryperson.getAreaName();
			this.setCreateId(repairevtcategoryperson.getCreateId());
			this.setCreateDate(repairevtcategoryperson.getCreateDate());
			this.setCreator(repairevtcategoryperson.getCreator());
		}
		
		public Integer getRepairMode() {
			return repairMode;
		}

		public void setRepairMode(Integer repairMode) {
			this.repairMode = repairMode;
		}
		
        public String getAreaName() {
			return areaName;
		}
		public void setAreaName(String areaName) {
			this.areaName = areaName;
		}
        public String getCategoryName() {
			return categoryName;
		}
		public void setCategoryName(String categoryName) {
			this.categoryName = categoryName;
		}
		public Long getDepartmentId() {
			return departmentId;
		}
		public void setDepartmentId(Long departmentId) {
			this.departmentId = departmentId;
		}
		public List<Long> getDeptId() {
			return deptId;
		}

		public void setDeptId(List<Long> deptId) {
			this.deptId = deptId;
		}
		
		public Long getRepairGroupId() {
			return repairGroupId;
		}

		public void setRepairGroupId(Long repairGroupId) {
			this.repairGroupId = repairGroupId;
		}

		public String getRepairGroupName() {
			return repairGroupName;
		}

		public void setRepairGroupName(String repairGroupName) {
			this.repairGroupName = repairGroupName;
		}

		public String getTelephone() {
			return telephone;
		}
		public void setTelephone(String telephone) {
			this.telephone = telephone;
		}
		public void setDepartmentName(String departmentName) {
				this.departmentName = departmentName;
		}
		public String getPersonName() {
			return personName;
		}
		public void setPersonName(String personName) {
			this.personName = personName;
		}
		public List<Long> getLstCategoryId() {
			return lstCategoryId;
		}
		public void setLstCategoryId(List<Long> lstCategoryId) {
			this.lstCategoryId = lstCategoryId;
		}
		public List<Long> getLstLocationId() {
			return lstLocationId;
		}
		public void setLstLocationId(List<Long> lstLocationId) {
			this.lstLocationId = lstLocationId;
		}
    	public Long getCategoryId() {
			return categoryId;
		}
		public void setCategoryId(Long categoryId) {
			this.categoryId = categoryId;
		}
		public Long getParentId() {
			return parentId;
		}
		public void setParentId(Long parentId) {
			this.parentId = parentId;
		}
		public Long getAreaId() {
			return areaId;
		}
		public void setAreaId(Long areaId) {
			this.areaId = areaId;
		}
		public Long getPersonId() {
			return personId;
		}
		public void setPersonId(Long personId) {
			this.personId = personId;
		}
		public String getRepairModeName() {
			return DictUtils.getDictLabel(DictTypeEnum.REPAIR_MODE, this.repairMode.toString());
		}

		public void setRepairModeName(String repairModeName) {
			this.repairModeName = repairModeName;
		}
		public Integer getCategoryType() {
			return categoryType;
		}

		public void setCategoryType(Integer categoryType) {
			this.categoryType = categoryType;
		}
		public Long getOldCategoryId() {
			return oldCategoryId;
		}

		public void setOldCategoryId(Long oldCategoryId) {
			this.oldCategoryId = oldCategoryId;
		}

		public Long getOldPersonId() {
			return oldPersonId;
		}

		public void setOldPersonId(Long oldPersonId) {
			this.oldPersonId = oldPersonId;
		}

		public Integer getOldRepairMode() {
			return oldRepairMode;
		}

		public void setOldRepairMode(Integer oldRepairMode) {
			this.oldRepairMode = oldRepairMode;
		}

		public Long getOldRepairGroupId() {
			return oldRepairGroupId;
		}

		public void setOldRepairGroupId(Long oldRepairGroupId) {
			this.oldRepairGroupId = oldRepairGroupId;
		}

		public Integer getOldCategoryType() {
			return oldCategoryType;
		}

		public void setOldCategoryType(Integer oldCategoryType) {
			this.oldCategoryType = oldCategoryType;
		}

		//----------------------------------------------
		public String getDepartmentName(){
			if(this.personId==null){
				return "";
			}else{
				
			}
			return OrganizationUtils.joinFullDeptName(OrganizationUtils.getFullDeptNameByPersonId(this.personId));
		}
}

