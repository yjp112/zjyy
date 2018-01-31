package com.supconit.nhgl.basic.meterConfig.electic.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.basic.meterConfig.electic.dao.ElectricConfigDao;
import com.supconit.nhgl.basic.meterConfig.electic.entities.ElectricConfig;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;
import hc.orm.AbstractBasicDaoImpl;
@Repository
public class ElectricConfigDaoImpl  extends AbstractBasicDaoImpl<ElectricConfig,Long> implements ElectricConfigDao{

	private static final String	NAMESPACE=ElectricConfig.class.getName();

	@Override
	public Pageable<ElectricConfig> findByCondition(Pagination<ElectricConfig> pager,
			ElectricConfig condition) {
		return findByPager(pager,"findByCondition","countByCondition",condition);
	}
	@Override
	public Pageable<ElectricConfig> findByConditionPower(Pagination<ElectricConfig> pager,
			ElectricConfig condition) {
		return findByPager(pager,"findByConditionPower","countByConditionPower",condition);
	}
	@Override
	public List<ElectricConfig> findById(Long id) {
		return selectList("findById", id);
	}

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public ElectricConfig findByBitNo(String bitNo) {
		return selectOne("findByBitNo", bitNo);
	}
}
