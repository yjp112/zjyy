package com.supconit.nhgl.analyse.gas.area.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.nhgl.analyse.gas.area.dao.NhQAreaDayDao;
import com.supconit.nhgl.analyse.gas.area.entities.NhQAreaDay;
import com.supconit.nhgl.analyse.gas.area.service.NhQAreaDayService;


@Service
public class NhQAreaDayServiceImpl implements NhQAreaDayService {

	@Autowired
	private NhQAreaDayDao dao;
	

	@Override
	public List<NhQAreaDay> getAreaDayList(NhQAreaDay area) {
		return dao.getAreaDayList(area);
	}


	@Override
	public List<NhQAreaDay> getAreaDayNightList(NhQAreaDay area) {
		return dao.getAreaDayNightList(area);
	}


	@Override
	public List<NhQAreaDay> getAreaList(NhQAreaDay area) {
		return dao.getAreaList(area);
	}


	@Override
	public NhQAreaDay getAreaDayNight(NhQAreaDay area) {
		return dao.getAreaDayNight(area);
	}


	@Override
	public List<NhQAreaDay> getAreaWeekList(NhQAreaDay area) {
		return dao.getAreaWeekList(area);
	}


	@Override
	public List<NhQAreaDay> getAreaWeekDays(NhQAreaDay area) {
		return dao.getAreaWeekDays(area);
	}


	@Override
	public List<NhQAreaDay> getAreaHolidays(NhQAreaDay area) {
		return dao.getAreaHolidays(area);
	}


	@Override
	public List<NhQAreaDay> getAreaHolidayList(NhQAreaDay area) {
		return dao.getAreaHolidayList(area);
	}


	@Override
	public NhQAreaDay getAreaHolidayDayNight(NhQAreaDay area) {
		return dao.getAreaHolidayDayNight(area);
	}


	@Override
	public List<NhQAreaDay> getAreaListCompareLastYear(NhQAreaDay area) {
		return dao.getAreaListCompareLastYear(area);
	}
}
