package com.supconit.nhgl.analyse.electric.area.dao.impl;

import hc.orm.AbstractBasicDaoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.analyse.electric.area.dao.NhDAreaDayDao;
import com.supconit.nhgl.analyse.electric.area.entities.NhDAreaDay;

@Repository
public class NhDAreaDayDaoImpl extends AbstractBasicDaoImpl<NhDAreaDay, Long> implements NhDAreaDayDao {

	private static final String NAMESPACE = NhDAreaDay.class.getName();
	

	@Override
	public List<NhDAreaDay> getAreaDayList(NhDAreaDay area) {
		return selectList("getAreaDayList",area);
	}


	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}


	@Override
	public List<NhDAreaDay> getAreaDayNightList(NhDAreaDay area) {
		return selectList("getAreaDayNightList",area);
	}


	@Override
	public List<NhDAreaDay> getAreaList(NhDAreaDay area) {
		return selectList("getAreaList",area);
	}


	@Override
	public List<NhDAreaDay> getAreaWeekList(NhDAreaDay area) {
		return selectList("getAreaWeekList",area);
	}


	@Override
	public NhDAreaDay getAreaDayNight(NhDAreaDay area) {
		return selectOne("getAreaDayNight",area);
	}


	@Override
	public List<NhDAreaDay> getAreaWeekDays(NhDAreaDay area) {
		return selectList("getAreaWeekDays",area);
	}


	@Override
	public List<NhDAreaDay> getAreaHolidays(NhDAreaDay area) {
		return selectList("getAreaHolidays",area);
	}


	@Override
	public List<NhDAreaDay> getAreaHolidayList(NhDAreaDay area) {
		return selectList("getAreaHolidayList",area);
	}


	@Override
	public NhDAreaDay getAreaHolidayDayNight(NhDAreaDay area) {
		return selectOne("getAreaHolidayDayNight",area);
	}


	@Override
	public List<NhDAreaDay> getAreaListCompareLastYear(NhDAreaDay area) {
		return selectList("getAreaListCompareLastYear",area);
	}


	@Override
	public List<NhDAreaDay> getAreaHolidayListCompareLastYear(NhDAreaDay area) {
		return selectList("getAreaHolidayListCompareLastYear",area);
	}
}
