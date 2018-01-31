package com.supconit.nhgl.analyse.electric.area.service;

import java.util.List;

import com.supconit.nhgl.analyse.electric.area.entities.NhDAreaDay;

public interface NhDAreaDayService {
	List<NhDAreaDay> getAreaWeekDays(NhDAreaDay area);
	List<NhDAreaDay> getAreaWeekList(NhDAreaDay area);
	List<NhDAreaDay> getAreaList(NhDAreaDay area);
	List<NhDAreaDay> getAreaDayList(NhDAreaDay area);
	List<NhDAreaDay> getAreaDayNightList(NhDAreaDay area);
	List<NhDAreaDay> getAreaListCompareLastYear(NhDAreaDay area);
	List<NhDAreaDay> getAreaHolidayListCompareLastYear(NhDAreaDay area);
	NhDAreaDay getAreaDayNight(NhDAreaDay area);
	List<NhDAreaDay> getAreaHolidays(NhDAreaDay area);
	List<NhDAreaDay> getAreaHolidayList(NhDAreaDay area);
	NhDAreaDay getAreaHolidayDayNight(NhDAreaDay area);
}
