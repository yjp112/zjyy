package com.supconit.nhgl.analyse.electric.area.service.impl;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.nhgl.analyse.electric.area.dao.NhAreaDao;
import com.supconit.nhgl.analyse.electric.area.entities.NhArea;
import com.supconit.nhgl.analyse.electric.area.service.NhAreaService;

@Service
public class NhAreaServiceImpl implements NhAreaService {
	
	@Autowired
	private NhAreaDao dao;
	
	@Override
	public List<NhArea> findAreaTypeByNhType(NhArea nhArea) {
		return dao.findAreaTypeByNhType(nhArea);
	}

	@Override
	public List<NhArea> getTreeById(Integer areaId) {
		return dao.getTreeById(areaId);
	}

	@Override
	public List<NhArea> getDistrictArea(NhArea areaEMonth) {
		return dao.getDistrictArea(areaEMonth);
	}

	@Override
	public Pageable<NhArea> findByCondition(Pagination<NhArea> pager, NhArea condition) {
		return dao.findByCondition(pager,condition);
	}

	@Override
	public Long findRootId() {
		return dao.findRootId();
	}

}
