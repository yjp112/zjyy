package com.supconit.nhgl.analyse.electric.area.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.supconit.nhgl.analyse.electric.area.dao.NhDAreaHourDao;
import com.supconit.nhgl.analyse.electric.area.entities.NhDAreaHour;
import com.supconit.nhgl.analyse.electric.area.service.NhDAreaHourService;



@Repository
public class NhDAreaHourServiceImpl implements NhDAreaHourService {
	@Autowired
	private NhDAreaHourDao	dao;

	@Override
	public List<NhDAreaHour> getAllSubElectricty(NhDAreaHour em){
		return dao.getAllSubElectricty(em);
	}

	@Override
	public List<NhDAreaHour> getDayofSubElectricty(NhDAreaHour em) {
		return dao.getDayofSubElectricty(em);
	}

	@Override
	public NhDAreaHour getSumDayofSubElectricty(NhDAreaHour em) {
		return dao.getSumDayofSubElectricty(em);
	}

	@Override
	public NhDAreaHour getTwoDayofSubElectricty(NhDAreaHour em) {
		return dao.getTwoDayofSubElectricty(em);
	}
	@Override
	public List<NhDAreaHour> getAllAreaElectricty(NhDAreaHour em){
		return dao.getAllAreaElectricty(em);
	}

	@Override
	public List<NhDAreaHour> getDayofAreaElectricty(NhDAreaHour em) {
		return dao.getDayofAreaElectricty(em);
	}

	@Override
	public NhDAreaHour getTwoDayofAreaElectricty(NhDAreaHour em) {
		return dao.getTwoDayofAreaElectricty(em);
	}

	@Override
	public List<NhDAreaHour> getSubInfoDetailByArea(NhDAreaHour elec) {
		return dao.getSubInfoDetailByArea(elec);
	}

	@Override
	public List<NhDAreaHour> getAreaDetail(NhDAreaHour elec) {
		return dao.getAreaDetail(elec);
	}

	@Override
	public List<NhDAreaHour> getDayElectric(NhDAreaHour ectm) {
		return dao.getDayElectric(ectm);
	}
}
