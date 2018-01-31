package com.supconit.nhgl.analyse.electric.dept.service;

import java.util.List;

import com.supconit.nhgl.analyse.electric.dept.entities.NhDDeptDay;


public interface NhDDeptDayService {

	NhDDeptDay getDeptDayNight(NhDDeptDay dept);
	List<NhDDeptDay> getDeptList(NhDDeptDay dept);
	List<NhDDeptDay> getDeptDayList(NhDDeptDay dept);
	List<NhDDeptDay> getDeptDayNightList(NhDDeptDay dept);
	List<NhDDeptDay> getDeptWeekList(NhDDeptDay dept);
	List<NhDDeptDay> getDeptWeekDays(NhDDeptDay dept);
	List<NhDDeptDay> getDeptHolidays(NhDDeptDay dept);
	List<NhDDeptDay> getDeptHolidayList(NhDDeptDay dept);
	NhDDeptDay getDeptHolidayDayNight(NhDDeptDay dept);
	List<NhDDeptDay> getDeptListCompareLastYear(NhDDeptDay dept);
	List<NhDDeptDay> getDeptHolidayListCompareLastYear(NhDDeptDay dept);
}
