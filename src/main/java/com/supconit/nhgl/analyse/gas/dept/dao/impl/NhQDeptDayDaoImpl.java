package com.supconit.nhgl.analyse.gas.dept.dao.impl;

import hc.orm.AbstractBasicDaoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.analyse.gas.dept.dao.NhQDeptDayDao;
import com.supconit.nhgl.analyse.gas.dept.entities.NhQDeptDay;



@Repository
public class NhQDeptDayDaoImpl extends AbstractBasicDaoImpl<NhQDeptDay, Long> implements NhQDeptDayDao {

	private static final String NAMESPACE = NhQDeptDay.class.getName();
	

	@Override
	public List<NhQDeptDay> getDeptDayList(NhQDeptDay area) {
		return selectList("getDeptDayList",area);
	}


	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}


	@Override
	public List<NhQDeptDay> getDeptDayNightList(NhQDeptDay dept) {
		return selectList("getDeptDayNightList",dept);
	}


	@Override
	public NhQDeptDay getDeptDayNight(NhQDeptDay dept) {
		return selectOne("getDeptDayNight",dept);
	}


	@Override
	public List<NhQDeptDay> getDeptList(NhQDeptDay dept) {
		return selectList("getDeptList",dept);
	}


	@Override
	public List<NhQDeptDay> getDeptWeekList(NhQDeptDay dept) {
		return selectList("getDeptWeekList",dept);
	}


	@Override
	public List<NhQDeptDay> getDeptWeekDays(NhQDeptDay dept) {
		return selectList("getDeptWeekDays",dept);
	}


	@Override
	public List<NhQDeptDay> getDeptHolidays(NhQDeptDay dept) {
		return selectList("getDeptHolidays",dept);
	}


	@Override
	public List<NhQDeptDay> getDeptHolidayList(NhQDeptDay dept) {
		return selectList("getDeptHolidayList",dept);
	}


	@Override
	public NhQDeptDay getDeptHolidayDayNight(NhQDeptDay dept) {
		return selectOne("getDeptHolidayDayNight",dept);
	}


	@Override
	public List<NhQDeptDay> getDeptListCompareLastYear(NhQDeptDay area) {
		return selectList("getDeptListCompareLastYear",area);
	}
}
