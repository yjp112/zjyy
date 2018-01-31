/**
 * 
 */
package com.supconit.jobschedule.daos.impl;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.supconit.common.utils.ListUtils;
import com.supconit.jobschedule.daos.ScheduleLogDao;
import com.supconit.jobschedule.entities.ScheduleLog;

import hc.base.domains.Pageable;

/**
 * @author 
 * @create 2014-06-05 11:26:12
 * @since 
 * 
 */
 
@Repository("jobschedule_scheduleJob_dao")
public class ScheduleLogDaoImpl extends hc.orm.AbstractBasicDaoImpl<ScheduleLog, Long> implements ScheduleLogDao {

	private static final String	NAMESPACE	= ScheduleLog.class.getName();
	private transient static final Logger	log	= LoggerFactory.getLogger(ScheduleLogDaoImpl.class);

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	
	/*
	 * @see com.supconit.dlhmc.jobschedule.daos.ScheduleJobDao#findByPager(hc.base.domains.Pageable, com.supconit.dlhmc.jobschedule.entities.ScheduleJob)
	 */
	@Override
	public Pageable<ScheduleLog> findByPager(Pageable<ScheduleLog> pager, ScheduleLog condition) {
		return findByPager(pager, "selectPager", "countPager", condition);
	}

	@Override
	public int deleteByIds(Long[] ids) {
		int maxSize=1000;
		int count=0;
		for (List<Long> eachIdList : ListUtils.partition(Arrays.asList(ids), maxSize)) {
			count+=update("deleteByIds", eachIdList);
		}
		if(log.isInfoEnabled()){
			log.info("要求删除["+ids.length+"]条记录，实际删除["+count+"]条记录。");
		}
		return count;
	}

	@Override
	public int updateErrorStatus() {
		return update("updateErrorStatus");
	}
}
