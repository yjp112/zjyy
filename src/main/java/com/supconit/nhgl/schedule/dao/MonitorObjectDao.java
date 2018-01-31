/**
 * 
 */
package com.supconit.nhgl.schedule.dao;

import java.util.List;

import com.supconit.nhgl.schedule.entites.MonitorObject;

import hc.base.domains.Pageable;

/**
 * @author 
 * @create 2014-06-16 18:01:30
 * @since 
 * 
 */
 
public interface MonitorObjectDao extends hc.orm.BasicDao<MonitorObject, Long> {

	/**
	 * @param pager
	 * @param condition
	 * @return
	 */
	Pageable<MonitorObject> findByPager(Pageable<MonitorObject> pager, MonitorObject condition);
	
	List<MonitorObject> findMonitorObject(MonitorObject condition);
	List<MonitorObject> findMonitorObjectByTaskCode(String taskCode);
	List<MonitorObject> selectByTaskCode(String taskCode);
	int deleteByTaskCode(String taskCode);

	List<MonitorObject> findMonitorObjectFromAreaConfigAndDeptConfig(MonitorObject condition);
}
