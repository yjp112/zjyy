package com.supconit.nhgl.analyse.energy.area.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.supconit.nhgl.analyse.energy.area.dao.NhENAreaHourDao;
import com.supconit.nhgl.analyse.energy.area.entities.NhENAreaHour;
import com.supconit.nhgl.analyse.energy.area.service.NhENAreaHourService;



@Repository
public class NhENAreaHourServiceImpl implements NhENAreaHourService {
	@Autowired
	private NhENAreaHourDao	dao;

	@Override
	public List<NhENAreaHour> getAllSubEnergy(NhENAreaHour em){
		return dao.getAllSubEnergy(em);
	}

	@Override
	public List<NhENAreaHour> getDayofSubEnergy(NhENAreaHour em) {
		return dao.getDayofSubEnergy(em);
	}

	@Override
	public NhENAreaHour getSumDayofSubEnergy(NhENAreaHour em) {
		return dao.getSumDayofSubEnergy(em);
	}

	@Override
	public List<NhENAreaHour> getTwoDayofSubEnergy(NhENAreaHour em) {
		return dao.getTwoDayofSubEnergy(em);
	}

	@Override
	public List<NhENAreaHour> getAllAreaEnergy(NhENAreaHour em) {
		return dao.getAllAreaEnergy(em);
	}

	@Override
	public List<NhENAreaHour> getDayofAreaEnergy(NhENAreaHour em) {
		return dao.getDayofAreaEnergy(em);
	}

	@Override
	public NhENAreaHour getTwoDayofAreaEnergy(NhENAreaHour em) {
		return dao.getTwoDayofAreaEnergy(em);
	}
}
