package com.supconit.nhgl.basic.deptConfig.dao.impl;

import hc.base.domains.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.nhgl.basic.deptConfig.dao.DeptConfigDao;
import com.supconit.nhgl.basic.deptConfig.entities.DeptConfig;

@Repository
public class DeptConfigDaoImpl extends AbstractBaseDao<DeptConfig,Long> implements DeptConfigDao{
	private static final String NAME_SPACE=DeptConfig.class.getName();
	
	@Override
	public Pageable<DeptConfig> findByCondition(Pageable<DeptConfig> pager,
			DeptConfig condition) {
		return findByPager(pager, "findByCondition", "countByCondition", condition);
	}
	@Override
	protected String getNamespace() {
		return NAME_SPACE;
	}

	@Override
	public List<Long> findDeptIds(String nhType) {
		return selectList("findDeptIds",nhType);
	}

	@Override
	public List<DeptConfig> findByDeptIdAndRule(Long deptId, Integer ruleFlag,Integer nhType) {
		Map map=new HashMap();
		map.put("deptId", deptId);
		map.put("ruleFlag", ruleFlag);
		map.put("nhType", nhType);
		return selectList("findByDeptIdAndRule",map);
	}
	@Override
	public int deleteByIds(Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return update("deleteByIds", map);
	}
	@Override
	public int deleteByDeptId(Long deptId,String nhType) {
		Map map=new HashMap();
		map.put("deptId", deptId);
		map.put("nhType", nhType);
		return update("deleteByDeptId",map);
	}
	@Override
	public int countFindAll(DeptConfig deptConfig) {
		return selectOne("countFindAll", deptConfig); 
	}
	@Override
	public DeptConfig findById(Long id, Integer nhType) {
		Map map=new HashMap();
		map.put("id", id);
		map.put("nhType", nhType);
		return selectOne("findById",map);
	}
	@Override
	public Long getByDeptId(Long id) {
		// TODO Auto-generated method stub
		return selectOne("getByDeptId",id);
	}
	@Override
	public List<DeptConfig> findAllDevicesWithOutRoot() {
		return selectList("findAllDevicesWithOutRoot");
	}
	@Override
	public List<DeptConfig> findAllDept() {
		return selectList("findAllDept");
	}
	public Pageable<DeptConfig> findUnusedDevice(Pageable<DeptConfig> pager,
			DeptConfig condition) {
		return findByPager(pager,"findUnusedDevice","countUnusedDevice",condition);
	}
	@Override
	public Pageable<DeptConfig> findRepeatDevice(Pageable<DeptConfig> pager,
			DeptConfig condition) {
		return findByPager(pager,"findRepeatDevice","countRepeatDevice",condition);
	}
	@Override
	public Pageable<DeptConfig> findErrorFtxs(Pageable<DeptConfig> pager,
			DeptConfig condition) {
		return findByPager(pager,"findErrorFtxs","countErrorFtxs",condition);
	}
	@Override
	public List<DeptConfig> findErrorIsSum(String nhType) {
		return selectList("findErrorIsSum",nhType);
	}
	@Override
	public void deleteByDeptIdAndNhtype(Set<Long> ids, String nhType) {
		Map map=new HashMap();
		map.put("ids", ids);
		map.put("nhType", nhType);
		delete("deleteByDeptIdAndNhtype", map);
	}
	@Override
	public List<DeptConfig> findAllDeptConfig(DeptConfig condiction) {
		return selectList("findAllDeptConfig",condiction);
	}
	@Override
	public void deleteAll() {
		delete("deleteAll");
	}
	@Override
	public List<DeptConfig> findDevicesMapWithOutRoot() {
		return selectList("findDevicesMapWithOutRoot");
	}
	
}
