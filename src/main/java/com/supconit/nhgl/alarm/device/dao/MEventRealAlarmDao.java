package com.supconit.nhgl.alarm.device.dao;

import com.supconit.nhgl.alarm.device.entities.IRealAlarm;

import hc.base.domains.Pageable;
import hc.orm.BasicDao;


public interface MEventRealAlarmDao extends BasicDao<IRealAlarm, Long>{
	public Pageable<IRealAlarm> findRealAlarmsByConditions(Pageable<IRealAlarm> pager,IRealAlarm condition);
	public IRealAlarm getRealAlarmById(long id);
}
