package com.supconit.nhgl.alarm.device.service;

import com.supconit.nhgl.alarm.device.entities.IRealAlarm;

import hc.base.domains.Pageable;


public interface MAlarmEventService {
	
	public Pageable<IRealAlarm> findRealAlarmsByConditions(Pageable<IRealAlarm> pager,IRealAlarm condition);
	public IRealAlarm findRealAlarmById(long id);
	
	
}
