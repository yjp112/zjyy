package com.supconit.nhgl.analyse.energy.dept.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.supconit.nhgl.analyse.energy.dept.dao.NhENDeptHourDao;
import com.supconit.nhgl.analyse.energy.dept.entities.NhENDeptHour;
import com.supconit.nhgl.analyse.energy.dept.service.NhENDeptHourService;



@Repository
public class NhENDeptHourServiceImpl implements NhENDeptHourService {
	@Autowired
	private NhENDeptHourDao	dao;

	@Override
	public List<NhENDeptHour> getAllSubEnergy(NhENDeptHour em){
		return dao.getAllSubEnergy(em);
	}

	@Override
	public List<NhENDeptHour> getDayofSubEnergy(NhENDeptHour em) {
		return dao.getDayofSubEnergy(em);
	}


	@Override
	public NhENDeptHour getTwoDayofSubEnergy(NhENDeptHour em) {
		return dao.getTwoDayofSubEnergy(em);
	}

	@Override
	public List<NhENDeptHour> getDayofDeptEnergy(NhENDeptHour em) {
		return dao.getDayofDeptEnergy(em);
	}
}
