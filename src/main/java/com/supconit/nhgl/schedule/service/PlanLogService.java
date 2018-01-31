/**
 * 
 */
package com.supconit.nhgl.schedule.service;

import com.supconit.nhgl.schedule.entites.PlanLog;

import hc.base.domains.Pageable;

/**
 * @author 
 * @create 2014-06-05 11:21:48 
 * @since 
 * 
 */
public interface PlanLogService extends hc.orm.BasicOrmService<PlanLog, Long> {

	/**
	 * 执行日志分页查询
	 * @param pager
	 * @param condition
	 * @author 王海波
	 * @return
	 */
	Pageable<PlanLog> findLog(Pageable<PlanLog> pager, PlanLog condition);
	int deleteByIds(Long[] ids);
}
