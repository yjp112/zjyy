package com.supconit.nhgl.analyse.energy.dept.dao.impl;

import hc.orm.AbstractBasicDaoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.analyse.energy.dept.dao.NhENDeptDayDao;
import com.supconit.nhgl.analyse.energy.dept.entities.NhENDeptDay;



@Repository
public class NhENDeptDayDaoImpl extends AbstractBasicDaoImpl<NhENDeptDay, Long> implements NhENDeptDayDao {

	private static final String NAMESPACE = NhENDeptDay.class.getName();
	

	@Override
	public List<NhENDeptDay> getDeptDayList(NhENDeptDay area) {
		return selectList("getDeptDayList",area);
	}


	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}


	@Override
	public List<NhENDeptDay> getDeptDayNightList(NhENDeptDay dept) {
		return selectList("getDeptDayNightList",dept);
	}


	@Override
	public NhENDeptDay getDeptDayNight(NhENDeptDay dept) {
		return selectOne("getDeptDayNight",dept);
	}


	@Override
	public List<NhENDeptDay> getDeptList(NhENDeptDay dept) {
		return selectList("getDeptList",dept);
	}


	@Override
	public List<NhENDeptDay> getDeptWeekList(NhENDeptDay dept) {
		return selectList("getDeptWeekList",dept);
	}


	@Override
	public List<NhENDeptDay> getDeptWeekDays(NhENDeptDay dept) {
		return selectList("getDeptWeekDays",dept);
	}


	@Override
	public List<NhENDeptDay> getDeptHolidays(NhENDeptDay dept) {
		return selectList("getDeptHolidays",dept);
	}


	@Override
	public List<NhENDeptDay> getDeptHolidayList(NhENDeptDay dept) {
		return selectList("getDeptHolidayList",dept);
	}


	@Override
	public NhENDeptDay getDeptHolidayDayNight(NhENDeptDay dept) {
		return selectOne("getDeptHolidayDayNight",dept);
	}


	@Override
	public List<NhENDeptDay> getDeptListCompareLastYear(NhENDeptDay dept) {
		return selectList("getDeptListCompareLastYear",dept);
	}
}
