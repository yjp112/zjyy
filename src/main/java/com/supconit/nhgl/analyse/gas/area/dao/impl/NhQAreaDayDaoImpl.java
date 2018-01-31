package com.supconit.nhgl.analyse.gas.area.dao.impl;

import hc.orm.AbstractBasicDaoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.analyse.gas.area.dao.NhQAreaDayDao;
import com.supconit.nhgl.analyse.gas.area.entities.NhQAreaDay;


@Repository
public class NhQAreaDayDaoImpl extends AbstractBasicDaoImpl<NhQAreaDay, Long> implements NhQAreaDayDao {

	private static final String NAMESPACE = NhQAreaDay.class.getName();
	

	@Override
	public List<NhQAreaDay> getAreaDayList(NhQAreaDay area) {
		return selectList("getAreaDayList",area);
	}


	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}


	@Override
	public List<NhQAreaDay> getAreaDayNightList(NhQAreaDay area) {
		return selectList("getAreaDayNightList",area);
	}


	@Override
	public List<NhQAreaDay> getAreaList(NhQAreaDay area) {
		return selectList("getAreaList",area);
	}


	@Override
	public NhQAreaDay getAreaDayNight(NhQAreaDay area) {
		return selectOne("getAreaDayNight",area);
	}


	@Override
	public List<NhQAreaDay> getAreaWeekList(NhQAreaDay area) {
		return selectList("getAreaWeekList",area);
	}


	@Override
	public List<NhQAreaDay> getAreaWeekDays(NhQAreaDay area) {
		return selectList("getAreaWeekDays", area);
	}


	@Override
	public List<NhQAreaDay> getAreaHolidays(NhQAreaDay area) {
		return selectList("getAreaHolidays",area);
	}


	@Override
	public List<NhQAreaDay> getAreaHolidayList(NhQAreaDay area) {
		return selectList("getAreaHolidayList",area);
	}


	@Override
	public NhQAreaDay getAreaHolidayDayNight(NhQAreaDay area) {
		return selectOne("getAreaHolidayDayNight",area);
	}


	@Override
	public List<NhQAreaDay> getAreaListCompareLastYear(NhQAreaDay area) {
		return selectList("getAreaListCompareLastYear",area);
	}
}
