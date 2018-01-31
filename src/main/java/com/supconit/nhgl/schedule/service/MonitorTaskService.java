/**
 * 
 */
package com.supconit.nhgl.schedule.service;

import java.util.List;

import com.supconit.nhgl.schedule.entites.MonitorTask;

import hc.base.domains.Pageable;

/**
 * @author 
 * @create 2014-06-16 18:01:51 
 * @since 
 * 
 */
public interface MonitorTaskService extends hc.orm.BasicOrmService<MonitorTask, Long> {

	/**
	 * 分页按条件查询。
	 * @param pager
	 * @param condition
	 * @return
	 */
	Pageable<MonitorTask> find(Pageable<MonitorTask> pager, MonitorTask condition);
	MonitorTask selectByTaskCode(String taskCode);
	Pageable<MonitorTask> findTaskList(Pageable<MonitorTask> pager, MonitorTask condition);
	MonitorTask findById(Integer id);
	List<MonitorTask> getByTaskType(Long taskType);
}
