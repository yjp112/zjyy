/**
 * 
 */
package com.supconit.nhgl.alarm.energy.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.nhgl.alarm.energy.dao.EnergyAlarmDao;
import com.supconit.nhgl.alarm.energy.entities.EnergyAlarm;
import com.supconit.nhgl.alarm.energy.service.EnergyAlarmService;

import hc.base.domains.Pageable;
import hc.orm.AbstractBasicOrmService;


/**
 * @author 
 * @create 2014-06-16 18:01:16 
 * @since 
 * 
 */
@Service("dlhmc_ycynjc_ynbj_service")
public class EnergyAlarmServiceImpl extends AbstractBasicOrmService<EnergyAlarm, Long> implements EnergyAlarmService {
	
	private transient static final Logger	logger	= LoggerFactory.getLogger(EnergyAlarmServiceImpl.class);
	
	@Autowired
	private EnergyAlarmDao		ynbjDao;
	
	@Override
	@Transactional(readOnly = true)
	public EnergyAlarm getById(Long id) {
		return this.ynbjDao.getById(id);
	}

	@Override
	@Transactional
	public void insert(EnergyAlarm entity) {
		this.ynbjDao.insert(entity);
	}

	@Override
	@Transactional
	public void update(EnergyAlarm entity) {
		this.ynbjDao.update(entity);
	}

	@Override
	@Transactional
	public void delete(EnergyAlarm entity) {
		this.ynbjDao.delete(entity);
	}
	
	/*
	 * @see com.supconit.nhgl.ycynjc.services.EnergyAlarmService#find(hc.base.domains.Pageable, com.supconit.nhgl.ycynjc.entities.EnergyAlarm)
	 */
	@Override
	@Transactional(readOnly = true)
	public Pageable<EnergyAlarm> find(Pageable<EnergyAlarm> pager, EnergyAlarm condition) {
		
		return this.ynbjDao.findByPager(pager, condition);
	}

	@Override
	public Pageable<EnergyAlarm> findByCondition(Pageable<EnergyAlarm> pager,
			EnergyAlarm condition) {
		return this.ynbjDao.findByCondition(pager, condition);
	}

	@Override
	public EnergyAlarm findById(Long id) {
		return this.ynbjDao.findById(id);
	}
}
