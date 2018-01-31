package com.supconit.nhgl.basic.meterConfig.gas.dao.impl;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.basic.meterConfig.gas.dao.GasConfigDao;
import com.supconit.nhgl.basic.meterConfig.gas.entities.GasConfig;

import hc.base.domains.Pageable;
import hc.orm.AbstractBasicDaoImpl;

@Repository
public class GasConfigDaoImpl extends AbstractBasicDaoImpl<GasConfig,Long> implements GasConfigDao{

	private static final String NAMESPACE=GasConfig.class.getName();
	@Override
	public Pageable<GasConfig> findByConditionPower(
			Pageable<GasConfig> pager, GasConfig condition) {
		return findByPager(pager,"findByConditionPower","countByConditionPower",condition);
	}

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}

}
