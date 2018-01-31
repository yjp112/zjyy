/**
 * 
 */
package com.supconit.nhgl.schedule.dao;

import java.util.List;

import com.supconit.nhgl.schedule.entites.TaskExecutionPlan;

import hc.base.domains.Pageable;

/**
 * @author 
 * @create 2014-06-16 18:01:44
 * @since 
 * 
 */
 
public interface TaskExecutionPlanDao extends hc.orm.BasicDao<TaskExecutionPlan, Long> {

	/**
	 * @param pager
	 * @param condition
	 * @return
	 */
	Pageable<TaskExecutionPlan> findByPager(Pageable<TaskExecutionPlan> pager, TaskExecutionPlan condition);
	
	TaskExecutionPlan selectByTaskCode(String taskCode);
	public List<TaskExecutionPlan> findBetweenTimes(TaskExecutionPlan taskExecutionPlan); 
	int deleteByTaskCode(String taskCode);
}
