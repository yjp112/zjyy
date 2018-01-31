package com.supconit.nhgl.analyse.water.area.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.nhgl.analyse.water.area.dao.NhSAreaDayDao;
import com.supconit.nhgl.analyse.water.area.entities.NhSAreaDay;
import com.supconit.nhgl.analyse.water.area.service.NhSAreaDayService;


@Service
public class NhSAreaDayServiceImpl implements NhSAreaDayService {

	@Autowired
	private NhSAreaDayDao dao;
	

	@Override
	public List<NhSAreaDay> getAreaDayList(NhSAreaDay area) {
		return dao.getAreaDayList(area);
	}


	@Override
	public List<NhSAreaDay> getAreaDayNightList(NhSAreaDay area) {
		return dao.getAreaDayNightList(area);
	}


	@Override
	public List<NhSAreaDay> getAreaList(NhSAreaDay area) {
		return dao.getAreaList(area);
	}


	@Override
	public List<NhSAreaDay> getAreaWeekList(NhSAreaDay area) {
		return dao.getAreaWeekList(area);
	}


	@Override
	public NhSAreaDay getAreaDayNight(NhSAreaDay area) {
		return dao.getAreaDayNight(area);
	}


	@Override
	public List<NhSAreaDay> getAreaWeekDays(NhSAreaDay area) {
		return dao.getAreaWeekDays(area);
	}


	@Override
	public List<NhSAreaDay> getAreaHolidays(NhSAreaDay area) {
		return dao.getAreaHolidays(area);
	}


	@Override
	public List<NhSAreaDay> getAreaHolidayList(NhSAreaDay area) {
		return dao.getAreaHolidayList(area);
	}


	@Override
	public NhSAreaDay getAreaHolidayDayNight(NhSAreaDay area) {
		return dao.getAreaHolidayDayNight(area);
	}


	@Override
	public List<NhSAreaDay> getAreaListCompareLastYear(NhSAreaDay area) {
		return dao.getAreaListCompareLastYear(area);
	}


	@Override
	public List<NhSAreaDay> getAreaHolidayListCompareLastYear(NhSAreaDay area) {
		return dao.getAreaHolidayListCompareLastYear(area);
	}
}
