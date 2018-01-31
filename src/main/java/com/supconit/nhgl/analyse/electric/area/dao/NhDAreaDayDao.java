package com.supconit.nhgl.analyse.electric.area.dao;

import java.util.List;

import com.supconit.nhgl.analyse.electric.area.entities.NhDAreaDay;

public interface NhDAreaDayDao {


	List<NhDAreaDay> getAreaHolidays(NhDAreaDay area);
	List<NhDAreaDay> getAreaHolidayList(NhDAreaDay area);
	List<NhDAreaDay> getAreaDayList(NhDAreaDay area);
	List<NhDAreaDay> getAreaDayNightList(NhDAreaDay area);
	List<NhDAreaDay> getAreaList(NhDAreaDay area);
	List<NhDAreaDay> getAreaListCompareLastYear(NhDAreaDay area);
	List<NhDAreaDay> getAreaHolidayListCompareLastYear(NhDAreaDay area);
	NhDAreaDay getAreaDayNight(NhDAreaDay area);
	NhDAreaDay getAreaHolidayDayNight(NhDAreaDay area);
	List<NhDAreaDay> getAreaWeekList(NhDAreaDay area);
	List<NhDAreaDay> getAreaWeekDays(NhDAreaDay area);

}
