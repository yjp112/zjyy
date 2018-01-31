package com.supconit.nhgl.analyse.water.dept.service;

import java.util.List;

import com.supconit.nhgl.analyse.water.dept.entities.NhSDeptDay;



public interface NhSDeptDayService {

	NhSDeptDay getDeptDayNight(NhSDeptDay dept);
	List<NhSDeptDay> getDeptWeekList(NhSDeptDay dept);
	List< NhSDeptDay> getDeptDayNightList( NhSDeptDay dept);
	List< NhSDeptDay> getDeptDayList( NhSDeptDay dept);
	List<NhSDeptDay> getDeptList(NhSDeptDay dept);
	List<NhSDeptDay> getDeptWeekDays(NhSDeptDay dept);
	List<NhSDeptDay> getDeptHolidays(NhSDeptDay dept);
	List<NhSDeptDay> getDeptHolidayList(NhSDeptDay dept);
	NhSDeptDay getDeptHolidayDayNight(NhSDeptDay dept);
	List<NhSDeptDay> getDeptListCompareLastYear(NhSDeptDay dept);
	List<NhSDeptDay> getDeptHolidayListCompareLastYear(NhSDeptDay dept);
}
