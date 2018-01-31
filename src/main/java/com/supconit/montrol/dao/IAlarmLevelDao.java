package com.supconit.montrol.dao;


import com.supconit.montrol.entity.MAlarmLevel;
import hc.base.domains.Pageable;

import java.util.List;

public interface IAlarmLevelDao  extends hc.orm.BasicDao<MAlarmLevel, Long>{
    public Pageable<MAlarmLevel> findByPager(Pageable<MAlarmLevel> pager,  MAlarmLevel condition);
	public MAlarmLevel findLevelByName(String name);
    public List<MAlarmLevel> findList();
    public Long getCount (MAlarmLevel condition);

}
