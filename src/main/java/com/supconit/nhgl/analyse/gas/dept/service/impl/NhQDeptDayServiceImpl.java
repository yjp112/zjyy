package com.supconit.nhgl.analyse.gas.dept.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.nhgl.analyse.gas.dept.dao.NhQDeptDayDao;
import com.supconit.nhgl.analyse.gas.dept.entities.NhQDeptDay;
import com.supconit.nhgl.analyse.gas.dept.service.NhQDeptDayService;



@Service
public class NhQDeptDayServiceImpl implements NhQDeptDayService {

	@Autowired
	private NhQDeptDayDao dao;
	

	@Override
	public List<NhQDeptDay> getDeptDayList(NhQDeptDay area) {
		return dao.getDeptDayList(area);
	}


	@Override
	public List<NhQDeptDay> getDeptDayNightList(NhQDeptDay dept) {
		return dao.getDeptDayNightList(dept);
	}


	@Override
	public NhQDeptDay getDeptDayNight(NhQDeptDay dept) {
		return dao.getDeptDayNight(dept);
	}


	@Override
	public List<NhQDeptDay> getDeptList(NhQDeptDay dept) {
		return dao.getDeptList(dept);
	}


	@Override
	public List<NhQDeptDay> getDeptWeekList(NhQDeptDay dept) {
		return dao.getDeptWeekList(dept);
	}


	@Override
	public List<NhQDeptDay> getDeptWeekDays(NhQDeptDay dept) {
		return dao.getDeptWeekDays(dept);
	}


	@Override
	public List<NhQDeptDay> getDeptHolidays(NhQDeptDay dept) {
		return dao.getDeptHolidays(dept);
	}


	@Override
	public List<NhQDeptDay> getDeptHolidayList(NhQDeptDay dept) {
		return dao.getDeptHolidayList(dept);
	}


	@Override
	public NhQDeptDay getDeptHolidayDayNight(NhQDeptDay dept) {
		return dao.getDeptHolidayDayNight(dept);
	}


	@Override
	public List<NhQDeptDay> getDeptListCompareLastYear(NhQDeptDay area) {
		return dao.getDeptListCompareLastYear(area);
	}
}
