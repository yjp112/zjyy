package com.supconit.nhgl.analyse.water.dept.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.nhgl.analyse.water.dept.dao.NhSDeptDayDao;
import com.supconit.nhgl.analyse.water.dept.entities.NhSDeptDay;
import com.supconit.nhgl.analyse.water.dept.service.NhSDeptDayService;



@Service
public class NhSDeptDayServiceImpl implements NhSDeptDayService {

	@Autowired
	private NhSDeptDayDao dao;
	

	@Override
	public List<NhSDeptDay> getDeptDayList(NhSDeptDay area) {
		return dao.getDeptDayList(area);
	}


	@Override
	public List<NhSDeptDay> getDeptDayNightList(NhSDeptDay dept) {
		return dao.getDeptDayNightList(dept);
	}


	@Override
	public List<NhSDeptDay> getDeptList(NhSDeptDay dept) {
		return dao.getDeptList(dept);
	}


	@Override
	public List<NhSDeptDay> getDeptWeekList(NhSDeptDay dept) {
		return dao.getDeptWeekList(dept);
	}


	@Override
	public NhSDeptDay getDeptDayNight(NhSDeptDay dept) {
		return dao.getDeptDayNight(dept);
	}


	@Override
	public List<NhSDeptDay> getDeptWeekDays(NhSDeptDay dept) {
		return dao.getDeptWeekDays(dept);
	}


	@Override
	public List<NhSDeptDay> getDeptHolidays(NhSDeptDay dept) {
		return dao.getDeptHolidays(dept);
	}


	@Override
	public List<NhSDeptDay> getDeptHolidayList(NhSDeptDay dept) {
		return dao.getDeptHolidayList(dept);
	}


	@Override
	public NhSDeptDay getDeptHolidayDayNight(NhSDeptDay dept) {
		return dao.getDeptHolidayDayNight(dept);
	}


	@Override
	public List<NhSDeptDay> getDeptListCompareLastYear(NhSDeptDay dept) {
		return dao.getDeptListCompareLastYear(dept);
	}


	@Override
	public List<NhSDeptDay> getDeptHolidayListCompareLastYear(NhSDeptDay dept) {
		return dao.getDeptHolidayListCompareLastYear(dept);
	}
}
