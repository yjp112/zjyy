/**
 * 
 */
package com.supconit.jobschedule.services;

import com.supconit.jobschedule.entities.ScheduleLog;

import hc.base.domains.Pageable;

/**
 * @author 
 * @create 2014-06-05 11:21:48 
 * @since 
 * 
 */
public interface ScheduleLogService extends hc.orm.BasicOrmService<ScheduleLog, Long> {

	/**
	 * 分页按条件查询。
	 * @param pager
	 * @param condition
	 * @return
	 */
	Pageable<ScheduleLog> find(Pageable<ScheduleLog> pager, ScheduleLog condition);

	void insertSelective(ScheduleLog scheduleLog);
	int deleteByIds(Long[] ids);
	int updateErrorStatus();
}
