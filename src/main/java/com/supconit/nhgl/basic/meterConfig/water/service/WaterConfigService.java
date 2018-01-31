package com.supconit.nhgl.basic.meterConfig.water.service;

import java.util.List;

import com.supconit.nhgl.basic.meterConfig.water.entities.WaterConfig;

import hc.base.domains.Pageable;
import hc.orm.BasicOrmService;


public interface WaterConfigService extends BasicOrmService<WaterConfig,Long>{
	Pageable<WaterConfig> findByConditonPower(Pageable<WaterConfig> pager,WaterConfig condition);
	public void update(List<WaterConfig> lst);
}
