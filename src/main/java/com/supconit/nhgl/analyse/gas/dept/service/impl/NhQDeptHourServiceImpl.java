package com.supconit.nhgl.analyse.gas.dept.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.supconit.nhgl.analyse.gas.dept.dao.NhQDeptHourDao;
import com.supconit.nhgl.analyse.gas.dept.entities.NhQDeptHour;
import com.supconit.nhgl.analyse.gas.dept.service.NhQDeptHourService;



@Repository
public class NhQDeptHourServiceImpl implements NhQDeptHourService {
	@Autowired
	private NhQDeptHourDao	dao;

	@Override
	public List<NhQDeptHour> getAllSubGas(NhQDeptHour em){
		return dao.getAllSubGas(em);
	}

	@Override
	public List<NhQDeptHour> getDayofSubGas(NhQDeptHour em) {
		return dao.getDayofSubGas(em);
	}


	@Override
	public NhQDeptHour getTwoDayofSubGas(NhQDeptHour em) {
		return dao.getTwoDayofSubGas(em);
	}

	@Override
	public List<NhQDeptHour> getDayofDeptGas(NhQDeptHour em) {
		return dao.getDayofDeptGas(em);
	}
}
