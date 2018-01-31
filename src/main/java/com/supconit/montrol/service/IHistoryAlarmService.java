package com.supconit.montrol.service;

import com.supconit.montrol.entity.MHistoryAlarm;
import hc.base.domains.Pageable;


public interface IHistoryAlarmService extends hc.orm.BasicOrmService<MHistoryAlarm, Long>{
    Pageable<MHistoryAlarm> find(Pageable<MHistoryAlarm> pager, MHistoryAlarm condition);
	public MHistoryAlarm findFirstAlarm();

    public void displayAlarm();
    /**
     * 查询设备第一次报警
     */
    MHistoryAlarm getByDeviceId(Long deviceId);
}
