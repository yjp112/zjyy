package com.supconit.nhgl.analyse.energy.dept.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.nhgl.analyse.energy.dept.dao.NhENDeptDayDao;
import com.supconit.nhgl.analyse.energy.dept.entities.NhENDeptDay;
import com.supconit.nhgl.analyse.energy.dept.service.NhENDeptDayService;



@Service
public class NhENDeptDayServiceImpl implements NhENDeptDayService {

	@Autowired
	private NhENDeptDayDao dao;
	

	@Override
	public List<NhENDeptDay> getDeptDayList(NhENDeptDay area) {
		return dao.getDeptDayList(area);
	}


	@Override
	public List<NhENDeptDay> getDeptDayNightList(NhENDeptDay dept) {
		return dao.getDeptDayNightList(dept);
	}


	@Override
	public NhENDeptDay getDeptDayNight(NhENDeptDay dept) {
		return dao.getDeptDayNight(dept);
	}


	@Override
	public List<NhENDeptDay> getDeptList(NhENDeptDay dept) {
		return dao.getDeptList(dept);
	}


	@Override
	public List<NhENDeptDay> getDeptWeekList(NhENDeptDay dept) {
		return dao.getDeptWeekList(dept);
	}


	@Override
	public List<NhENDeptDay> getDeptWeekDays(NhENDeptDay dept) {
		return dao.getDeptWeekDays(dept);
	}


	@Override
	public List<NhENDeptDay> getDeptHolidays(NhENDeptDay dept) {
		return dao.getDeptHolidays(dept);
	}


	@Override
	public List<NhENDeptDay> getDeptHolidayList(NhENDeptDay dept) {
		return dao.getDeptHolidayList(dept);
	}


	@Override
	public NhENDeptDay getDeptHolidayDayNight(NhENDeptDay dept) {
		return dao.getDeptHolidayDayNight(dept);
	}


	@Override
	public List<NhENDeptDay> getDeptListCompareLastYear(NhENDeptDay dept) {
		return dao.getDeptListCompareLastYear(dept);
	}
}
