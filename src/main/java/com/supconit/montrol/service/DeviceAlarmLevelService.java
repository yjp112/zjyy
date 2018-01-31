package com.supconit.montrol.service;

import com.supconit.montrol.entity.DeviceAlarmLevel;
import com.supconit.montrol.entity.DeviceTag;
import hc.base.domains.Pageable;
import hc.orm.BasicOrmService;

import java.util.List;

public interface DeviceAlarmLevelService extends BasicOrmService<DeviceAlarmLevel, Long> {
    Pageable<DeviceAlarmLevel> find(Pageable<DeviceAlarmLevel> pager, DeviceAlarmLevel condition);
    List<DeviceAlarmLevel> findList(DeviceAlarmLevel condition);
    void delByTagId(Long condition);
}
