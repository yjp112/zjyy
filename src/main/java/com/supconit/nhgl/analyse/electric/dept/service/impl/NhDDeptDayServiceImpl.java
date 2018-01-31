package com.supconit.nhgl.analyse.electric.dept.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.nhgl.analyse.electric.dept.dao.NhDDeptDayDao;
import com.supconit.nhgl.analyse.electric.dept.entities.NhDDeptDay;
import com.supconit.nhgl.analyse.electric.dept.service.NhDDeptDayService;


@Service
public class NhDDeptDayServiceImpl implements NhDDeptDayService {

	@Autowired
	private NhDDeptDayDao dao;
	

	@Override
	public List<NhDDeptDay> getDeptDayList(NhDDeptDay area) {
		return dao.getDeptDayList(area);
	}


	@Override
	public List<NhDDeptDay> getDeptDayNightList(NhDDeptDay dept) {
		return dao.getDeptDayNightList(dept);
	}


	@Override
	public List<NhDDeptDay> getDeptList(NhDDeptDay dept) {
		return dao.getDeptList(dept);
	}


	@Override
	public List<NhDDeptDay> getDeptWeekList(NhDDeptDay dept) {
		return dao.getDeptWeekList(dept);
	}


	@Override
	public NhDDeptDay getDeptDayNight(NhDDeptDay dept) {
		return dao.getDeptDayNight(dept);
	}


	@Override
	public List<NhDDeptDay> getDeptWeekDays(NhDDeptDay dept) {
		return dao.getDeptWeekDays(dept);
	}


	@Override
	public List<NhDDeptDay> getDeptHolidays(NhDDeptDay dept) {
		return dao.getDeptHolidays(dept);
	}


	@Override
	public List<NhDDeptDay> getDeptHolidayList(NhDDeptDay dept) {
		return dao.getDeptHolidayList(dept);
	}


	@Override
	public NhDDeptDay getDeptHolidayDayNight(NhDDeptDay dept) {
		return dao.getDeptHolidayDayNight(dept);
	}


	@Override
	public List<NhDDeptDay> getDeptListCompareLastYear(NhDDeptDay dept) {
		return dao.getDeptListCompareLastYear(dept);
	}


	@Override
	public List<NhDDeptDay> getDeptHolidayListCompareLastYear(NhDDeptDay dept) {
		return dao.getDeptHolidayListCompareLastYear(dept);
	}
}
