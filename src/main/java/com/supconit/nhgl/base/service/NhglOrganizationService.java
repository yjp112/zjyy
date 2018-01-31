
package com.supconit.nhgl.base.service;

import java.util.List;

import com.supconit.base.entities.ExPosition;
import com.supconit.nhgl.base.entities.NhglOrganization;



public interface NhglOrganizationService {	

	public	String getFullDeptNameByDeptId(long deptId);
	public	List<NhglOrganization> getFullDeptNameByPersonId(long personId);
	public	NhglOrganization getPersonPositionByOrganization(NhglOrganization org);
	public	List<NhglOrganization> getPersonCodesByRoleCode(String roleCode);
	public	List<NhglOrganization> getPersonListByDeptAndLunch(NhglOrganization org);
	public List<ExPosition> findSubPositionById(Long departmentId); 
		
}

