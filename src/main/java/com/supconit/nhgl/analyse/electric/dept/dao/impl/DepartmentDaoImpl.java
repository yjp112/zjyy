package com.supconit.nhgl.analyse.electric.dept.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.analyse.electric.dept.dao.DepartmentDao;
import com.supconit.nhgl.analyse.electric.dept.entities.Department;

import hc.orm.AbstractBasicDaoImpl;

@Repository
public class DepartmentDaoImpl extends AbstractBasicDaoImpl<Department, Long> implements DepartmentDao {
	private static final String NAMESPACE = Department.class.getName();
	
	/**
	 * @方法名: findByCon
	 * @创建日期: 2014-5-7
	 * @开发人员:高文龙
	 * @描述:获取对应的部门列表
	 */
	@Override
	public List<Department> findByCon(Department dept) {
		return selectList("findByCon", dept);
	}
	
	@Override
	public List<Department> findById(Long id) {
		return selectList("findById", id);
	}

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public List<Department> findByConEle(Department dept) {
		return selectList("findByConEle", dept);
	}

	@Override
	public List<Department> findByConGas(Department dept) {
		return selectList("findByConGas", dept);
	}

	@Override
	public List<Department> findByConWater(Department dept) {
		return selectList("findByConWater", dept);
	}

	@Override
	public List<Department> findByConEnergy(Department dept) {
		return selectList("findByConEnergy", dept);
	}

	@Override
	public List<Department> findAllChildren(Long id) {
		return selectList("findAllChildren",id);
	}

	@Override
	public List<Long> findAllChildrenIds(Long id) {
		return selectList("findAllChildrenIds",id);
	}
}
