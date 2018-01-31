package com.supconit.nhgl.analyse.electric.dept.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.supconit.nhgl.analyse.electric.dept.dao.DepartmentDao;
import com.supconit.nhgl.analyse.electric.dept.entities.Department;
import com.supconit.nhgl.analyse.electric.dept.service.DepartmentService;

@Repository
public class DepartmentServicesImpl implements DepartmentService {
	@Autowired
	private DepartmentDao	dao;
	
	/**
	 * 
	 * @方法名: findByCon
	 * @创建日期: 2014-5-7
	 * @开发人员:高文龙
	 * @描述:获取对应的部门列表
	 */
	@Override
	public List<Department> findByCon(Department dept) {
		return dao.findByCon(dept);
	}

	@Override
	public List<Department> findById(Long id) {
		return dao.findById(id);
	}

	@Override
	public List<Department> findByConEle(Department dept) {
		return dao.findByConEle(dept);
	}

	@Override
	public List<Department> findByConWater(Department dept) {
		return dao.findByConWater(dept);
	}

	@Override
	public List<Department> findByConGas(Department dept) {
		return dao.findByConGas(dept);
	}

	@Override
	public List<Department> findByConEnergy(Department dept) {
		return dao.findByConEnergy(dept);
	}

	@Override
	public List<Department> findAllChildren(Long id) {
		return dao.findAllChildren(id);
	}

	@Override
	public List<Long> findAllChildrenIds(Long id) {
		return dao.findAllChildrenIds(id);
	}
	
}
