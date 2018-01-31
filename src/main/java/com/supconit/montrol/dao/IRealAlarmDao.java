package com.supconit.montrol.dao;

import java.util.List;

import com.supconit.montrol.entity.MRealAlarm;
import hc.base.domains.Pageable;

public interface IRealAlarmDao extends hc.orm.BasicDao<MRealAlarm, Long>{
    public Pageable<MRealAlarm> findByPager(Pageable<MRealAlarm> pager,  MRealAlarm condition);
	public MRealAlarm findNewAlarm();
	public long countRealAlarm();
	public List<MRealAlarm> findFirstFifthAlarm();

    public  void displayAlarm();
}
