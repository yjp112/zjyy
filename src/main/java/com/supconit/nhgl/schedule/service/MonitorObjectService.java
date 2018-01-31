/**
 * 
 */
package com.supconit.nhgl.schedule.service;

import java.util.List;

import com.supconit.nhgl.schedule.entites.MonitorObject;

import hc.base.domains.Pageable;

/**
 * @author 
 * @create 2014-06-16 18:01:30 
 * @since 
 * 
 */
public interface MonitorObjectService extends hc.orm.BasicOrmService<MonitorObject, Long> {

	/**
	 * 分页按条件查询。
	 * @param pager
	 * @param condition
	 * @return
	 */
	Pageable<MonitorObject> find(Pageable<MonitorObject> pager, MonitorObject condition);
	List<MonitorObject> selectByTaskCode(String taskCode);
	int deleteByTaskCode(String taskCode);
	
	/**从device表中查询
	 * @param condition
	 * @return
	 */
	List<MonitorObject> findMonitorObject(MonitorObject condition);
	/**从device表中查询
	 * @param condition
	 * @return
	 */
	List<MonitorObject> findMonitorObjectByTaskCode(String taskCode);
}
