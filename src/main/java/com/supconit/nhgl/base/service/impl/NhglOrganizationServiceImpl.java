package com.supconit.nhgl.base.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.supconit.base.entities.ExPosition;
import com.supconit.nhgl.base.dao.impl.NhglOrganizationDao;
import com.supconit.nhgl.base.entities.NhDept;
import com.supconit.nhgl.base.entities.NhglOrganization;
import com.supconit.nhgl.base.service.NhglOrganizationService;

@Service
public class NhglOrganizationServiceImpl implements NhglOrganizationService {

	@Resource 
	private NhglOrganizationDao organizationDao;
	@Override
	public String getFullDeptNameByDeptId(long deptId) {
		return organizationDao.getFullDeptNameByDeptId(deptId);
	}

	@Override
	public List<NhglOrganization> getFullDeptNameByPersonId(long personId) {
		return organizationDao.getFullDeptNameByPersonId(personId);
	}

	@Override
	public NhglOrganization getPersonPositionByOrganization(NhglOrganization org) {
		List<NhglOrganization> orgList = organizationDao.getPersonPositionByOrganization(org);
		return orgList.size()>0?orgList.get(0):new NhglOrganization();
	}

	@Override
	public List<NhglOrganization> getPersonCodesByRoleCode(String roleCode) {
		return organizationDao.getPersonCodesByRoleCode(roleCode);
	}

	@Override
	public List<NhglOrganization> getPersonListByDeptAndLunch(NhglOrganization org) {
		return organizationDao.getPersonListByDeptAndLunch(org);
	}

	@Override
	public List<ExPosition> findSubPositionById(Long deptId) {
		// TODO Auto-generated method stub
		NhDept depart = organizationDao.findDepartmentById(deptId);
		if(depart!=null){
			return organizationDao.findSubPostionById(depart);
		}
		return null;
	}

}
