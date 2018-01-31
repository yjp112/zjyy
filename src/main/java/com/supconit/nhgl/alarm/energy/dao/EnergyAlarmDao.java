/**
 * 
 */
package com.supconit.nhgl.alarm.energy.dao;

import com.supconit.nhgl.alarm.energy.entities.EnergyAlarm;

import hc.base.domains.Pageable;
import hc.orm.BasicDao;


/**
 * @author 
 * @create 2014-06-16 18:01:16
 * @since 
 * 
 */
 
public interface EnergyAlarmDao extends BasicDao<EnergyAlarm, Long> {

	/**
	 * @param pager
	 * @param condition
	 * @return
	 */
	Pageable<EnergyAlarm> findByPager(Pageable<EnergyAlarm> pager, EnergyAlarm condition);
	public Pageable<EnergyAlarm> findByCondition(Pageable<EnergyAlarm> pager,EnergyAlarm condition);
	public EnergyAlarm findById(Long id);
}
