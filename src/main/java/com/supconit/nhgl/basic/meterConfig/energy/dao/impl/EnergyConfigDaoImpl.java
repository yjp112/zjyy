package com.supconit.nhgl.basic.meterConfig.energy.dao.impl;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.basic.meterConfig.energy.dao.EnergyConfigDao;
import com.supconit.nhgl.basic.meterConfig.energy.entities.EnergyConfig;

import hc.base.domains.Pageable;
import hc.orm.AbstractBasicDaoImpl;

@Repository
public class EnergyConfigDaoImpl extends AbstractBasicDaoImpl<EnergyConfig,Long> implements EnergyConfigDao{

	private static final String NAMESPACE=EnergyConfig.class.getName();
	@Override
	public Pageable<EnergyConfig> findByConditionPower(
			Pageable<EnergyConfig> pager, EnergyConfig condition) {
		return findByPager(pager,"findByConditionPower","countByConditionPower",condition);
	}

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}

}
