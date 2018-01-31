package com.supconit.employee.lunchapply.daos.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.employee.lunchapply.daos.LunchApplyDao;
import com.supconit.employee.lunchapply.entities.LunchApply;

import hc.base.domains.Pageable;

@Repository
public class LunchApplyDaoImpl extends AbstractBaseDao<LunchApply, Long> implements LunchApplyDao{
	private static final String	NAMESPACE = LunchApply.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	
	@Override
	public Pageable<LunchApply> findByPage(Pageable<LunchApply> pager,
			LunchApply condition) {
		return findByPager(pager, "findByConditions", "countByConditions", condition);
	}

	@Override
	public int countLunchNumOfCurrentDay(Date currentDay) {
		Map param = new HashMap(1);
		param.put("currentDay", currentDay);
		return getSqlSession().selectOne("countLunchNumOfCurrentDay",param);
	}

}
