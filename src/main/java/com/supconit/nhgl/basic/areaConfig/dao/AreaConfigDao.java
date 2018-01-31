package com.supconit.nhgl.basic.areaConfig.dao;

import hc.base.domains.Pageable;

import java.util.List;
import java.util.Set;

import com.supconit.common.daos.BaseDao;
import com.supconit.nhgl.basic.areaConfig.entities.AreaConfig;


public interface AreaConfigDao extends BaseDao<AreaConfig, Long>{
	Pageable<AreaConfig> findByCondition(Pageable<AreaConfig> pager, AreaConfig condition);
	public int deleteByIds(Long[] ids);
	public int deleteByAreaId(Long areaId,String nhtype);
	int countFindAll(AreaConfig areaConfig);
	AreaConfig getByCondition(AreaConfig areaConfig);
	List<AreaConfig> findByAreaIdAndRule(AreaConfig areaConfig);
	Long getByAreaId(Long id);
	List<AreaConfig> findAllDevicesWithOutRoot();
	List<AreaConfig> findAllArea();
	
	Pageable<AreaConfig> findUnusedDevice(Pageable<AreaConfig> pager, AreaConfig condition);
	Pageable<AreaConfig> findRepeatDevice(Pageable<AreaConfig> pager, AreaConfig condition);
	Pageable<AreaConfig> findErrorFtxs(Pageable<AreaConfig> pager, AreaConfig condition);
	List<AreaConfig> findErrorIsSum(String nhType);
	void deleteByAreaIdAndNhtype(Set<Long> ids,String nhType);
	List<AreaConfig> findAreaConfigByNhType(AreaConfig condition);
	void deleteAll();
	List<AreaConfig> findDevicesMapWithOutRoot();
}
