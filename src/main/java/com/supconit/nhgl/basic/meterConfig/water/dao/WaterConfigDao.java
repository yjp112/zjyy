package com.supconit.nhgl.basic.meterConfig.water.dao;

import com.supconit.nhgl.basic.meterConfig.water.entities.WaterConfig;

import hc.base.domains.Pageable;
import hc.orm.BasicDao;

public interface WaterConfigDao extends BasicDao<WaterConfig,Long>{
	public Pageable<WaterConfig>findByConditionPower (Pageable<WaterConfig> pager, WaterConfig condition);
}
