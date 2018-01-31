package com.supconit.nhgl.basic.deptConfig.service;

import hc.base.domains.Pageable;

import java.util.List;
import java.util.Map;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.nhgl.basic.deptConfig.entities.DeptConfig;


public interface DeptConfigService extends BaseBusinessService<DeptConfig,Long>{
	Pageable<DeptConfig> findByCondition(Pageable<DeptConfig> pager, DeptConfig condition);
	List<DeptConfig> findByDeptIdAndRule(Long deptId,Integer ruleFlag,Integer nhtype);
	List<Long> findDeptIds(String nhtype);
	void deleteByIds(Long[] ids);
	void deleteByDeptId(Long deptId,String nhtype);
	void insertList(List<DeptConfig> deptConfiglst);
	void updateList(List<DeptConfig> deptConfiglst,String nhType);
	int countFindAll(DeptConfig deptConfig); 
	List<DeptConfig> findErrorIsSum(String nhType);
	DeptConfig findById(Long id,Integer nhtype);
	Long getByDeptId(Long id); 
	List<DeptConfig> setDeviceId(List<String> lstErrMsg,List<DeptConfig> lstDeptConfig);
	Pageable<DeptConfig> findUnusedDevice(Pageable<DeptConfig> pager, DeptConfig condition);
	Pageable<DeptConfig> findRepeatDevice(Pageable<DeptConfig> pager, DeptConfig condition);
	Pageable<DeptConfig> findErrorFtxs(Pageable<DeptConfig> pager, DeptConfig condition);
	List<DeptConfig> findAllDeptConfig(DeptConfig condition);
	Map<String,Integer> findDevicesMapWithOutRoot();
	Map<Long,String> findAllDept();
}
