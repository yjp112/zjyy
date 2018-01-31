package com.supconit.nhgl.analyse.electric.area.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.nhgl.analyse.electric.area.dao.NhDAreaDayDao;
import com.supconit.nhgl.analyse.electric.area.entities.NhDAreaDay;
import com.supconit.nhgl.analyse.electric.area.service.NhDAreaDayService;

@Service
public class NhDAreaDayServiceImpl implements NhDAreaDayService {

	@Autowired
	private NhDAreaDayDao dao;
	

	@Override
	public List<NhDAreaDay> getAreaDayList(NhDAreaDay area) {
		return dao.getAreaDayList(area);
	}


	@Override
	public List<NhDAreaDay> getAreaDayNightList(NhDAreaDay area) {
		return dao.getAreaDayNightList(area);
	}


	@Override
	public List<NhDAreaDay> getAreaList(NhDAreaDay area) {
		return dao.getAreaList(area);
	}


	@Override
	public List<NhDAreaDay> getAreaWeekList(NhDAreaDay area) {
		return dao.getAreaWeekList(area);
	}


	@Override
	public NhDAreaDay getAreaDayNight(NhDAreaDay area) {
		return dao.getAreaDayNight(area);
	}


	@Override
	public List<NhDAreaDay> getAreaWeekDays(NhDAreaDay area) {
		return dao.getAreaWeekDays(area);
	}


	@Override
	public List<NhDAreaDay> getAreaHolidays(NhDAreaDay area) {
		return dao.getAreaHolidays(area);
	}


	@Override
	public List<NhDAreaDay> getAreaHolidayList(NhDAreaDay area) {
		return dao.getAreaHolidayList(area);
	}


	@Override
	public NhDAreaDay getAreaHolidayDayNight(NhDAreaDay area) {
		return dao.getAreaHolidayDayNight(area);
	}


	@Override
	public List<NhDAreaDay> getAreaListCompareLastYear(NhDAreaDay area) {
		return dao.getAreaListCompareLastYear(area);
	}


	@Override
	public List<NhDAreaDay> getAreaHolidayListCompareLastYear(NhDAreaDay area) {
		return dao.getAreaHolidayListCompareLastYear(area);
	}
}
