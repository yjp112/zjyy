package com.supconit.nhgl.basic.meterConfig.gas.dao;

import com.supconit.nhgl.basic.meterConfig.gas.entities.GasConfig;

import hc.base.domains.Pageable;
import hc.orm.BasicDao;

public interface GasConfigDao extends BasicDao<GasConfig,Long>{
	
	public Pageable<GasConfig>findByConditionPower (Pageable<GasConfig> pager, GasConfig condition);

}
