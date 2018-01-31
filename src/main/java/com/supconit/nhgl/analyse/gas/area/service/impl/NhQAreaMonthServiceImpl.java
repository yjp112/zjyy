package com.supconit.nhgl.analyse.gas.area.service.impl;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.nhgl.analyse.gas.area.dao.NhQAreaMonthDao;
import com.supconit.nhgl.analyse.gas.area.entities.NhQAreaMonth;
import com.supconit.nhgl.analyse.gas.area.service.NhQAreaMonthService;

@Service
public class NhQAreaMonthServiceImpl implements NhQAreaMonthService {

	@Autowired
	private NhQAreaMonthDao dao;
	
	@Override
	public List<NhQAreaMonth> getAreaTree(NhQAreaMonth area) {
		return dao.getAreaTree(area);
	}

	@Override
	public Pageable<NhQAreaMonth> findByCondition(
			Pagination<NhQAreaMonth> pager, NhQAreaMonth condition) {
		return dao.findByCondition(pager, condition);
	}

	@Override
	public List<NhQAreaMonth> getAreaList(NhQAreaMonth ems) {
		return dao.getAreaList(ems);
	}

	@Override
	public List<NhQAreaMonth> getAreaMonthList(NhQAreaMonth ems) {
		return dao.getAreaMonthList(ems);
	}

	@Override
	public NhQAreaMonth getAreaDayNight(NhQAreaMonth ems) {
		return dao.getAreaDayNight(ems);
	}

	@Override
	public List<NhQAreaMonth> getAreaSubGas(NhQAreaMonth area) {
		return dao.getAreaSubGas(area);
	}

}
