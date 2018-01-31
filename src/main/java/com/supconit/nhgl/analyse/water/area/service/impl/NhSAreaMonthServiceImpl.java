package com.supconit.nhgl.analyse.water.area.service.impl;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.nhgl.analyse.water.area.dao.NhSAreaMonthDao;
import com.supconit.nhgl.analyse.water.area.entities.NhSAreaMonth;
import com.supconit.nhgl.analyse.water.area.service.NhSAreaMonthService;

@Service
public class NhSAreaMonthServiceImpl implements NhSAreaMonthService {

	@Autowired
	private NhSAreaMonthDao dao;
	
	@Override
	public List<NhSAreaMonth> getAreaTree(NhSAreaMonth area) {
		return dao.getAreaTree(area);
	}

	@Override
	public Pageable<NhSAreaMonth> findByCondition(
			Pagination<NhSAreaMonth> pager, NhSAreaMonth condition) {
		return dao.findByCondition(pager, condition);
	}

	@Override
	public List<NhSAreaMonth> getParentAreaMonthWater(NhSAreaMonth areaWMonth) {
		return dao.getParentAreaMonthWater(areaWMonth);
	}

	@Override
	public List<NhSAreaMonth> getAreaList(NhSAreaMonth ems) {
		return dao.getAreaList(ems);
	}

	@Override
	public List<NhSAreaMonth> getAreaMonthList(NhSAreaMonth ems) {
		return dao.getAreaMonthList(ems);
	}

	@Override
	public NhSAreaMonth getAreaDayNight(NhSAreaMonth ems) {
		return dao.getAreaDayNight(ems);
	}
	@Override
	public List<NhSAreaMonth> getUntilAreaWater(NhSAreaMonth areaWMonth) {
		return dao.getUntilAreaWater(areaWMonth);
	}

	@Override
	public List<NhSAreaMonth> getAreaSubWater(NhSAreaMonth area) {
		return dao.getAreaSubWater(area);
	}

	@Override
	public Pageable<NhSAreaMonth> findByConditionForNhgs(
			Pageable<NhSAreaMonth> pager, NhSAreaMonth condition) {
		return dao.findByConditionForNhgs(pager, condition);
	}

}
