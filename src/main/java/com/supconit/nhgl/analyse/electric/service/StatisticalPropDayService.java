package com.supconit.nhgl.analyse.electric.service;

import java.util.List;

import com.supconit.nhgl.analyse.electric.entities.StatisticalPropDay;

public interface StatisticalPropDayService {
	
	List<StatisticalPropDay> findByDate(StatisticalPropDay sta);
	List<StatisticalPropDay> findByDay(StatisticalPropDay sta);
	List<StatisticalPropDay> findByHoildayLast(StatisticalPropDay sta);
	StatisticalPropDay getMaxDate();
	void insertSpd(List<StatisticalPropDay> stalist);
	StatisticalPropDay getWeekTime(StatisticalPropDay sta);
}
