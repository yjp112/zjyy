package com.supconit.hikvision.daos.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.hikvision.daos.AccessCountDao;
import com.supconit.hikvision.entities.AccessCount;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;
@Repository
public class AccessCountDaoImpl extends AbstractBaseDao<AccessCount, Long> implements AccessCountDao  {

	private static final String	NAMESPACE = AccessCount.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	@Override
	public Pageable<AccessCount> findByPage(Pagination<AccessCount> pager, AccessCount condition) {
        return findByPager(pager, "findByConditions", "countByConditions", condition);
	}
	
	@Override
	public List<AccessCount> findByConditions(AccessCount condition) {
		return selectList("findByConditions", condition);
	}
	@Override
	public List<AccessCount> findAllByConditions(AccessCount condition) {
		return selectList("findAllByConditions", condition);
	}

	/** 手机端使用 **/
	@Override
	public List<AccessCount> searchMonthAttendances(AccessCount condition) {
		return selectList("searchMonthAttendances", condition);
	}

	@Override
	public List<AccessCount> searchUnusualMonthAttendances(AccessCount condition) {
		return selectList("searchUnusualMonthAttendances", condition);
	}

	@Override
	public AccessCount countMonthAttendances(AccessCount condition) {
		return selectOne("countMonthAttendances",condition);
	}

	@Override
	public AccessCount searchAttendance(AccessCount condition) {
		return selectOne("searchAttendance",condition);
	}
}
