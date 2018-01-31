package com.supconit.nhgl.query.collectError.services;

import java.util.List;

import com.supconit.nhgl.query.collectError.entities.CollectError;

import hc.base.domains.Pageable;
import hc.orm.BasicOrmService;



public interface CollectErrorService extends BasicOrmService<CollectError,Long>{
	Pageable<CollectError> findByConditon(Pageable<CollectError> pager,CollectError condition);
	Pageable<CollectError> findByConditonPower(Pageable<CollectError> pager,CollectError condition);
	List<CollectError> findById(Long id);
	public void update(List<CollectError> lst);
	Pageable<CollectError> findByWaterConditon(Pageable<CollectError> pager,CollectError condition);
	Pageable<CollectError> findByEnergyConditon(Pageable<CollectError> pager,CollectError condition);
	Pageable<CollectError> findByGasConditon(Pageable<CollectError> pager,CollectError condition);
}
