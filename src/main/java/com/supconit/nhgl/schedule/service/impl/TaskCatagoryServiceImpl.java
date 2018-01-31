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

import com.supconit.nhgl.schedule.dao.TaskCategoryDao;
import com.supconit.nhgl.schedule.entites.TaskCatagory;

import hc.base.domains.Pageable;

/**
 * @author 
 * @create 2014-06-17 00:48:22 
 * @since 
 * 
 */
@Service("dlhmc_ycynjc_TASKCATAGORY_service")
public class TaskCatagoryServiceImpl extends hc.orm.AbstractBasicOrmService<TaskCatagory, Long> implements com.supconit.nhgl.schedule.service.TaskCatagoryService {
	
	private transient static final Logger	logger	= LoggerFactory.getLogger(TaskCatagoryServiceImpl.class);
	
	@Autowired
	private TaskCategoryDao		TaskCategoryDao;
	
	@Override
	@Transactional(readOnly = true)
	public TaskCatagory getById(Long id) {
		return this.TaskCategoryDao.getById(id);
	}
	
	@Override
	@Transactional
	public void insert(TaskCatagory entity) {
		this.TaskCategoryDao.insert(entity);
	}

	@Override
	@Transactional
	public void update(TaskCatagory entity) {
		this.TaskCategoryDao.update(entity);
	}

	@Override
	@Transactional
	public void delete(TaskCatagory entity) {
		this.TaskCategoryDao.delete(entity);
	}
	
	@Override
	public int deleteByIds(Long[] ids) {
		return TaskCategoryDao.deleteByIds(ids);
	}
	
	/*
	 * @see com.supconit.dlhmc.ycynjc.services.TaskCatagoryService#find(hc.base.domains.Pageable, com.supconit.dlhmc.ycynjc.entities.TaskCatagory)
	 */
	@Override
	@Transactional(readOnly = true)
	public Pageable<TaskCatagory> find(Pageable<TaskCatagory> pager, TaskCatagory condition) {
		
		return this.TaskCategoryDao.findByPager(pager, condition);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Pageable<TaskCatagory> findTaskByPager(Pageable<TaskCatagory> pager, TaskCatagory condition) {
		
		return this.TaskCategoryDao.findTaskByPager(pager, condition);
	}
	
	@Override
	public List<TaskCatagory> getTaskCategory() {
		return this.TaskCategoryDao.getTaskCategory();
	}
}
