package com.supconit.nhgl.alarm.device.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.nhgl.alarm.device.dao.MEventRealAlarmDao;
import com.supconit.nhgl.alarm.device.entities.IRealAlarm;
import com.supconit.nhgl.alarm.device.service.MAlarmEventService;

import hc.base.domains.Pageable;
@Service
public class MAlarmEventServiceImpl implements MAlarmEventService{
	@Autowired
	private MEventRealAlarmDao realAlarmDao;
	@Override
	public IRealAlarm findRealAlarmById(long id) {
		return realAlarmDao.getRealAlarmById(id);
	}
	@Override
	public Pageable<IRealAlarm> findRealAlarmsByConditions(Pageable<IRealAlarm> pager, IRealAlarm condition) {
		return realAlarmDao.findRealAlarmsByConditions(pager,condition);
	}
}
