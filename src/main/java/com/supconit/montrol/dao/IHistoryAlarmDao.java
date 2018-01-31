package com.supconit.montrol.dao;

import com.supconit.montrol.entity.MHistoryAlarm;
import hc.base.domains.Pageable;

public interface IHistoryAlarmDao  extends hc.orm.BasicDao<MHistoryAlarm, Long>{

    public Pageable<MHistoryAlarm> findByPager(Pageable<MHistoryAlarm> pager,  MHistoryAlarm condition);
	public int disappearAlarmById(long id);
	public MHistoryAlarm findFirstAlarm();

    public  void displayAlarm();
    /**
     * 查询设备第一次报警
     */
    MHistoryAlarm getByDeviceId(Long deviceId);
}
