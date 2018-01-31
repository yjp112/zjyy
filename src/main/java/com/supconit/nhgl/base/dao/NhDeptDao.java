package com.supconit.nhgl.base.dao;

import hc.base.domains.Pagination;
import hc.orm.BasicDao;

import java.util.List;

import com.supconit.nhgl.base.entities.NhDept;


public interface NhDeptDao extends BasicDao<NhDept,Long>{
	List<NhDept> findById(Long id);
	List<NhDept> findAll();
	List<NhDept> findAllChildren(Long id);
	List<Long> findAllChildrenIds(Long id);
	List<NhDept> findByNoIds(List<Long> ids );
	List<NhDept> findByCon(NhDept dept);
	NhDept findDeptTypeByPid(NhDept dept);
	Long findRootId();
	List<NhDept> getTreeById(Integer deptId);
	List<NhDept> getDeptArea(NhDept deptEMonth);
	void findByCondition(Pagination<NhDept> pager, NhDept condition);
	List<NhDept> findByConfig(Long nhType); 
}
