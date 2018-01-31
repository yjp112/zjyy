/**
 * 
 */
package com.supconit.nhgl.schedule.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.supconit.nhgl.schedule.entites.MonitorObject;

import hc.base.domains.Pageable;

/**
 * @author 
 * @create 2014-06-16 18:01:30
 * @since 
 * 
 */
 
@Repository("dlhmc_ycynjc_jcdx_dao")
public class MonitorObjectDaoImpl extends hc.orm.AbstractBasicDaoImpl<MonitorObject, Long> implements com.supconit.nhgl.schedule.dao.MonitorObjectDao {

	private static final String	NAMESPACE	= MonitorObject.class.getName();
	private transient static final Logger	log	= LoggerFactory.getLogger(MonitorObjectDaoImpl.class);

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	
	/*
	 * @see com.supconit.nhgl.ycynjc.dao.JcdxDao#findByPager(hc.base.domains.Pageable, com.supconit.nhgl.ycynjc.entities.MonitorObject)
	 */
	@Override
	public Pageable<MonitorObject> findByPager(Pageable<MonitorObject> pager, MonitorObject condition) {
		return findByPager(pager, "selectPager", "countPager", condition);
	}

	@Override
	public List<MonitorObject> findMonitorObject(MonitorObject condition) {
		return selectList("findMonitorObject", condition);
	}
	@Override
	public int deleteByTaskCode(String taskCode) {
		return delete("deleteByTaskCode", taskCode);
	}

	@Override
	public List<MonitorObject> findMonitorObjectByTaskCode(String taskCode) {
		return selectList("findMonitorObjectByTaskCode", taskCode);
	}

	@Override
	public List<MonitorObject> selectByTaskCode(String taskCode) {
		return selectList("selectByTaskCode", taskCode);
	}

	@Override
	public List<MonitorObject> findMonitorObjectFromAreaConfigAndDeptConfig(MonitorObject condition) {
		return selectList("findMonitorObjectFromAreaConfigAndDeptConfig", condition);
	}
}
