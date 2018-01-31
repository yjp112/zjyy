package com.supconit.spare.daos.impl;

import hc.base.domains.Pageable;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.spare.daos.StockBackDao;
import com.supconit.spare.entities.StockBack;

@Repository
public class StockBackDaoImpl extends AbstractBaseDao<StockBack, Long> implements StockBackDao{
	private static final String	NAMESPACE = StockBack.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	
	@Override
	public Pageable<StockBack> findByCondition(Pageable<StockBack> pager,
			StockBack condition) {
		return findByPager(pager, "findByConditions", "countByConditions", condition);
	}

}
