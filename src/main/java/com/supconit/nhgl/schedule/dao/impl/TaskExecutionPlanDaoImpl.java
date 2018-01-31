/**
 * 
 */
package com.supconit.nhgl.schedule.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.schedule.entites.TaskExecutionPlan;

import hc.base.domains.Pageable;

/**
 * @author 
 * @create 2014-06-16 18:01:44
 * @since 
 * 
 */
 
@Repository("dlhmc_ycynjc_rwzxjh_dao")
public class TaskExecutionPlanDaoImpl extends hc.orm.AbstractBasicDaoImpl<TaskExecutionPlan, Long> implements com.supconit.nhgl.schedule.dao.TaskExecutionPlanDao {

	private static final String	NAMESPACE	= TaskExecutionPlan.class.getName();

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	
	/*
	 * @see com.supconit.nhgl.ycynjc.dao.RwzxjhDao#findByPager(hc.base.domains.Pageable, com.supconit.nhgl.ycynjc.entities.TaskExecutionPlan)
	 */
	@Override
	public Pageable<TaskExecutionPlan> findByPager(Pageable<TaskExecutionPlan> pager, TaskExecutionPlan condition) {
		return findByPager(pager, "selectPager", "countPager", condition);
	}

	@Override
	public TaskExecutionPlan selectByTaskCode(String taskCode) {
		return selectOne("selectByTaskCode", taskCode);
	}
	
	@Override
	public List<TaskExecutionPlan> findBetweenTimes(TaskExecutionPlan taskExecutionPlan) {
		return selectList("findBetweenTimes",taskExecutionPlan);
	}

	@Override
	public int deleteByTaskCode(String taskCode) {
		return delete("deleteByTaskCode", taskCode);
	}
}
