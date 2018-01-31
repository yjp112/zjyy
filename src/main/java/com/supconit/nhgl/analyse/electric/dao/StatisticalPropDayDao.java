package com.supconit.nhgl.analyse.electric.dao;

import java.util.List;

import com.supconit.nhgl.analyse.electric.entities.StatisticalPropDay;

import hc.orm.BasicDao;

public interface StatisticalPropDayDao extends BasicDao<StatisticalPropDay, Long>{

	
	List<StatisticalPropDay> findByDate(StatisticalPropDay sta);
	List<StatisticalPropDay> findByDay(StatisticalPropDay sta);
	List<StatisticalPropDay> findByHoildayLast(StatisticalPropDay sta);
	StatisticalPropDay getMaxDate();
	void insertSpd(List<StatisticalPropDay> stalist);
	StatisticalPropDay getWeekTime(StatisticalPropDay sta);
}
