/**
 * 
 */
package com.supconit.nhgl.schedule.dao.impl;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.supconit.common.utils.ListUtils;
import com.supconit.jobschedule.daos.impl.ScheduleLogDaoImpl;
import com.supconit.nhgl.schedule.entites.TaskCatagory;

import hc.base.domains.Pageable;

/**
 * @author 
 * @create 2014-06-17 00:48:22
 * @since 
 * 
 */
 
@Repository("dlhmc_ycynjc_TASKCATAGORY_dao")
public class TaskCategoryDaoImpl extends hc.orm.AbstractBasicDaoImpl<TaskCatagory, Long> implements com.supconit.nhgl.schedule.dao.TaskCategoryDao {

	private static final String	NAMESPACE	= TaskCatagory.class.getName();
	private transient static final Logger	log	= LoggerFactory.getLogger(ScheduleLogDaoImpl.class);

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	
	/*
	 * @see com.supconit.dlhmc.ycynjc.daos.TASKCATAGORYDao#findByPager(hc.base.domains.Pageable, com.supconit.dlhmc.ycynjc.entities.TaskCatagory)
	 */
	@Override
	public Pageable<TaskCatagory> findByPager(Pageable<TaskCatagory> pager, TaskCatagory condition) {
		return findByPager(pager, "selectPager", "countPager", condition);
	}
	
	@Override
	public Pageable<TaskCatagory> findTaskByPager(Pageable<TaskCatagory> pager, TaskCatagory condition) {
		return findByPager(pager, "selectTaskPager", "countTaskPager", condition);
	}
	
	@Override
	public List<TaskCatagory> getTaskCategory() {
		return selectList("getTaskCategory");
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
	
}
