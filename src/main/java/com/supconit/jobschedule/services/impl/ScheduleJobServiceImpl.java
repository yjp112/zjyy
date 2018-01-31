/**
 * 
 */
package com.supconit.jobschedule.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.jobschedule.daos.ScheduleJobDao;
import com.supconit.jobschedule.entities.ScheduleJob;
import com.supconit.jobschedule.services.ScheduleJobService;
import com.supconit.jobschedule.services.SchedulerManagerService;

import hc.base.domains.Pageable;

/**
 * @author 
 * @create 2014-06-05 11:26:12 
 * @since 
 * 
 */
@Service("jobschedule_scheduleJob_service")
public class ScheduleJobServiceImpl extends hc.orm.AbstractBasicOrmService<ScheduleJob, Long> implements ScheduleJobService {
	
	private transient static final Logger	log	= LoggerFactory.getLogger(ScheduleJobServiceImpl.class);
	
	@Autowired
	private ScheduleJobDao		scheduleJobDao;
	
	@Override
	@Transactional(readOnly = true)
	public ScheduleJob getById(Long id) {
		return this.scheduleJobDao.getById(id);
	}

	@Override
	@Transactional
	public void insert(ScheduleJob entity) {
		this.scheduleJobDao.insert(entity);
	}

	@Override
	@Transactional
	public void update(ScheduleJob entity) {
		this.scheduleJobDao.update(entity);
	}

	@Override
	@Transactional
	public void delete(ScheduleJob entity) {
		this.scheduleJobDao.delete(entity);
	}
	
	/*
	 * @see com.supconit.dlhmc.jobschedule.services.ScheduleJobService#find(hc.base.domains.Pageable, com.supconit.dlhmc.jobschedule.entities.ScheduleJob)
	 */
	@Override
	@Transactional(readOnly = true)
	public Pageable<ScheduleJob> find(Pageable<ScheduleJob> pager, ScheduleJob condition) {
		
		return this.scheduleJobDao.findByPager(pager, condition);
	}

	@Override
	public List<ScheduleJob> findAllEnabled() {
		return scheduleJobDao.selectByStatus(SchedulerManagerService.STATUS_READY);
	}

	@Override
	public void updateByPrimaryKeySelective(ScheduleJob scheduleJob) {
		scheduleJobDao.update(scheduleJob);
		
	}
}
