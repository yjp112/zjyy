package com.supconit.nhgl.base.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.base.entities.ExPosition;
import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.nhgl.base.dao.impl.NhglOrganizationDao;
import com.supconit.nhgl.base.entities.NhDept;
import com.supconit.nhgl.base.entities.NhglOrganization;

@Repository
public class NhglOrganizationDaoImpl extends AbstractBaseDao<NhglOrganization, Long> implements NhglOrganizationDao {

	@Override
	protected String getNamespace() {
		return NhglOrganization.class.getName();
	}

	@Override
	public String getFullDeptNameByDeptId(long deptId) {
		return selectOne("getFullDeptNameByDeptId", deptId);
	}

	@Override
	public List<NhglOrganization> getFullDeptNameByPersonId(long personId) {
		return selectList("getFullDeptNameByPersonId", personId);
	}

	@Override
	public List<NhglOrganization> getPersonPositionByOrganization(NhglOrganization org) {
		return selectList("getPersonPositionByOrganization", org);
	}

	@Override
	public List<NhglOrganization> getPersonCodesByRoleCode(String roleCode) {
		Map param = new HashMap<>(1);
		param.put("roleCode", roleCode);
		return selectList("getPersonCodesByRoleCode", param);
	}

	@Override
	public List<NhglOrganization> getPersonListByDeptAndLunch(NhglOrganization org) {
		return selectList("getPersonListByDeptAndLunch", org);
	}

	@Override
	public List<ExPosition> findSubPostionById(NhDept depart) {
		// TODO Auto-generated method stub
		return  selectList("findSubPostionById", depart);
	}

	@Override
	public NhDept findDepartmentById(Long departmentId) {
		// TODO Auto-generated method stub
		 return selectOne("findDepartmentById", departmentId);
	}



}
