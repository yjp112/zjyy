package com.supconit.spare.daos;

import hc.base.domains.Pageable;

import com.supconit.common.daos.BaseDao;
import com.supconit.spare.entities.StockBack;

public interface StockBackDao extends BaseDao<StockBack, Long>{
	/**
	 * 分页
	 */
	Pageable<StockBack> findByCondition(Pageable<StockBack> pager,StockBack condition);
}
