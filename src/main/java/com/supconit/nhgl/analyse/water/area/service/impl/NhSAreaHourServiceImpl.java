package com.supconit.nhgl.analyse.water.area.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.supconit.nhgl.analyse.water.area.dao.NhSAreaHourDao;
import com.supconit.nhgl.analyse.water.area.entities.NhSAreaHour;
import com.supconit.nhgl.analyse.water.area.service.NhSAreaHourService;




@Repository
public class NhSAreaHourServiceImpl implements NhSAreaHourService {
	@Autowired
	private NhSAreaHourDao	dao;

	@Override
	public List<NhSAreaHour> getAllSubWater(NhSAreaHour em){
		return dao.getAllSubWater(em);
	}

	@Override
	public List<NhSAreaHour> getDayofSubWater(NhSAreaHour em) {
		return dao.getDayofSubWater(em);
	}

	@Override
	public List<NhSAreaHour> getTwoDayofSubWater(NhSAreaHour em) {
		return dao.getTwoDayofSubWater(em);
	}

	@Override
	public NhSAreaHour getSumDayofSubWater(NhSAreaHour em) {
		return dao.getSumDayofSubWater(em);
	}

	@Override
	public List<NhSAreaHour> getAllAreaWater(NhSAreaHour em) {
		return dao.getAllAreaWater(em);
	}

	@Override
	public List<NhSAreaHour> getDayofAreaWater(NhSAreaHour em) {
		return dao.getDayofAreaWater(em);
	}

	@Override
	public NhSAreaHour getTwoDayofAreaWater(NhSAreaHour em) {
		return dao.getTwoDayofAreaWater(em);
	}
}
