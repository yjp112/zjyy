package com.supconit.nhgl.analyse.energy.area.service.impl;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.nhgl.analyse.energy.area.dao.NhENAreaMonthDao;
import com.supconit.nhgl.analyse.energy.area.entities.NhENAreaMonth;
import com.supconit.nhgl.analyse.energy.area.service.NhENAreaMonthService;

@Service
public class NhENAreaMonthServiceImpl implements NhENAreaMonthService {

	@Autowired
	private NhENAreaMonthDao dao;
	
	@Override
	public List<NhENAreaMonth> getAreaTree(NhENAreaMonth area) {
		return dao.getAreaTree(area);
	}

	@Override
	public Pageable<NhENAreaMonth> findByCondition(
			Pagination<NhENAreaMonth> pager, NhENAreaMonth condition) {
		return dao.findByCondition(pager, condition);
	}

	@Override
	public List<NhENAreaMonth> getAreaList(NhENAreaMonth ems) {
		return dao.getAreaList(ems);
	}

	@Override
	public List<NhENAreaMonth> getAreaMonthList(NhENAreaMonth ems) {
		return dao.getAreaMonthList(ems);
	}

	@Override
	public NhENAreaMonth getAreaDayNight(NhENAreaMonth ems) {
		return dao.getAreaDayNight(ems);
	}

	@Override
	public List<NhENAreaMonth> getAreaSubEnergy(NhENAreaMonth ems) {
		return dao.getAreaSubEnergy(ems);
	}

}
