package com.supconit.spare.services;

import hc.base.domains.Pageable;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.spare.entities.StockBack;

public interface StockBackService extends BaseBusinessService<StockBack, Long>{
	/**
	 * 分页查询
	 */
	Pageable<StockBack> findByCondition(Pageable<StockBack> pager,StockBack condition);
}
