package com.supconit.nhgl.analyse.energy.area.service;

import java.util.List;

import com.supconit.nhgl.analyse.energy.area.entities.NhENAreaDay;


public interface NhENAreaDayService {

	List<NhENAreaDay> getAreaHolidays(NhENAreaDay area);
	List<NhENAreaDay> getAreaDayList(NhENAreaDay area);
	List<NhENAreaDay> getAreaDayNightList(NhENAreaDay area);
	List<NhENAreaDay> getAreaList(NhENAreaDay area);
	NhENAreaDay getAreaDayNight(NhENAreaDay area);
	List<NhENAreaDay> getAreaWeekList(NhENAreaDay area);
	List<NhENAreaDay> getAreaWeekDays(NhENAreaDay area);
	List<NhENAreaDay> getAreaHolidayList(NhENAreaDay area);
	NhENAreaDay getAreaHolidayDayNight(NhENAreaDay area);
	List<NhENAreaDay> getAreaListCompareLastYear(NhENAreaDay area);
}
