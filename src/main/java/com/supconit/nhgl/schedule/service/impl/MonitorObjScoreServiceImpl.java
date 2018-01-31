/**
 * 
 */
package com.supconit.nhgl.schedule.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.nhgl.schedule.dao.MonitorObjScoreDao;
import com.supconit.nhgl.schedule.entites.MonitorObjScore;

import hc.base.domains.Pageable;

/**
 * @author 
 * @create 2014-06-16 18:01:23 
 * @since 
 * 
 */
@Service("dlhmc_ycynjc_jcdxpf_service")
public class MonitorObjScoreServiceImpl extends hc.orm.AbstractBasicOrmService<MonitorObjScore, Long> implements com.supconit.nhgl.schedule.service.MonitorObjScoreService {
	
	private transient static final Logger	logger	= LoggerFactory.getLogger(MonitorObjScoreServiceImpl.class);
	
	@Autowired
	private MonitorObjScoreDao		jcdxpfDao;
	
	@Override
	@Transactional(readOnly = true)
	public MonitorObjScore getById(Long id) {
		return this.jcdxpfDao.getById(id);
	}

	@Override
	@Transactional
	public void insert(MonitorObjScore entity) {
		this.jcdxpfDao.insert(entity);
	}

	@Override
	@Transactional
	public void update(MonitorObjScore entity) {
		this.jcdxpfDao.update(entity);
	}

	@Override
	@Transactional
	public void delete(MonitorObjScore entity) {
		this.jcdxpfDao.delete(entity);
	}
	
	/*
	 * @see com.supconit.nhgl.ycynjc.services.MonitorObjScoreService#find(hc.base.domains.Pageable, com.supconit.nhgl.ycynjc.entities.MonitorObjScore)
	 */
	@Override
	@Transactional(readOnly = true)
	public Pageable<MonitorObjScore> find(Pageable<MonitorObjScore> pager, MonitorObjScore condition) {
		
		return this.jcdxpfDao.findByPager(pager, condition);
	}
}
