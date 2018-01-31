package com.supconit.nhgl.basic.meterConfig.electic.dao;

import java.util.List;

import com.supconit.nhgl.basic.meterConfig.electic.entities.ElectricConfig;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;
import hc.orm.BasicDao;



public interface ElectricConfigDao extends BasicDao<ElectricConfig, Long> {

	public Pageable<ElectricConfig> findByCondition(Pagination<ElectricConfig> pager, ElectricConfig condition);
	public Pageable<ElectricConfig> findByConditionPower(Pagination<ElectricConfig> pager, ElectricConfig condition);
	public List<ElectricConfig> findById(Long id);
	
	public ElectricConfig findByBitNo(String bitNo);

}
