package com.supconit.nhgl.analyse.electric.dept.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.supconit.nhgl.analyse.electric.dept.dao.NhDDeptHourDao;
import com.supconit.nhgl.analyse.electric.dept.entities.NhDDeptHour;
import com.supconit.nhgl.analyse.electric.dept.service.NhDDeptHourService;


@Repository
public class NhDDeptHourServiceImpl implements NhDDeptHourService {
	@Autowired
	private NhDDeptHourDao	dao;

	@Override
	public List<NhDDeptHour> getAllSubElectricty(NhDDeptHour em){
		return dao.getAllSubElectricty(em);
	}

	@Override
	public List<NhDDeptHour> getDayofSubElectricty(NhDDeptHour em) {
		return dao.getDayofSubElectricty(em);
	}


	@Override
	public NhDDeptHour getTwoDayofSubElectricty(NhDDeptHour em) {
		return dao.getTwoDayofSubElectricty(em);
	}

	@Override
	public List<NhDDeptHour> getDayofDeptElectricty(NhDDeptHour em) {
		return dao.getDayofDeptElectricty(em);
	}

	@Override
	public List<NhDDeptHour> getSubInfoDetailByDept(NhDDeptHour elecDept) {
		return dao.getSubInfoDetailByDept(elecDept);
	}
}
