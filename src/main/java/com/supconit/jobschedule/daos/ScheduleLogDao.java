/**
 * 
 */
package com.supconit.jobschedule.daos;

import com.supconit.jobschedule.entities.ScheduleLog;

import hc.base.domains.Pageable;

/**
 * @author 
 * @create 2014-06-05 11:26:12
 * @since 
 * 
 */
 
public interface ScheduleLogDao extends hc.orm.BasicDao<ScheduleLog, Long> {

	/**
	 * @param pager
	 * @param condition
	 * @return
	 */
	Pageable<ScheduleLog> findByPager(Pageable<ScheduleLog> pager, ScheduleLog condition);
	int deleteByIds(Long[] ids);
	int updateErrorStatus();
}
