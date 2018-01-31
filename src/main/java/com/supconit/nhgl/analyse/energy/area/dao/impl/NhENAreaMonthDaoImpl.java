package com.supconit.nhgl.analyse.energy.area.dao.impl;

import hc.base.domains.Pageable;
import hc.orm.AbstractBasicDaoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.analyse.energy.area.dao.NhENAreaMonthDao;
import com.supconit.nhgl.analyse.energy.area.entities.NhENAreaMonth;

@Repository
public class NhENAreaMonthDaoImpl extends AbstractBasicDaoImpl<NhENAreaMonth, Long> implements NhENAreaMonthDao {

	private static final String NAMESPACE = NhENAreaMonth.class.getName();
	
	@Override
	public List<NhENAreaMonth> getAreaElectricMonth(NhENAreaMonth ems) {
		return selectList("getAreaElectricMonth", ems);
	}

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public List<NhENAreaMonth> getAreaTree(NhENAreaMonth area) {
		return selectList("getAreaTree", area);
	}

	@Override
	public Pageable<NhENAreaMonth> findByCondition(Pageable<NhENAreaMonth> pager,
			NhENAreaMonth condition) {
		return findByPager(pager, "findByCondition", "countByCondition", condition);
	}

	@Override
	public List<NhENAreaMonth> getAreaList(NhENAreaMonth ems) {
		return selectList("getAreaList",ems);
	}

	@Override
	public List<NhENAreaMonth> getAreaMonthList(NhENAreaMonth ems) {
		return selectList("getAreaMonthList",ems);
	}

	@Override
	public NhENAreaMonth getAreaDayNight(NhENAreaMonth ems) {
		return selectOne("getAreaDayNight",ems);
	}

	@Override
	public List<NhENAreaMonth> getAreaSubEnergy(NhENAreaMonth ems) {
		return selectList("getAreaSubEnergy",ems);
	}

}
