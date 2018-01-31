package com.supconit.montrol.dao;


import com.supconit.montrol.entity.DeviceAlarmLevel;
import hc.base.domains.Pageable;

import java.util.List;

public interface DeviceAlarmLevelDao extends hc.orm.BasicDao<DeviceAlarmLevel, Long>{
    public Pageable<DeviceAlarmLevel> findByPager(Pageable<DeviceAlarmLevel> pager, DeviceAlarmLevel condition);

    List<DeviceAlarmLevel> findList(DeviceAlarmLevel condition);

    void delByTagId(Long condition);
}
