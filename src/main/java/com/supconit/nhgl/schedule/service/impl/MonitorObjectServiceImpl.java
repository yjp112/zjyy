/**
 * 
 */
package com.supconit.nhgl.schedule.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.nhgl.schedule.dao.MonitorObjectDao;
import com.supconit.nhgl.schedule.entites.MonitorObject;

import hc.base.domains.Pageable;

/**
 * @author 
 * @create 2014-06-16 18:01:30 
 * @since 
 * 
 */
@Service("dlhmc_ycynjc_jcdx_service")
public class MonitorObjectServiceImpl extends hc.orm.AbstractBasicOrmService<MonitorObject, Long> implements com.supconit.nhgl.schedule.service.MonitorObjectService {
	
	private transient static final Logger	logger	= LoggerFactory.getLogger(MonitorObjectServiceImpl.class);
	
	@Autowired
	private MonitorObjectDao		jcdxDao;
	
	@Override
	@Transactional(readOnly = true)
	public MonitorObject getById(Long id) {
		return this.jcdxDao.getById(id);
	}

	@Override
	@Transactional
	public void insert(MonitorObject entity) {
		this.jcdxDao.insert(entity);
	}

	@Override
	@Transactional
	public void update(MonitorObject entity) {
		this.jcdxDao.update(entity);
	}

	@Override
	@Transactional
	public void delete(MonitorObject entity) {
		this.jcdxDao.delete(entity);
	}
	
	/*
	 * @see com.supconit.nhgl.ycynjc.services.MonitorObjectService#find(hc.base.domains.Pageable, com.supconit.nhgl.ycynjc.entities.MonitorObject)
	 */
	@Override
	@Transactional(readOnly = true)
	public Pageable<MonitorObject> find(Pageable<MonitorObject> pager, MonitorObject condition) {
		
		return this.jcdxDao.findByPager(pager, condition);
	}

	@Override
	public List<MonitorObject> findMonitorObject(MonitorObject condition) {
		return jcdxDao.findMonitorObjectFromAreaConfigAndDeptConfig(condition);
		
	}

	@Override
	public int deleteByTaskCode(String taskCode) {
		return jcdxDao.deleteByTaskCode(taskCode);
	}

	@Override
	public List<MonitorObject> selectByTaskCode(String taskCode) {
		return jcdxDao.selectByTaskCode(taskCode);
	}

	@Override
	public List<MonitorObject> findMonitorObjectByTaskCode(String taskCode){
		return jcdxDao.findMonitorObjectByTaskCode(taskCode);
	}
}
