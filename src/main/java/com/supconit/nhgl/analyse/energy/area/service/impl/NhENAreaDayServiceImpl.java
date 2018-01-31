package com.supconit.nhgl.analyse.energy.area.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.nhgl.analyse.energy.area.dao.NhENAreaDayDao;
import com.supconit.nhgl.analyse.energy.area.entities.NhENAreaDay;
import com.supconit.nhgl.analyse.energy.area.service.NhENAreaDayService;


@Service
public class NhENAreaDayServiceImpl implements NhENAreaDayService {

	@Autowired
	private NhENAreaDayDao dao;
	

	@Override
	public List<NhENAreaDay> getAreaDayList(NhENAreaDay area) {
		return dao.getAreaDayList(area);
	}


	@Override
	public List<NhENAreaDay> getAreaDayNightList(NhENAreaDay area) {
		return dao.getAreaDayNightList(area);
	}


	@Override
	public List<NhENAreaDay> getAreaList(NhENAreaDay area) {
		return dao.getAreaList(area);
	}


	@Override
	public NhENAreaDay getAreaDayNight(NhENAreaDay area) {
		return dao.getAreaDayNight(area);
	}


	@Override
	public List<NhENAreaDay> getAreaWeekList(NhENAreaDay area) {
		return dao.getAreaWeekList(area);
	}


	@Override
	public List<NhENAreaDay> getAreaWeekDays(NhENAreaDay area) {
		return dao.getAreaWeekDays(area);
	}


	@Override
	public List<NhENAreaDay> getAreaHolidays(NhENAreaDay area) {
		return dao.getAreaHolidays(area);
	}


	@Override
	public List<NhENAreaDay> getAreaHolidayList(NhENAreaDay area) {
		return dao.getAreaHolidayList(area);
	}


	@Override
	public NhENAreaDay getAreaHolidayDayNight(NhENAreaDay area) {
		return dao.getAreaHolidayDayNight(area);
	}


	@Override
	public List<NhENAreaDay> getAreaListCompareLastYear(NhENAreaDay area) {
		return dao.getAreaListCompareLastYear(area);
	}
}
