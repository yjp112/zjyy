package com.supconit.spare.daos.impl;

import java.util.List;

import hc.base.domains.Pageable;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.spare.daos.StockBackDetailDao;
import com.supconit.spare.entities.StockBackDetail;

@Repository
public class StockBackDetailDaoImpl extends AbstractBaseDao<StockBackDetail, Long> implements StockBackDetailDao{
	private static final String	NAMESPACE = StockBackDetail.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	
	@Override
	public Pageable<StockBackDetail> findByCondition(Pageable<StockBackDetail> pager,
			StockBackDetail condition) {
		return findByPager(pager, "findByConditions", "countByConditions", condition);
	}

	@Override
	public void insert(List<StockBackDetail> list) {
		insert("insert",list);
	}

	@Override
	public void deleteByStockBackId(Long id) {
		delete("deleteByStockBackId",id);
	}

}
