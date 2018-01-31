package com.supconit.nhgl.analyse.gas.area.dao;

import java.util.List;

import com.supconit.nhgl.analyse.gas.area.entities.NhQAreaDay;


public interface NhQAreaDayDao {

	List<NhQAreaDay> getAreaList(NhQAreaDay area);
	List<NhQAreaDay> getAreaHolidayList(NhQAreaDay area);
	NhQAreaDay getAreaDayNight(NhQAreaDay area);
	NhQAreaDay getAreaHolidayDayNight(NhQAreaDay area);
	List<NhQAreaDay> getAreaWeekList(NhQAreaDay area);
	List<NhQAreaDay> getAreaWeekDays(NhQAreaDay area);
	List<NhQAreaDay> getAreaDayNightList(NhQAreaDay area);
	List<NhQAreaDay> getAreaDayList(NhQAreaDay area);
	List<NhQAreaDay> getAreaHolidays(NhQAreaDay area);
	List<NhQAreaDay> getAreaListCompareLastYear(NhQAreaDay area);

}
