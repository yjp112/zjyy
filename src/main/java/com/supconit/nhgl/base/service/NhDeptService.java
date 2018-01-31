package com.supconit.nhgl.base.service;

import hc.base.domains.Pagination;
import hc.orm.BasicOrmService;

import java.util.List;

import com.supconit.nhgl.base.entities.NhDept;

public interface NhDeptService extends BasicOrmService<NhDept,Long>{
	List<NhDept> findById(Long id);
	List<NhDept> findAll();
	List<NhDept> findAllChildren(Long id);
	List<Long> findAllChildrenIds(Long id);
	List<NhDept> findByNoIds(List<Long> ids );
	List<NhDept> findByCon(NhDept dept);
	NhDept findDeptTypeByPid(NhDept dept);
	List<NhDept> getTreeById(Integer deptId);
	Long findRootId();
	/**
	 * 获取部门面积
	 * @param deptEMonth
	 * @return
	 */
	List<NhDept> getDeptArea(NhDept deptEMonth);
	void findByCondition(Pagination<NhDept> pager, NhDept condition);
	List<NhDept> findByConfig(Long nhType); 
}
