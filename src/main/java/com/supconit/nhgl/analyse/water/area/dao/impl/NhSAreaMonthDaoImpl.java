package com.supconit.nhgl.analyse.water.area.dao.impl;

import hc.base.domains.Pageable;
import hc.orm.AbstractBasicDaoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.analyse.water.area.dao.NhSAreaMonthDao;
import com.supconit.nhgl.analyse.water.area.entities.NhSAreaMonth;

@Repository
public class NhSAreaMonthDaoImpl extends AbstractBasicDaoImpl<NhSAreaMonth, Long> implements NhSAreaMonthDao {

	private static final String NAMESPACE = NhSAreaMonth.class.getName();
	
	@Override
	public List<NhSAreaMonth> getAreaElectricMonth(NhSAreaMonth ems) {
		return selectList("getAreaElectricMonth", ems);
	}

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public List<NhSAreaMonth> getAreaTree(NhSAreaMonth area) {
		return selectList("getAreaTree", area);
	}

	@Override
	public Pageable<NhSAreaMonth> findByCondition(Pageable<NhSAreaMonth> pager,
			NhSAreaMonth condition) {
		return findByPager(pager, "findByCondition", "countByCondition", condition);
	}

	@Override
	public List<NhSAreaMonth> getParentAreaMonthWater(NhSAreaMonth areaWMonth) {
		return selectList("getParentAreaMonthWater", areaWMonth);
	}

	@Override
	public List<NhSAreaMonth> getAreaList(NhSAreaMonth ems) {
		return selectList("getAreaList",ems);
	}

	@Override
	public List<NhSAreaMonth> getAreaMonthList(NhSAreaMonth ems) {
		return selectList("getAreaMonthList",ems);
	}

	@Override
	public NhSAreaMonth getAreaDayNight(NhSAreaMonth ems) {
		return selectOne("getAreaDayNight",ems);
	}
	@Override
	public List<NhSAreaMonth> getUntilAreaWater(NhSAreaMonth areaWMonth) {
		return selectList("getUntilAreaWater", areaWMonth);
	}

	@Override
	public List<NhSAreaMonth> getAreaSubWater(NhSAreaMonth ems) {
		return selectList("getAreaSubWater",ems);
	}

	@Override
	public Pageable<NhSAreaMonth> findByConditionForNhgs(
			Pageable<NhSAreaMonth> pager, NhSAreaMonth condition) {
		return findByPager(pager,"findByConditionForNhgs","countByConditionForNhgs",condition);
	}

}
