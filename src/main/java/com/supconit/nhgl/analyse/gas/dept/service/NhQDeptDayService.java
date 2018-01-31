package com.supconit.nhgl.analyse.gas.dept.service;

import java.util.List;

import com.supconit.nhgl.analyse.gas.dept.entities.NhQDeptDay;



public interface NhQDeptDayService {

	List<NhQDeptDay> getDeptDayNightList(NhQDeptDay dept);
	List<NhQDeptDay> getDeptDayList(NhQDeptDay dept);
	NhQDeptDay getDeptDayNight(NhQDeptDay dept);
	List<NhQDeptDay> getDeptList(NhQDeptDay dept);
	List<NhQDeptDay> getDeptWeekList(NhQDeptDay dept);
	List<NhQDeptDay> getDeptWeekDays(NhQDeptDay dept);
	List<NhQDeptDay> getDeptHolidays(NhQDeptDay dept);
	List<NhQDeptDay> getDeptHolidayList(NhQDeptDay dept);
	NhQDeptDay getDeptHolidayDayNight(NhQDeptDay dept);
	List<NhQDeptDay> getDeptListCompareLastYear(NhQDeptDay area);
}
