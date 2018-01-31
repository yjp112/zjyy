package com.supconit.nhgl.analyse.water.area.dao.impl;

import hc.orm.AbstractBasicDaoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.analyse.water.area.dao.NhSAreaDayDao;
import com.supconit.nhgl.analyse.water.area.entities.NhSAreaDay;



@Repository
public class NhSAreaDayDaoImpl extends AbstractBasicDaoImpl<NhSAreaDay, Long> implements NhSAreaDayDao {

	private static final String NAMESPACE = NhSAreaDay.class.getName();
	

	@Override
	public List<NhSAreaDay> getAreaDayList(NhSAreaDay area) {
		return selectList("getAreaDayList",area);
	}


	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}


	@Override
	public List<NhSAreaDay> getAreaDayNightList(NhSAreaDay area) {
		return selectList("getAreaDayNightList",area);
	}


	@Override
	public List<NhSAreaDay> getAreaList(NhSAreaDay area) {
		return selectList("getAreaList",area);
	}


	@Override
	public List<NhSAreaDay> getAreaWeekList(NhSAreaDay area) {
		return selectList("getAreaWeekList",area);
	}


	@Override
	public NhSAreaDay getAreaDayNight(NhSAreaDay area) {
		return selectOne("getAreaDayNight",area);
	}


	@Override
	public List<NhSAreaDay> getAreaWeekDays(NhSAreaDay area) {
		return selectList("getAreaWeekDays",area);
	}


	@Override
	public List<NhSAreaDay> getAreaHolidays(NhSAreaDay area) {
		return selectList("getAreaHolidays",area);
	}


	@Override
	public List<NhSAreaDay> getAreaHolidayList(NhSAreaDay area) {
		return selectList("getAreaHolidayList",area);
	}


	@Override
	public NhSAreaDay getAreaHolidayDayNight(NhSAreaDay area) {
		return selectOne("getAreaHolidayDayNight",area);
	}


	@Override
	public List<NhSAreaDay> getAreaListCompareLastYear(NhSAreaDay area) {
		return selectList("getAreaListCompareLastYear",area);
	}


	@Override
	public List<NhSAreaDay> getAreaHolidayListCompareLastYear(NhSAreaDay area) {
		return selectList("getAreaHolidayListCompareLastYear",area);
	}
}
