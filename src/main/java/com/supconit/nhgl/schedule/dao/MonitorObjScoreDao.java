/**
 * 
 */
package com.supconit.nhgl.schedule.dao;

import com.supconit.nhgl.schedule.entites.MonitorObjScore;

import hc.base.domains.Pageable;

/**
 * @author 
 * @create 2014-06-16 18:01:23
 * @since 
 * 
 */
 
public interface MonitorObjScoreDao extends hc.orm.BasicDao<MonitorObjScore, Long> {

	/**
	 * @param pager
	 * @param condition
	 * @return
	 */
	Pageable<MonitorObjScore> findByPager(Pageable<MonitorObjScore> pager, MonitorObjScore condition);

}
