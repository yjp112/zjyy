package com.supconit.nhgl.basic.meterConfig.electic.service;

import java.util.List;

import com.supconit.nhgl.basic.meterConfig.electic.entities.ElectricConfig;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;
import hc.orm.BasicOrmService;



public interface ElectricConfigService extends BasicOrmService<ElectricConfig,Long>{
	Pageable<ElectricConfig> findByConditon(Pagination<ElectricConfig> pager,ElectricConfig condition);
	Pageable<ElectricConfig> findByConditonPower(Pagination<ElectricConfig> pager,ElectricConfig condition);
	List<ElectricConfig> findById(Long id);
	public void update(List<ElectricConfig> lst);
}
