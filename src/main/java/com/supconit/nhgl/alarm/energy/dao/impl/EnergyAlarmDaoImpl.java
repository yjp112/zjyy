/**
 * 
 */
package com.supconit.nhgl.alarm.energy.dao.impl;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.alarm.energy.dao.EnergyAlarmDao;
import com.supconit.nhgl.alarm.energy.entities.EnergyAlarm;

import hc.base.domains.Pageable;
import hc.orm.AbstractBasicDaoImpl;

/**
 * @author 
 * @create 2014-06-16 18:01:16
 * @since 
 * 
 */
 
@Repository("dlhmc_ycynjc_ynbj_dao")
public class EnergyAlarmDaoImpl extends AbstractBasicDaoImpl<EnergyAlarm, Long> implements EnergyAlarmDao {

	private static final String	NAMESPACE	= EnergyAlarm.class.getName();

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	
	/*
	 * @see com.supconit.nhgl.ycynjc.dao.YnbjDao#findByPager(hc.base.domains.Pageable, com.supconit.nhgl.ycynjc.entities.EnergyAlarm)
	 */
	@Override
	public Pageable<EnergyAlarm> findByPager(Pageable<EnergyAlarm> pager, EnergyAlarm condition) {
		return findByPager(pager, "selectPager", "countPager", condition);
	}

	@Override
	public Pageable<EnergyAlarm> findByCondition(Pageable<EnergyAlarm> pager,
			EnergyAlarm condition) {
		return findByPager(pager,"findByCondition","countByCondition",condition);
	}

	@Override
	public EnergyAlarm findById(Long id) {
		return selectOne("getById",id);
	}
}
