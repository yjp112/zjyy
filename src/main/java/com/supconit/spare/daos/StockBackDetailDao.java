package com.supconit.spare.daos;

import java.util.List;

import hc.base.domains.Pageable;

import com.supconit.common.daos.BaseDao;
import com.supconit.spare.entities.StockBackDetail;

public interface StockBackDetailDao extends BaseDao<StockBackDetail, Long>{
	/**
	 * 分页
	 */
	Pageable<StockBackDetail> findByCondition(Pageable<StockBackDetail> pager,StockBackDetail condition);
	/**
	 * 新增
	 */
	void insert(List<StockBackDetail> list);
	/**
	 * 删除
	 */
	void deleteByStockBackId(Long id);
}
