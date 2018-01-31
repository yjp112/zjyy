package com.supconit.nhgl.basic.areaConfig.service;

import hc.base.domains.Pageable;

import java.util.List;
import java.util.Map;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.nhgl.basic.areaConfig.entities.AreaConfig;
import com.supconit.nhgl.basic.areaConfig.entities.ExportAreaConfig;


public interface AreaConfigService extends BaseBusinessService<AreaConfig,Long>{
	Pageable<AreaConfig> findByCondition(Pageable<AreaConfig> pager, AreaConfig condition);
	void deleteByAreaId(Long areaId,String nhtype);
	int countFindAll(AreaConfig areaConfig);
	AreaConfig getByCondition(AreaConfig areaConfig);
	List<AreaConfig> findByAreaIdAndRule(AreaConfig condition, Integer ruleFlagPlus);    
	List<AreaConfig> setAreaId(List<String> lstErrMsg,List<AreaConfig> lstAreaConfig);
	void insertList(List<AreaConfig> areaConfiglst);
	
	Pageable<AreaConfig> findUnusedDevice(Pageable<AreaConfig> pager, AreaConfig condition);
	Pageable<AreaConfig> findRepeatDevice(Pageable<AreaConfig> pager, AreaConfig condition);
	Pageable<AreaConfig> findErrorFtxs(Pageable<AreaConfig> pager, AreaConfig condition);
	List<AreaConfig> findErrorIsSum(String nhType);
	List<ExportAreaConfig> findAreaConfigByNhType(AreaConfig condition);
	Map<String,Integer> findDevicesMapWithOutRoot();
	Map<Long,String> findAllArea();
}
