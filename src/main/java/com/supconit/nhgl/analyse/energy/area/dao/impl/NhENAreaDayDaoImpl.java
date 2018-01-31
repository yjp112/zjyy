package com.supconit.nhgl.analyse.energy.area.dao.impl;

import hc.orm.AbstractBasicDaoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.analyse.energy.area.dao.NhENAreaDayDao;
import com.supconit.nhgl.analyse.energy.area.entities.NhENAreaDay;


@Repository
public class NhENAreaDayDaoImpl extends AbstractBasicDaoImpl<NhENAreaDay, Long> implements NhENAreaDayDao {

	private static final String NAMESPACE = NhENAreaDay.class.getName();
	

	@Override
	public List<NhENAreaDay> getAreaDayList(NhENAreaDay area) {
		return selectList("getAreaDayList",area);
	}


	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}


	@Override
	public List<NhENAreaDay> getAreaDayNightList(NhENAreaDay area) {
		return selectList("getAreaDayNightList",area);
	}


	@Override
	public List<NhENAreaDay> getAreaList(NhENAreaDay area) {
		return selectList("getAreaList",area);
	}


	@Override
	public NhENAreaDay getAreaDayNight(NhENAreaDay area) {
		return selectOne("getAreaDayNight",area);
	}


	@Override
	public List<NhENAreaDay> getAreaWeekList(NhENAreaDay area) {
		return selectList("getAreaWeekList",area);
	}


	@Override
	public List<NhENAreaDay> getAreaWeekDays(NhENAreaDay area) {
		return selectList("getAreaWeekDays",area);
	}


	@Override
	public List<NhENAreaDay> getAreaHolidays(NhENAreaDay area) {
		return selectList("getAreaHolidays",area);
	}


	@Override
	public List<NhENAreaDay> getAreaHolidayList(NhENAreaDay area) {
		return selectList("getAreaHolidayList",area);
	}


	@Override
	public NhENAreaDay getAreaHolidayDayNight(NhENAreaDay area) {
		return selectOne("getAreaHolidayDayNight",area);
	}


	@Override
	public List<NhENAreaDay> getAreaListCompareLastYear(NhENAreaDay area) {
		return selectList("getAreaListCompareLastYear",area);
	}
}
