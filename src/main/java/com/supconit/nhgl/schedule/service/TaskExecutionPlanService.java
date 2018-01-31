/**
 * 
 */
package com.supconit.nhgl.schedule.service;

import java.util.List;

import com.supconit.nhgl.schedule.entites.TaskCatagory;
import com.supconit.nhgl.schedule.entites.TaskExecutionPlan;

import hc.base.domains.Pageable;

/**
 * @author 
 * @create 2014-06-16 18:01:44 
 * @since 
 * 
 */
public interface TaskExecutionPlanService extends hc.orm.BasicOrmService<TaskExecutionPlan, Long> {

	/**
	 * 分页按条件查询。
	 * @param pager
	 * @param condition
	 * @return
	 */
	Pageable<TaskExecutionPlan> find(Pageable<TaskExecutionPlan> pager, TaskExecutionPlan condition);
	TaskExecutionPlan selectByTaskCode(String taskCode);
	int deleteByTaskCode(String taskCode);
	List<TaskExecutionPlan> findBetweenTimes(TaskExecutionPlan taskExecutionPlan);
	String buildTree(List<TaskCatagory> tclist);
//	void del(Integer id) throws Exception;
//	void saveInsert(MonitorTask monitorTask) throws Exception;
//	void saveUpdate(MonitorTask monitorTask) throws Exception;
}
