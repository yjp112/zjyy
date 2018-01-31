package com.supconit.nhgl.analyse.gas.dept.dao;

import java.util.List;

import com.supconit.nhgl.analyse.gas.dept.entities.NhQDeptDay;



public interface NhQDeptDayDao {

	NhQDeptDay getDeptDayNight(NhQDeptDay dept);
	NhQDeptDay getDeptHolidayDayNight(NhQDeptDay dept);
	List<NhQDeptDay> getDeptList(NhQDeptDay dept);
	List<NhQDeptDay> getDeptHolidayList(NhQDeptDay dept);
	List<NhQDeptDay> getDeptWeekList(NhQDeptDay dept);
	List<NhQDeptDay> getDeptWeekDays(NhQDeptDay dept);
	List<NhQDeptDay> getDeptDayNightList(NhQDeptDay dept);
	List<NhQDeptDay> getDeptDayList(NhQDeptDay area);
	List<NhQDeptDay> getDeptHolidays(NhQDeptDay area);
	List<NhQDeptDay> getDeptListCompareLastYear(NhQDeptDay area);

}
