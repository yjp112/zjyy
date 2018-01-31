/**
 * 
 */
package com.supconit.nhgl.schedule.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.schedule.entites.MonitorTask;

import hc.base.domains.Pageable;

/**
 * @author 
 * @create 2014-06-16 18:01:51
 * @since 
 * 
 */
 
@Repository("dlhmc_ycynjc_jcrw_dao")
public class MonitorTaskDaoImpl extends hc.orm.AbstractBasicDaoImpl<MonitorTask, Long> implements com.supconit.nhgl.schedule.dao.MonitorTaskDao {

	private static final String	NAMESPACE	= MonitorTask.class.getName();

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	
	/*
	 * @see com.supconit.nhgl.ycynjc.dao.JcrwDao#findByPager(hc.base.domains.Pageable, com.supconit.nhgl.ycynjc.entities.MonitorTask)
	 */
	@Override
	public Pageable<MonitorTask> findByPager(Pageable<MonitorTask> pager, MonitorTask condition) {
		return findByPager(pager, "selectPager", "countPager", condition);
	}

	@Override
	public MonitorTask selectByTaskCode(String taskCode) {
		return selectOne("selectByTaskCode", taskCode);
	}

	@Override
	public Pageable<MonitorTask> findTaskList(Pageable<MonitorTask> pager,
			MonitorTask condition) {
		return findByPager(pager, "selectTaskPager" , "countTaskPager", condition);
	}

	@Override
	public MonitorTask findById(Integer id) {
		return selectOne("findById", id);
	}
	
	@Override
	public List<MonitorTask> getByTaskType(Long taskType) {
		return selectList("getByTaskType", taskType);
	}
}
