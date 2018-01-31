package com.supconit.employee.timeCard.daos.impl;

import hc.base.domains.Pageable;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.employee.timeCard.daos.TimeCardDao;
import com.supconit.employee.timeCard.entities.TimeCard;

@Repository
public class TimeCardDaoImpl  extends AbstractBaseDao<TimeCard, Long> implements TimeCardDao{
	private static final String	NAMESPACE = TimeCard.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	
	@Override
	public Pageable<TimeCard> findByPage(Pageable<TimeCard> pager,
			TimeCard condition) {
		return findByPager(pager, "findByConditions", "countByConditions", condition);
	}

	
}
