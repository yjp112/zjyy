package com.supconit.nhgl.query.complex.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.query.complex.dao.ComplexDao;
import com.supconit.nhgl.query.complex.entities.Complex;

import hc.base.domains.Pageable;
import hc.orm.AbstractBasicDaoImpl;
@Repository
public class ComplexDaoImpl  extends AbstractBasicDaoImpl<Complex,Long> implements ComplexDao{

	private static final String	NAMESPACE=Complex.class.getName();

	@Override
	public Pageable<Complex> findByCondition(Pageable<Complex> pager,
			Complex condition) {
		return findByPager(pager,"findByCondition","countByCondition",condition);
	}
	@Override
	public Pageable<Complex> findByConditionPower(Pageable<Complex> pager,
			Complex condition) {
		return findByPager(pager,"findByConditionPower","countByConditionPower",condition);
	}
	@Override
	public List<Complex> findById(Long id) {
		return selectList("findById", id);
	}

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public Complex findByBitNo(String bitNo) {
		return selectOne("findByBitNo", bitNo);
	}
	
	@Override
	public double sumByCondition(Complex condition) {
		return selectOne("sumByCondition", condition);
	}
	
	@Override
	public Pageable<Complex> findByWaterCondition(Pageable<Complex> pager,
			Complex condition) {
		return findByPager(pager,"findByWaterCondition","countWaterByCondition",condition);
	}
	@Override
	public Pageable<Complex> findByEnergyCondition(Pageable<Complex> pager,
			Complex condition) {
		return findByPager(pager,"findByEnergyCondition","countEnergyByCondition",condition);
	}
	@Override
	public Pageable<Complex> findByGasCondition(Pageable<Complex> pager,
			Complex condition) {
		return findByPager(pager,"findByGasCondition","countGasByCondition",condition);
	}
}
