package com.supconit.nhgl.analyse.water.dept.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.supconit.nhgl.analyse.water.dept.dao.NhSDeptHourDao;
import com.supconit.nhgl.analyse.water.dept.entities.NhSDeptHour;
import com.supconit.nhgl.analyse.water.dept.service.NhSDeptHourService;



@Repository
public class NhSDeptHourServiceImpl implements NhSDeptHourService {
	@Autowired
	private NhSDeptHourDao	dao;

	@Override
	public List<NhSDeptHour> getAllSubWater(NhSDeptHour em){
		return dao.getAllSubWater(em);
	}

	@Override
	public List<NhSDeptHour> getDayofSubWater(NhSDeptHour em) {
		return dao.getDayofSubWater(em);
	}


	@Override
	public NhSDeptHour getTwoDayofSubWater(NhSDeptHour em) {
		return dao.getTwoDayofSubWater(em);
	}

	@Override
	public List<NhSDeptHour> getDayofDeptWater(NhSDeptHour em) {
		return dao.getDayofDeptWater(em);
	}
}
