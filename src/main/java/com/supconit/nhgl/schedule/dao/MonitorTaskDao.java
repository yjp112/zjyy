/**
 * 
 */
package com.supconit.nhgl.schedule.dao;

import java.util.List;

import com.supconit.nhgl.schedule.entites.MonitorTask;

import hc.base.domains.Pageable;

/**
 * @author 
 * @create 2014-06-16 18:01:51
 * @since 
 * 
 */
 
public interface MonitorTaskDao extends hc.orm.BasicDao<MonitorTask, Long> {

	/**
	 * @param pager
	 * @param condition
	 * @return
	 */
	Pageable<MonitorTask> findByPager(Pageable<MonitorTask> pager, MonitorTask condition);
	MonitorTask selectByTaskCode(String taskCode);
	Pageable<MonitorTask> findTaskList(Pageable<MonitorTask> pager, MonitorTask condition);
	MonitorTask findById(Integer id);
	List<MonitorTask> getByTaskType(Long taskType);
}
