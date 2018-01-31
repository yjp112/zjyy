package com.supconit.nhgl.basic.meterConfig.gas.service;

import java.util.List;

import com.supconit.nhgl.basic.meterConfig.gas.entities.GasConfig;

import hc.base.domains.Pageable;
import hc.orm.BasicOrmService;

public interface GasConfigService extends BasicOrmService<GasConfig,Long>{
	Pageable<GasConfig> findByConditonPower(Pageable<GasConfig> pager,GasConfig condition);
	public void update(List<GasConfig> lst);
}
