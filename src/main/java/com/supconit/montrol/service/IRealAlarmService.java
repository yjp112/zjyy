package com.supconit.montrol.service;

import java.util.List;
import com.supconit.montrol.entity.MRealAlarm;
import hc.base.domains.Pageable;

public interface IRealAlarmService extends hc.orm.BasicOrmService<MRealAlarm, Long>{

	public Pageable<MRealAlarm> find(Pageable<MRealAlarm> pager,  MRealAlarm condition);
	public MRealAlarm findNewAlarm();
	public long countRealAlarm();
	public List<MRealAlarm> findFirstFifthAlarm();

    public void displayAlarm();
	
}
