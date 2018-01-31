package com.supconit.nhgl.analyse.electric.area.dao.impl;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;
import hc.orm.AbstractBasicDaoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.analyse.electric.area.dao.NhDAreaMonthDao;
import com.supconit.nhgl.analyse.electric.area.entities.NhDAreaMonth;

@Repository
public class NhDAreaMonthDaoImpl extends AbstractBasicDaoImpl<NhDAreaMonth, Long> implements NhDAreaMonthDao {

	private static final String NAMESPACE = NhDAreaMonth.class.getName();
	
	@Override
	public List<NhDAreaMonth> getAreaElectricMonth(NhDAreaMonth ems) {
		return selectList("getAreaElectricMonth", ems);
	}

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public List<NhDAreaMonth> getAreaTree(NhDAreaMonth area) {
		return selectList("getAreaTree", area);
	}

	@Override
	public Pageable<NhDAreaMonth> findByCondition(Pageable<NhDAreaMonth> pager,
			NhDAreaMonth condition) {
		return findByPager(pager, "findByCondition", "countByCondition", condition);
	}

	@Override
	public List<NhDAreaMonth> getAreaList(NhDAreaMonth area) {
		return selectList("getAreaList",area);
	}

	@Override
	public List<NhDAreaMonth> getAreaMonthList(NhDAreaMonth area) {
		return selectList("getAreaMonthList",area);
	}

	@Override
	public NhDAreaMonth getAreaDayNight(NhDAreaMonth area) {
		return selectOne("getAreaDayNight",area);
	}
	@Override
	public List<NhDAreaMonth> getParentAreaMonthElectricty(
			NhDAreaMonth areaEMonth) {
		return selectList("getParentAreaMonthElectricty", areaEMonth);
	}

	@Override
	public List<NhDAreaMonth> getUntilAreaElectricty(NhDAreaMonth areaEMonth) {
		return selectList("getUntilAreaElectricty", areaEMonth);
	}

	@Override
	public Pageable<NhDAreaMonth> findUntilByCondition(
			Pagination<NhDAreaMonth> pager, NhDAreaMonth condition) {
		return findByPager(pager, "findUntilByCondition", "countUntilByCondition", condition);
	}

	@Override
	public List<NhDAreaMonth> getAreaSubElectricty(NhDAreaMonth area) {
		return selectList("getAreaSubElectricty",area);
	}

	@Override
	public NhDAreaMonth getMonthElectricityTotal(NhDAreaMonth etm) {
		return selectOne("getMonthElectricityTotal", etm);
	}

	@Override
	public Pageable<NhDAreaMonth> findByConditionForNhgs(
			Pageable<NhDAreaMonth> pager, NhDAreaMonth condition) {
		return findByPager(pager,"findByConditionForNhgs","countByConditionForNhgs",condition);
	}

}
