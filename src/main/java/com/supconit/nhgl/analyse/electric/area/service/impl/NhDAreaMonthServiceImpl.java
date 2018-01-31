package com.supconit.nhgl.analyse.electric.area.service.impl;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.nhgl.analyse.electric.area.dao.NhDAreaMonthDao;
import com.supconit.nhgl.analyse.electric.area.entities.NhDAreaMonth;
import com.supconit.nhgl.analyse.electric.area.service.NhDAreaMonthService;

@Service
public class NhDAreaMonthServiceImpl implements NhDAreaMonthService {

	@Autowired
	private NhDAreaMonthDao dao;
	
	@Override
	public List<NhDAreaMonth> getAreaTree(NhDAreaMonth area) {
		return dao.getAreaTree(area);
	}

	@Override
	public Pageable<NhDAreaMonth> findByCondition(
			Pagination<NhDAreaMonth> pager, NhDAreaMonth condition) {
		return dao.findByCondition(pager, condition);
	}

	@Override
	public List<NhDAreaMonth> getAreaList(NhDAreaMonth area) {
		return dao.getAreaList(area);
	}

	@Override
	public List<NhDAreaMonth> getAreaMonthList(NhDAreaMonth area) {
		return dao.getAreaMonthList(area);
	}

	@Override
	public NhDAreaMonth getAreaDayNight(NhDAreaMonth area) {
		return dao.getAreaDayNight(area);
	}
	public List<NhDAreaMonth> getParentAreaMonthElectricty(
			NhDAreaMonth areaEMonth) {
		return dao.getParentAreaMonthElectricty(areaEMonth);
	}

	@Override
	public List<NhDAreaMonth> getUntilAreaElectricty(NhDAreaMonth areaEMonth) {
		return dao.getUntilAreaElectricty(areaEMonth);
	}

	@Override
	public Pageable<NhDAreaMonth> findUntilByCondition(
			Pagination<NhDAreaMonth> pager, NhDAreaMonth condition) {
		return dao.findUntilByCondition(pager, condition);
	}

	@Override
	public List<NhDAreaMonth> getAreaSubElectricty(NhDAreaMonth area) {
		return dao.getAreaSubElectricty(area);
	}

	@Override
	public NhDAreaMonth getMonthElectricityTotal(NhDAreaMonth etm) {
		return dao.getMonthElectricityTotal(etm);
	}

	@Override
	public Pageable<NhDAreaMonth> findByConditionForNhgs(
			Pageable<NhDAreaMonth> pager, NhDAreaMonth condition) {
		return dao.findByConditionForNhgs(pager, condition);
	}

}
