package com.supconit.nhgl.analyse.energy.dept.dao;

import java.util.List;

import com.supconit.nhgl.analyse.energy.dept.entities.NhENDeptDay;



public interface NhENDeptDayDao {


	List<NhENDeptDay> getDeptHolidays(NhENDeptDay dept);
	List<NhENDeptDay> getDeptDayNightList(NhENDeptDay dept);
	List<NhENDeptDay> getDeptDayList(NhENDeptDay area);
	NhENDeptDay getDeptDayNight(NhENDeptDay dept);
	NhENDeptDay getDeptHolidayDayNight(NhENDeptDay dept);
	List<NhENDeptDay> getDeptList(NhENDeptDay dept);
	List<NhENDeptDay> getDeptHolidayList(NhENDeptDay dept);
	List<NhENDeptDay> getDeptWeekList(NhENDeptDay dept);
	List<NhENDeptDay> getDeptWeekDays(NhENDeptDay dept);
	List<NhENDeptDay> getDeptListCompareLastYear(NhENDeptDay dept);
}
