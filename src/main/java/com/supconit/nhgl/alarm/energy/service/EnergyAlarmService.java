/**
 * 
 */
package com.supconit.nhgl.alarm.energy.service;

import com.supconit.nhgl.alarm.energy.entities.EnergyAlarm;

import hc.base.domains.Pageable;
import hc.orm.BasicOrmService;


/**
 * @author 
 * @create 2014-06-16 18:01:16 
 * @since 
 * 
 */
public interface EnergyAlarmService extends BasicOrmService<EnergyAlarm, Long> {

	/**
	 * 分页按条件查询。
	 * @param pager
	 * @param condition
	 * @return
	 */
	Pageable<EnergyAlarm> find(Pageable<EnergyAlarm> pager, EnergyAlarm condition);
	public Pageable<EnergyAlarm> findByCondition(Pageable<EnergyAlarm> pager,EnergyAlarm condition);
	public EnergyAlarm findById(Long id);
}
