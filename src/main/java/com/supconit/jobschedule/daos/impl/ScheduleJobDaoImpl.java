/**
 * 
 */
package com.supconit.jobschedule.daos.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.jobschedule.daos.ScheduleJobDao;
import com.supconit.jobschedule.entities.ScheduleJob;

import hc.base.domains.Pageable;

/**
 * @author 
 * @create 2014-06-05 11:21:48
 * @since 
 * 
 */
 
@Repository("jobschedule_scheduleLog_dao")
public class ScheduleJobDaoImpl extends hc.orm.AbstractBasicDaoImpl<ScheduleJob, Long> implements ScheduleJobDao {

	private static final String	NAMESPACE	= ScheduleJob.class.getName();

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	
	/*
	 * @see com.supconit.dlhmc.jobschedule.daos.ScheduleLogDao#findByPager(hc.base.domains.Pageable, com.supconit.dlhmc.jobschedule.entities.ScheduleLog)
	 */
	@Override
	public Pageable<ScheduleJob> findByPager(Pageable<ScheduleJob> pager, ScheduleJob condition) {
		return findByPager(pager, "selectPager", "countPager", condition);
	}

	@Override
	public List<ScheduleJob> selectByStatus(int status) {
		return selectList("selectByStatus", status);
	}
}
