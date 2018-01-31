package com.supconit.nhgl.analyse.electric.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.nhgl.analyse.electric.dao.StatisticalPropDayDao;
import com.supconit.nhgl.analyse.electric.entities.StatisticalPropDay;
import com.supconit.nhgl.analyse.electric.service.StatisticalPropDayService;

@Service
public class StatisticalPropDayServiceImpl implements StatisticalPropDayService {
	@Autowired
	private StatisticalPropDayDao dao;
	@Override
	public List<StatisticalPropDay> findByDate(StatisticalPropDay sta) {
		return dao.findByDate(sta);
	}
	@Override
	public List<StatisticalPropDay> findByDay(StatisticalPropDay sta) {
		return dao.findByDay(sta);
	}
	@Override
	public void insertSpd(List<StatisticalPropDay> stalist) {
		dao.insertSpd(stalist);
	}
	@Override
	public List<StatisticalPropDay> findByHoildayLast(StatisticalPropDay sta) {
		return dao.findByHoildayLast(sta);
	}
	@Override
	public StatisticalPropDay getMaxDate() {
		return dao.getMaxDate();
	}
	
	@Override
	public StatisticalPropDay getWeekTime(StatisticalPropDay sta) {
		return dao.getWeekTime(sta);
	}
}
