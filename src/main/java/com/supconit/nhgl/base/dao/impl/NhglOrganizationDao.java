package com.supconit.nhgl.base.dao.impl;

import java.util.List;

import com.supconit.base.entities.ExPosition;
import com.supconit.common.daos.BaseDao;
import com.supconit.nhgl.base.entities.NhDept;
import com.supconit.nhgl.base.entities.NhglOrganization;

public interface NhglOrganizationDao extends BaseDao<NhglOrganization, Long>{
	public	String getFullDeptNameByDeptId(long deptId);
	public	List<NhglOrganization> getFullDeptNameByPersonId(long personId);
	public	List<NhglOrganization> getPersonPositionByOrganization(NhglOrganization org);
	public	List<NhglOrganization> getPersonCodesByRoleCode(String roleCode);
	public	List<NhglOrganization> getPersonListByDeptAndLunch(NhglOrganization org);
	public List<ExPosition> findSubPostionById(NhDept depart);
	public NhDept findDepartmentById(Long departmentId);  
}
