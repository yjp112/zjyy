/**
 * 
 */
package com.supconit.nhgl.schedule.service;

import com.supconit.nhgl.schedule.entites.MonitorObjScore;

import hc.base.domains.Pageable;

/**
 * @author 
 * @create 2014-06-16 18:01:23 
 * @since 
 * 
 */
public interface MonitorObjScoreService extends hc.orm.BasicOrmService<MonitorObjScore, Long> {

	/**
	 * 分页按条件查询。
	 * @param pager
	 * @param condition
	 * @return
	 */
	Pageable<MonitorObjScore> find(Pageable<MonitorObjScore> pager, MonitorObjScore condition);

}
