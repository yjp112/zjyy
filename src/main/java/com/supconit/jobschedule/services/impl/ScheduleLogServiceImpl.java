/**
 * 
 */
package com.supconit.jobschedule.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.jobschedule.daos.ScheduleLogDao;
import com.supconit.jobschedule.entities.ScheduleLog;
import com.supconit.jobschedule.services.ScheduleLogService;

import hc.base.domains.Pageable;

/**
 * @author 
 * @create 2014-06-05 11:21:48 
 * @since 
 * 
 */
@Service("Logschedule_scheduleLog_service")
public class ScheduleLogServiceImpl extends hc.orm.AbstractBasicOrmService<ScheduleLog, Long> implements ScheduleLogService {
	
	private transient static final Logger	log	= LoggerFactory.getLogger(ScheduleLogServiceImpl.class);
	
	@Autowired
	private ScheduleLogDao		scheduleLogDao;
	
	@Override
	@Transactional(readOnly = true)
	public ScheduleLog getById(Long id) {
		return this.scheduleLogDao.getById(id);
	}

	@Override
	@Transactional
	public void insert(ScheduleLog entity) {
		this.scheduleLogDao.insert(entity);
	}

	@Override
	@Transactional
	public void update(ScheduleLog entity) {
		this.scheduleLogDao.update(entity);
	}

	@Override
	@Transactional
	public void delete(ScheduleLog entity) {
		this.scheduleLogDao.delete(entity);
	}
	
	/*
	 * @see com.supconit.dlhmc.Logschedule.services.ScheduleLogService#find(hc.base.domains.Pageable, com.supconit.dlhmc.Logschedule.entities.ScheduleLog)
	 */
	@Override
	@Transactional(readOnly = true)
	public Pageable<ScheduleLog> find(Pageable<ScheduleLog> pager, ScheduleLog condition) {
		
		return this.scheduleLogDao.findByPager(pager, condition);
	}

	@Override
	public void insertSelective(ScheduleLog scheduleLog) {
		scheduleLogDao.insert(scheduleLog);
	}

	@Override
	public int deleteByIds(Long[] ids) {
		return scheduleLogDao.deleteByIds(ids);
	}

	@Override
	public int updateErrorStatus() {
		return scheduleLogDao.updateErrorStatus();
	}
}
