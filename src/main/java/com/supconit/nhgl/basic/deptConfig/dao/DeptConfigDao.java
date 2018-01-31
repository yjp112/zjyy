package com.supconit.nhgl.basic.deptConfig.dao;

import hc.base.domains.Pageable;

import java.util.List;
import java.util.Set;

import com.supconit.common.daos.BaseDao;
import com.supconit.nhgl.basic.deptConfig.entities.DeptConfig;

public interface DeptConfigDao extends BaseDao<DeptConfig, Long>{
	Pageable<DeptConfig> findByCondition(Pageable<DeptConfig> pager, DeptConfig condition);
	Pageable<DeptConfig> findUnusedDevice(Pageable<DeptConfig> pager, DeptConfig condition);
	Pageable<DeptConfig> findRepeatDevice(Pageable<DeptConfig> pager, DeptConfig condition);
	Pageable<DeptConfig> findErrorFtxs(Pageable<DeptConfig> pager, DeptConfig condition);
	List<DeptConfig> findByDeptIdAndRule(Long deptId,Integer ruleFlag,Integer nhtype);
	List<DeptConfig> findErrorIsSum(String nhType);
	DeptConfig findById(Long id,Integer nhtype);
	List<Long> findDeptIds(String nhType);
	public int deleteByIds(Long[] ids);
	public int deleteByDeptId(Long deptId,String nhtype);
	int countFindAll(DeptConfig deptConfig);
	Long getByDeptId(Long id);
	List<DeptConfig> findAllDevicesWithOutRoot();
	List<DeptConfig> findAllDept();
	void deleteByDeptIdAndNhtype(Set<Long> ids,String nhType);
	List<DeptConfig> findAllDeptConfig(DeptConfig condition);
	void deleteAll();
	List<DeptConfig> findDevicesMapWithOutRoot();
}
