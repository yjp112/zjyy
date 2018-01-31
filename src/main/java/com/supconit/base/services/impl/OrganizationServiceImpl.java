package com.supconit.base.services.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.supconit.base.daos.OrganizationDao;
import com.supconit.base.domains.Organization;
import com.supconit.base.entities.ExPosition;
import com.supconit.base.services.OrganizationService;
import com.supconit.honeycomb.business.organization.entities.Department;

@Service
public class OrganizationServiceImpl implements OrganizationService {

	@Resource 
	private OrganizationDao organizationDao;
	@Override
	public String getFullDeptNameByDeptId(long deptId) {
		return organizationDao.getFullDeptNameByDeptId(deptId);
	}

	@Override
	public List<Organization> getFullDeptNameByPersonId(long personId) {
		return organizationDao.getFullDeptNameByPersonId(personId);
	}

	@Override
	public Organization getPersonPositionByOrganization(Organization org) {
		List<Organization> orgList = organizationDao.getPersonPositionByOrganization(org);
		return orgList.size()>0?orgList.get(0):new Organization();
	}

	@Override
	public List<Organization> getPersonCodesByRoleCode(String roleCode) {
		return organizationDao.getPersonCodesByRoleCode(roleCode);
	}

	@Override
	public List<Organization> getPersonListByDeptAndLunch(Organization org) {
		return organizationDao.getPersonListByDeptAndLunch(org);
	}

	@Override
	public List<ExPosition> findSubPositionById(Long departmentId) {
		// TODO Auto-generated method stub
		Department depart = organizationDao.findDepartmentById(departmentId);
		if(depart!=null){
			return organizationDao.findSubPostionById(depart);
		}
		return null;
	}

}
