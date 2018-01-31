package com.supconit.nhgl.analyse.energy.dept.service;

import java.util.List;

import com.supconit.nhgl.analyse.energy.dept.entities.NhENDeptDay;



public interface NhENDeptDayService {
	NhENDeptDay getDeptHolidayDayNight(NhENDeptDay dept);
	NhENDeptDay getDeptDayNight(NhENDeptDay dept);
	List<NhENDeptDay> getDeptList(NhENDeptDay dept);
	List<NhENDeptDay> getDeptWeekList(NhENDeptDay dept);
	List<NhENDeptDay> getDeptWeekDays(NhENDeptDay dept);
	List<NhENDeptDay> getDeptDayList(NhENDeptDay dept);
	List<NhENDeptDay> getDeptDayNightList(NhENDeptDay dept);
	List<NhENDeptDay> getDeptHolidays(NhENDeptDay dept);
	List<NhENDeptDay> getDeptHolidayList(NhENDeptDay dept);
	List<NhENDeptDay> getDeptListCompareLastYear(NhENDeptDay dept);
}
