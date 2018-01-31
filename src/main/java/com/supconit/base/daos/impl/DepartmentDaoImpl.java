package com.supconit.base.daos.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.base.daos.DepartmentDao;
import com.supconit.base.entities.Department;

import hc.orm.AbstractBasicDaoImpl;

@Repository
public class DepartmentDaoImpl extends AbstractBasicDaoImpl<Department, Long> implements DepartmentDao {
	private static final String NAMESPACE = Department.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	@Override
	public List<Department> findFirstLevel(Long default_departmentId) {
		Map param = new HashMap(1);
		param.put("id", default_departmentId);
		return selectList("findFirstLevel",param);
	}
	
	@Override
	public List<Long> findDeptChildIdsById(Long id) {
		return selectList("findDeptChildIdsById",id);
	}
	@Override
	public List<Long> findDeptChildIdsByName(String name) {
		return selectList("findDeptChildIdsByName",name);
	}

	
}
