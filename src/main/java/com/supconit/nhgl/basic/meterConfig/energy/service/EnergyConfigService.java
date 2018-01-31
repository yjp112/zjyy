package com.supconit.nhgl.basic.meterConfig.energy.service;

import java.util.List;

import com.supconit.nhgl.basic.meterConfig.energy.entities.EnergyConfig;

import hc.base.domains.Pageable;
import hc.orm.BasicOrmService;

public interface EnergyConfigService extends BasicOrmService<EnergyConfig,Long>{
	Pageable<EnergyConfig> findByConditonPower(Pageable<EnergyConfig> pager,EnergyConfig condition);
	public void update(List<EnergyConfig> lst);
}
