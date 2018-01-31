package com.supconit.nhgl.alarm.device.dao.impl;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.alarm.device.dao.MEventRealAlarmDao;
import com.supconit.nhgl.alarm.device.entities.IRealAlarm;

import hc.base.domains.Pageable;
import hc.orm.AbstractBasicDaoImpl;
@Repository
public class IEventRealAlarmDaoImpl extends AbstractBasicDaoImpl<IRealAlarm, Long> implements MEventRealAlarmDao{
	private static final String	NAMESPACE	= IRealAlarm.class.getName();

	@Override
	public IRealAlarm getRealAlarmById(long id) {
		return selectOne("findById",id);
	}
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	@Override
	public Pageable<IRealAlarm> findRealAlarmsByConditions(Pageable<IRealAlarm> pager,IRealAlarm condition) {
		return findByPager(pager,"findByConditions","countByConditions",condition);
	}

}
