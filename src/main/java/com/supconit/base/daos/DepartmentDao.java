package com.supconit.base.daos;

import java.util.List;

import com.supconit.base.entities.Department;


import hc.orm.BasicDao;

/**
 * 部门定制访问层
 * @yuhuan
 */
public interface DepartmentDao extends BasicDao<Department, Long>{
	/**
	 * 查询第一层部门
	 * @param default_departmentId 根部门Id
	 * @return
	 */
	List<Department> findFirstLevel(Long default_departmentId);
	/**
	 * 查询部门子类别
	 */
	List<Long> findDeptChildIdsById(Long id);
	/**
	 * 查询部门子类别
	 */
	List<Long> findDeptChildIdsByName(String name);
}
