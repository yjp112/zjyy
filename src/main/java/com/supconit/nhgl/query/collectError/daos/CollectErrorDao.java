package com.supconit.nhgl.query.collectError.daos;

import java.util.List;

import com.supconit.nhgl.query.collectError.entities.CollectError;

import hc.base.domains.Pageable;
import hc.orm.BasicDao;




public interface CollectErrorDao extends BasicDao<CollectError, Long> {

	public Pageable<CollectError> findByCondition(Pageable<CollectError> pager, CollectError condition);
	public Pageable<CollectError> findByConditionPower(Pageable<CollectError> pager, CollectError condition);
	public List<CollectError> findById(Long id);
	
	public CollectError findByBitNo(String bitNo);
	public Pageable<CollectError> findByWaterCondition(Pageable<CollectError> pager, CollectError condition);
	public Pageable<CollectError> findByEnergyCondition(Pageable<CollectError> pager, CollectError condition);
	public Pageable<CollectError> findByGasCondition(Pageable<CollectError> pager, CollectError condition);
}
