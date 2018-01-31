package com.supconit.nhgl.basic.meterConfig.energy.dao;

import com.supconit.nhgl.basic.meterConfig.energy.entities.EnergyConfig;

import hc.base.domains.Pageable;
import hc.orm.BasicDao;

public interface EnergyConfigDao extends BasicDao<EnergyConfig,Long>{
	
	public Pageable<EnergyConfig>findByConditionPower (Pageable<EnergyConfig> pager, EnergyConfig condition);

}
