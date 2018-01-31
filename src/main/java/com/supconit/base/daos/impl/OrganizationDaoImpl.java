package com.supconit.base.daos.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.base.daos.OrganizationDao;
import com.supconit.base.domains.Organization;
import com.supconit.base.entities.ExPosition;
import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.honeycomb.business.organization.entities.Department;

@Repository
public class OrganizationDaoImpl extends AbstractBaseDao<Organization, Long> implements OrganizationDao {

	@Override
	protected String getNamespace() {
		return Organization.class.getName();
	}

	@Override
	public String getFullDeptNameByDeptId(long deptId) {
		return selectOne("getFullDeptNameByDeptId", deptId);
	}

	@Override
	public List<Organization> getFullDeptNameByPersonId(long personId) {
		return selectList("getFullDeptNameByPersonId", personId);
	}

	@Override
	public List<Organization> getPersonPositionByOrganization(Organization org) {
		return selectList("getPersonPositionByOrganization", org);
	}

	@Override
	public List<Organization> getPersonCodesByRoleCode(String roleCode) {
		Map param = new HashMap<>(1);
		param.put("roleCode", roleCode);
		return selectList("getPersonCodesByRoleCode", param);
	}

	@Override
	public List<Organization> getPersonListByDeptAndLunch(Organization org) {
		return selectList("getPersonListByDeptAndLunch", org);
	}

	@Override
	public List<ExPosition> findSubPostionById(Department depart) {
		// TODO Auto-generated method stub
		return  selectList("findSubPostionById", depart);
	}

	@Override
	public Department findDepartmentById(Long departmentId) {
		// TODO Auto-generated method stub
		 return selectOne("findDepartmentById", departmentId);
	}



}
