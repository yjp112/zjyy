package com.supconit.nhgl.analyse.gas.area.dao.impl;

import hc.base.domains.Pageable;
import hc.orm.AbstractBasicDaoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.analyse.gas.area.dao.NhQAreaMonthDao;
import com.supconit.nhgl.analyse.gas.area.entities.NhQAreaMonth;

@Repository
public class NhQAreaMonthDaoImpl extends AbstractBasicDaoImpl<NhQAreaMonth, Long> implements NhQAreaMonthDao {

	private static final String NAMESPACE = NhQAreaMonth.class.getName();
	
	@Override
	public List<NhQAreaMonth> getAreaElectricMonth(NhQAreaMonth ems) {
		return selectList("getAreaElectricMonth", ems);
	}

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public List<NhQAreaMonth> getAreaTree(NhQAreaMonth area) {
		return selectList("getAreaTree", area);
	}

	@Override
	public Pageable<NhQAreaMonth> findByCondition(Pageable<NhQAreaMonth> pager,
			NhQAreaMonth condition) {
		return findByPager(pager, "findByCondition", "countByCondition", condition);
	}

	@Override
	public List<NhQAreaMonth> getAreaList(NhQAreaMonth ems) {
		return selectList("getAreaList",ems);
	}

	@Override
	public List<NhQAreaMonth> getAreaMonthList(NhQAreaMonth ems) {
		return selectList("getAreaMonthList",ems);
	}

	@Override
	public NhQAreaMonth getAreaDayNight(NhQAreaMonth ems) {
		return selectOne("getAreaDayNight",ems);
	}

	@Override
	public List<NhQAreaMonth> getAreaSubGas(NhQAreaMonth ems) {
		return selectList("getAreaSubGas",ems);
	}

}
