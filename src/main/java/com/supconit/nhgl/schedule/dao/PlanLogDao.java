/**
 * 
 */
package com.supconit.nhgl.schedule.dao;

import com.supconit.nhgl.schedule.entites.PlanLog;

import hc.base.domains.Pageable;

/**
 * @author 
 * @create 2014-06-05 11:26:12
 * @since 
 * 
 */
 
public interface PlanLogDao extends hc.orm.BasicDao<PlanLog, Long> {

	/**
	 * @param pager
	 * @param condition
	 * @return
	 */
	int deleteByIds(Long[] ids);
	Pageable<PlanLog> findLogByPager(Pageable<PlanLog> pager,
			PlanLog condition);
}
