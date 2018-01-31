package com.supconit.nhgl.query.collect.energy.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.query.collect.energy.dao.EnergyMeterMonthDao;
import com.supconit.nhgl.query.collect.energy.entities.EnergyMeterMonth;

import hc.base.domains.Pageable;
import hc.orm.AbstractBasicDaoImpl;

@Repository
public class EnergyMeterMonthDaoImpl extends AbstractBasicDaoImpl<EnergyMeterMonth,Long> implements EnergyMeterMonthDao{
	private static final String NAMESPACE=EnergyMeterMonth.class.getName();
	
	@Override
	public Pageable<EnergyMeterMonth> findByCondition(
			Pageable<EnergyMeterMonth> pager, EnergyMeterMonth condition) {
		return findByPager(pager,"findByCondition","countByCondition",condition);
	}

	@Override
	public Pageable<EnergyMeterMonth> findByDeptCondition(
			Pageable<EnergyMeterMonth> pager, EnergyMeterMonth condition) {
		return findByPager(pager,"findByDeptCondition","countByDeptCondition",condition);
	}

	@Override
	public EnergyMeterMonth findByConc(EnergyMeterMonth em) {
		return selectOne("findByConc", em);
	}

	@Override
	public EnergyMeterMonth findByConp(EnergyMeterMonth em) {
		return selectOne("findByConp",em);
	}


	@Override
	public List<EnergyMeterMonth> findByArea(EnergyMeterMonth em) {
		return selectList("findByArea",em);
	}

	@Override
	public List<EnergyMeterMonth> findByDeptAndFx(EnergyMeterMonth em) {
		return selectList("findByDeptAndFx",em);
	}


	@Override
	public List<EnergyMeterMonth> findArea(EnergyMeterMonth em) {
		return selectList("findArea",em);
	}

	@Override
	public List<EnergyMeterMonth> findMaxTime(EnergyMeterMonth em) {
		return selectList("findMaxTime",em);
	}

	@Override
	public int save(List<EnergyMeterMonth> em) {
		return insert("insert",em);
	}

	@Override
	public List<EnergyMeterMonth> getMonthEnergy(EnergyMeterMonth em) {
		return selectList("getMonthEnergy",em);
	}

	@Override
	public List<EnergyMeterMonth> getChildrenAreaMonthEnergy(
			EnergyMeterMonth em) {
		return selectList("getChildrenAreaMonthEnergy",em);
	}

	@Override
	public List<EnergyMeterMonth> getParentAreaMonthEnergy(
			EnergyMeterMonth em) {
		return selectList("getParentAreaMonthEnergy",em);
	}

	@Override
	public List<EnergyMeterMonth> getChildrenDeptMonthEnergy(
			EnergyMeterMonth em) {
		return selectList("getChildrenDeptMonthEnergy",em);
	}

	@Override
	public void deleteByMonth(EnergyMeterMonth emm) {
			delete("deleteByMonth",emm);
	}

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public List<EnergyMeterMonth> findDept(EnergyMeterMonth em) {
		return selectList("findDept", em);
	}

	@Override
	public List<EnergyMeterMonth> findByDept(EnergyMeterMonth em) {
		return selectList("findByDept", em);
	}

	@Override
	public List<EnergyMeterMonth> getParentDeptMonthEnergy(EnergyMeterMonth em) {
		return selectList("getParentDeptMonthEnergy",em);
	}

	@Override
	public List<EnergyMeterMonth> getChildDeptMonthEnergy(EnergyMeterMonth em) {
		return selectList("getChildDeptMonthEnergy",em);
	}

}
