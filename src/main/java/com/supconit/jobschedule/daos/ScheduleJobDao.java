/**
 * 
 */
package com.supconit.jobschedule.daos;

import java.util.List;

import com.supconit.jobschedule.entities.ScheduleJob;

import hc.base.domains.Pageable;

/**
 * @author 
 * @create 2014-06-05 11:21:48
 * @since 
 * 
 */
 
public interface ScheduleJobDao extends hc.orm.BasicDao<ScheduleJob, Long> {

	/**
	 * @param pager
	 * @param condition
	 * @return
	 */
	Pageable<ScheduleJob> findByPager(Pageable<ScheduleJob> pager, ScheduleJob condition);

	List<ScheduleJob> selectByStatus(int status);	
}
