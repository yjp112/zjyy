package com.supconit.nhgl.analyse.water.area.service;

import java.util.List;

import com.supconit.nhgl.analyse.water.area.entities.NhSAreaDay;


public interface NhSAreaDayService {

	List<NhSAreaDay> getAreaHolidays(NhSAreaDay area);
	List<NhSAreaDay> getAreaWeekDays(NhSAreaDay area);
	List<NhSAreaDay> getAreaWeekList(NhSAreaDay area);
	List<NhSAreaDay> getAreaDayList(NhSAreaDay area);
	List<NhSAreaDay> getAreaDayNightList(NhSAreaDay area);
	List<NhSAreaDay> getAreaList(NhSAreaDay area);
	NhSAreaDay getAreaDayNight(NhSAreaDay area);
	List<NhSAreaDay> getAreaHolidayList(NhSAreaDay area);
	NhSAreaDay getAreaHolidayDayNight(NhSAreaDay area);
	List<NhSAreaDay> getAreaListCompareLastYear(NhSAreaDay area);
	List<NhSAreaDay> getAreaHolidayListCompareLastYear(NhSAreaDay area);
}
