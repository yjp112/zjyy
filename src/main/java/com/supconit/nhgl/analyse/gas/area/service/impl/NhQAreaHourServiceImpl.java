package com.supconit.nhgl.analyse.gas.area.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.supconit.nhgl.analyse.gas.area.dao.NhQAreaHourDao;
import com.supconit.nhgl.analyse.gas.area.entities.NhQAreaHour;
import com.supconit.nhgl.analyse.gas.area.service.NhQAreaHourService;




@Repository
public class NhQAreaHourServiceImpl implements NhQAreaHourService {
	@Autowired
	private NhQAreaHourDao	dao;

	@Override
	public List<NhQAreaHour> getAllSubGas(NhQAreaHour em){
		return dao.getAllSubGas(em);
	}

	@Override
	public List<NhQAreaHour> getDayofSubGas(NhQAreaHour em) {
		return dao.getDayofSubGas(em);
	}

	@Override
	public NhQAreaHour getSumDayofSubGas(NhQAreaHour em) {
		return dao.getSumDayofSubGas(em);
	}

	@Override
	public List<NhQAreaHour> getTwoDayofSubGas(NhQAreaHour em) {
		return dao.getTwoDayofSubGas(em);
	}

	@Override
	public List<NhQAreaHour> getAllAreaGas(NhQAreaHour em) {
		return dao.getAllAreaGas(em);
	}

	@Override
	public List<NhQAreaHour> getDayofAreaGas(NhQAreaHour em) {
		return dao.getDayofAreaGas(em);
	}

	@Override
	public NhQAreaHour getTwoDayofAreaGas(NhQAreaHour em) {
		return dao.getTwoDayofAreaGas(em);
	}
}
