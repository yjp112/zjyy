package com.supconit.base.daos;

import java.util.List;

import com.supconit.base.domains.Organization;
import com.supconit.base.entities.ExPosition;
import com.supconit.common.daos.BaseDao;
import com.supconit.honeycomb.business.organization.entities.Department;

public interface OrganizationDao extends BaseDao<Organization, Long>{
	public	String getFullDeptNameByDeptId(long deptId);
	public	List<Organization> getFullDeptNameByPersonId(long personId);
	public	List<Organization> getPersonPositionByOrganization(Organization org);
	public	List<Organization> getPersonCodesByRoleCode(String roleCode);
	public	List<Organization> getPersonListByDeptAndLunch(Organization org);
	public List<ExPosition> findSubPostionById(Department depart);
	public Department findDepartmentById(Long departmentId);  
}
