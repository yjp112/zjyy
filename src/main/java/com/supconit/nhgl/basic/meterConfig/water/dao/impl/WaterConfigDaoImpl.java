package com.supconit.nhgl.basic.meterConfig.water.dao.impl;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.basic.meterConfig.water.dao.WaterConfigDao;
import com.supconit.nhgl.basic.meterConfig.water.entities.WaterConfig;

import hc.base.domains.Pageable;
import hc.orm.AbstractBasicDaoImpl;
@Repository
public class WaterConfigDaoImpl extends AbstractBasicDaoImpl<WaterConfig,Long> implements WaterConfigDao{
	private static final String	NAMESPACE=WaterConfig.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	@Override
	public Pageable<WaterConfig> findByConditionPower(Pageable<WaterConfig> pager,
			WaterConfig condition) {
		return findByPager(pager,"findByConditionPower","countByConditionPower",condition);
	}
}
