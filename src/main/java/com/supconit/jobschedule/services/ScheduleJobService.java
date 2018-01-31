/**
 * 
 */
package com.supconit.jobschedule.services;

import java.util.List;

import com.supconit.jobschedule.entities.ScheduleJob;

import hc.base.domains.Pageable;

/**
 * @author 
 * @create 2014-06-05 11:26:12 
 * @since 
 * 
 */
public interface ScheduleJobService extends hc.orm.BasicOrmService<ScheduleJob, Long> {

	/**
	 * 分页按条件查询。
	 * @param pager
	 * @param condition
	 * @return
	 */
	Pageable<ScheduleJob> find(Pageable<ScheduleJob> pager, ScheduleJob condition);

	List<ScheduleJob> findAllEnabled();

	void updateByPrimaryKeySelective(ScheduleJob scheduleJob);

}
