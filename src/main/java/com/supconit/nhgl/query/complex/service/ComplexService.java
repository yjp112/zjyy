package com.supconit.nhgl.query.complex.service;

import java.util.List;

import com.supconit.nhgl.query.complex.entities.Complex;

import hc.base.domains.Pageable;
import hc.orm.BasicOrmService;



public interface ComplexService extends BasicOrmService<Complex,Long>{
	Pageable<Complex> findByConditon(Pageable<Complex> pager,Complex condition);
	Pageable<Complex> findByConditonPower(Pageable<Complex> pager,Complex condition);
	List<Complex> findById(Long id);
	public void update(List<Complex> lst);
	Pageable<Complex> findByWaterConditon(Pageable<Complex> pager,Complex condition);
	Pageable<Complex> findByEnergyConditon(Pageable<Complex> pager,Complex condition);
	Pageable<Complex> findByGasConditon(Pageable<Complex> pager,Complex condition);
	public double sumByCondition(Complex condition);
}
