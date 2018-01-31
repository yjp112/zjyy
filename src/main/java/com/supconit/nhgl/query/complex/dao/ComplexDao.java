package com.supconit.nhgl.query.complex.dao;

import java.util.List;

import com.supconit.nhgl.query.complex.entities.Complex;

import hc.base.domains.Pageable;
import hc.orm.BasicDao;




public interface ComplexDao extends BasicDao<Complex, Long> {

	public Pageable<Complex> findByCondition(Pageable<Complex> pager, Complex condition);
	public Pageable<Complex> findByConditionPower(Pageable<Complex> pager, Complex condition);
	public List<Complex> findById(Long id);
	
	public Complex findByBitNo(String bitNo);
	public Pageable<Complex> findByWaterCondition(Pageable<Complex> pager, Complex condition);
	public Pageable<Complex> findByEnergyCondition(Pageable<Complex> pager, Complex condition);
	public Pageable<Complex> findByGasCondition(Pageable<Complex> pager, Complex condition);
	
	public double sumByCondition(Complex condition);
}
