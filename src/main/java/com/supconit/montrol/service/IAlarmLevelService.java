package com.supconit.montrol.service;

import com.supconit.montrol.entity.MAlarmLevel;
import hc.base.domains.Pageable;
import hc.orm.BasicOrmService;

import java.util.List;

public interface IAlarmLevelService  extends BasicOrmService<MAlarmLevel, Long> {
    Pageable<MAlarmLevel> find(Pageable<MAlarmLevel> pager, MAlarmLevel condition);
    List<MAlarmLevel> findList();

    Long getCount (MAlarmLevel condition);
}
