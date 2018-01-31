package com.supconit.nhgl.analyse.water.dept.dao;

import java.util.List;

import com.supconit.nhgl.analyse.water.dept.entities.NhSDeptDay;



public interface NhSDeptDayDao {


	NhSDeptDay getDeptHolidayDayNight(NhSDeptDay dept);
    NhSDeptDay getDeptDayNight(NhSDeptDay dept);
	List<NhSDeptDay> getDeptWeekList(NhSDeptDay dept);
	List<NhSDeptDay> getDeptList(NhSDeptDay dept);
	List<NhSDeptDay> getDeptHolidayList(NhSDeptDay dept);
	List<NhSDeptDay> getDeptDayNightList(NhSDeptDay dept);
	List<NhSDeptDay> getDeptDayList(NhSDeptDay dept);
	List<NhSDeptDay> getDeptListCompareLastYear(NhSDeptDay dept);
	List<NhSDeptDay> getDeptHolidayListCompareLastYear(NhSDeptDay dept);
	List<NhSDeptDay> getDeptWeekDays(NhSDeptDay dept);
	List<NhSDeptDay> getDeptHolidays(NhSDeptDay dept);

}
