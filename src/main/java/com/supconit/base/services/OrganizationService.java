
package com.supconit.base.services;

import java.util.List;

import com.supconit.base.domains.Organization;
import com.supconit.base.entities.ExPosition;



public interface OrganizationService {	

	public	String getFullDeptNameByDeptId(long deptId);
	public	List<Organization> getFullDeptNameByPersonId(long personId);
	public	Organization getPersonPositionByOrganization(Organization org);
	public	List<Organization> getPersonCodesByRoleCode(String roleCode);
	public	List<Organization> getPersonListByDeptAndLunch(Organization org);
	public List<ExPosition> findSubPositionById(Long departmentId); 
		
}

