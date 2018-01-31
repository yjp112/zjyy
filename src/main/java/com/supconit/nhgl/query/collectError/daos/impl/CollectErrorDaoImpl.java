package com.supconit.nhgl.query.collectError.daos.impl;

import hc.base.domains.Pageable;
import hc.orm.AbstractBasicDaoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.query.collectError.daos.CollectErrorDao;
import com.supconit.nhgl.query.collectError.entities.CollectError;
@Repository
public class CollectErrorDaoImpl  extends AbstractBasicDaoImpl<CollectError,Long> implements CollectErrorDao{

	private static final String	NAMESPACE=CollectError.class.getName();

	@Override
	public Pageable<CollectError> findByCondition(Pageable<CollectError> pager,
			CollectError condition) {
		return findByPager(pager,"findByCondition","countByCondition",condition);
	}
	@Override
	public Pageable<CollectError> findByConditionPower(Pageable<CollectError> pager,
			CollectError condition) {
		return findByPager(pager,"findByConditionPower","countByConditionPower",condition);
	}
	@Override
	public List<CollectError> findById(Long id) {
		return selectList("findById", id);
	}

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public CollectError findByBitNo(String bitNo) {
		return selectOne("findByBitNo", bitNo);
	}
	@Override
	public Pageable<CollectError> findByWaterCondition(Pageable<CollectError> pager,
			CollectError condition) {
		return findByPager(pager,"findByWaterCondition","countWaterByCondition",condition);
	}
	@Override
	public Pageable<CollectError> findByEnergyCondition(Pageable<CollectError> pager,
			CollectError condition) {
		return findByPager(pager,"findByEnergyCondition","countEnergyByCondition",condition);
	}
	@Override
	public Pageable<CollectError> findByGasCondition(Pageable<CollectError> pager,
			CollectError condition) {
		return findByPager(pager,"findByGasCondition","countGasByCondition",condition);
	}
}
