package com.supconit.nhgl.analyse.electric.dept.dao;

import java.util.List;

import com.supconit.nhgl.analyse.electric.dept.entities.Department;

import hc.orm.BasicDao;

/**
 * 
 * @author 
 *
 */
public interface DepartmentDao extends BasicDao<Department, Long>{

	List<Department> findByCon(Department dept);
	List<Department> findById(Long id);
	List<Department> findAllChildren(Long id);
	List<Long> findAllChildrenIds(Long id);
	List<Department> findByConEle(Department dept);
	List<Department> findByConGas(Department dept);
	List<Department> findByConWater(Department dept);
	List<Department> findByConEnergy(Department dept);
}
