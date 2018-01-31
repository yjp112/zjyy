package com.supconit.nhgl.analyse.electric.dept.dao.impl;

import hc.orm.AbstractBasicDaoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.analyse.electric.dept.dao.NhDDeptDayDao;
import com.supconit.nhgl.analyse.electric.dept.entities.NhDDeptDay;


@Repository
public class NhDDeptDayDaoImpl extends AbstractBasicDaoImpl<NhDDeptDay, Long> implements NhDDeptDayDao {

	private static final String NAMESPACE = NhDDeptDay.class.getName();
	

	@Override
	public List<NhDDeptDay> getDeptDayList(NhDDeptDay area) {
		return selectList("getDeptDayList",area);
	}


	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}


	@Override
	public List<NhDDeptDay> getDeptDayNightList(NhDDeptDay dept) {
		return selectList("getDeptDayNightList",dept);
	}


	@Override
	public List<NhDDeptDay> getDeptList(NhDDeptDay area) {
		return selectList("getDeptList",area);
	}


	@Override
	public List<NhDDeptDay> getDeptWeekList(NhDDeptDay dept) {
		return selectList("getDeptWeekList",dept);
	}


	@Override
	public NhDDeptDay getDeptDayNight(NhDDeptDay dept) {
		return selectOne("getDeptDayNight",dept);
	}


	@Override
	public List<NhDDeptDay> getDeptWeekDays(NhDDeptDay dept) {
		return selectList("getDeptWeekDays",dept);
	}


	@Override
	public List<NhDDeptDay> getDeptHolidays(NhDDeptDay dept) {
		return selectList("getDeptHolidays",dept);
	}


	@Override
	public List<NhDDeptDay> getDeptHolidayList(NhDDeptDay dept) {
		return selectList("getDeptHolidayList",dept);
	}


	@Override
	public NhDDeptDay getDeptHolidayDayNight(NhDDeptDay dept) {
		return selectOne("getDeptHolidayDayNight",dept);
	}


	@Override
	public List<NhDDeptDay> getDeptListCompareLastYear(NhDDeptDay dept) {
		return selectList("getDeptListCompareLastYear",dept);
	}


	@Override
	public List<NhDDeptDay> getDeptHolidayListCompareLastYear(NhDDeptDay dept) {
		return selectList("getDeptHolidayListCompareLastYear",dept);
	}
}
