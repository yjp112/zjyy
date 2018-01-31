/**
 * 
 */
package com.supconit.nhgl.schedule.dao.impl;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.schedule.entites.MonitorObjScore;

import hc.base.domains.Pageable;

/**
 * @author 
 * @create 2014-06-16 18:01:23
 * @since 
 * 
 */
 
@Repository("dlhmc_ycynjc_jcdxpf_dao")
public class MonitorObjScoreDaoImpl extends hc.orm.AbstractBasicDaoImpl<MonitorObjScore, Long> implements com.supconit.nhgl.schedule.dao.MonitorObjScoreDao {

	private static final String	NAMESPACE	= MonitorObjScore.class.getName();

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	
	/*
	 * @see com.supconit.nhgl.ycynjc.dao.JcdxpfDao#findByPager(hc.base.domains.Pageable, com.supconit.nhgl.ycynjc.entities.MonitorObjScore)
	 */
	@Override
	public Pageable<MonitorObjScore> findByPager(Pageable<MonitorObjScore> pager, MonitorObjScore condition) {
		return findByPager(pager, "selectPager", "countPager", condition);
	}
}
