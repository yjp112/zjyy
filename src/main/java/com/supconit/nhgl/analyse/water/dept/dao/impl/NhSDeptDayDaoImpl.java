package com.supconit.nhgl.analyse.water.dept.dao.impl;

import hc.orm.AbstractBasicDaoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.analyse.water.dept.dao.NhSDeptDayDao;
import com.supconit.nhgl.analyse.water.dept.entities.NhSDeptDay;



@Repository
public class NhSDeptDayDaoImpl extends AbstractBasicDaoImpl<NhSDeptDay, Long> implements NhSDeptDayDao {

	private static final String NAMESPACE = NhSDeptDay.class.getName();
	

	@Override
	public List<NhSDeptDay> getDeptDayList(NhSDeptDay area) {
		return selectList("getDeptDayList",area);
	}


	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}


	@Override
	public List<NhSDeptDay> getDeptDayNightList(NhSDeptDay dept) {
		return selectList("getDeptDayNightList",dept);
	}


	@Override
	public List<NhSDeptDay> getDeptList(NhSDeptDay dept) {
		return selectList("getDeptList",dept);
	}


	@Override
	public List<NhSDeptDay> getDeptWeekList(NhSDeptDay dept) {
		return selectList("getDeptWeekList",dept);
	}


	@Override
	public NhSDeptDay getDeptDayNight(NhSDeptDay dept) {
		return selectOne("getDeptDayNight",dept);
	}


	@Override
	public List<NhSDeptDay> getDeptWeekDays(NhSDeptDay dept) {
		return selectList("getDeptWeekDays",dept);
	}


	@Override
	public List<NhSDeptDay> getDeptHolidays(NhSDeptDay dept) {
		return selectList("getDeptHolidays",dept);
	}


	@Override
	public List<NhSDeptDay> getDeptHolidayList(NhSDeptDay dept) {
		return selectList("getDeptHolidayList",dept);
	}


	@Override
	public NhSDeptDay getDeptHolidayDayNight(NhSDeptDay dept) {
		return selectOne("getDeptHolidayDayNight",dept);
	}


	@Override
	public List<NhSDeptDay> getDeptListCompareLastYear(NhSDeptDay dept) {
		return selectList("getDeptListCompareLastYear",dept);
	}


	@Override
	public List<NhSDeptDay> getDeptHolidayListCompareLastYear(NhSDeptDay dept) {
		return selectList("getDeptHolidayListCompareLastYear",dept);
	}
}
