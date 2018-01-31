package com.supconit.nhgl.query.collect.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.query.collect.dao.ElectricMeterMonthDao;
import com.supconit.nhgl.query.collect.entities.ElectricMeterMonth;

import hc.base.domains.Pageable;
import hc.orm.AbstractBasicDaoImpl;

@Repository
public class ElectricMeterMonthDaoImpl extends AbstractBasicDaoImpl<ElectricMeterMonth,Long> implements ElectricMeterMonthDao {
	private static final String	NAMESPACE=ElectricMeterMonth.class.getName();
	
	@Override
	public ElectricMeterMonth findByConc(ElectricMeterMonth em) {
		return selectOne("findByConc", em);
	}
	@Override
	public ElectricMeterMonth findByConp(ElectricMeterMonth em) {
		return selectOne("findByConp", em);
	}
	@Override
	public List<ElectricMeterMonth> findByDept(ElectricMeterMonth em) {
		return selectList("findByDept", em);
	}
	@Override
	public List<ElectricMeterMonth> findByDeptAndFx(ElectricMeterMonth em) {
		return selectList("findByDeptAndFx", em);
	}
	@Override
	public List<ElectricMeterMonth> findDept(ElectricMeterMonth em) {
		return selectList("findDept", em);
	}
	@Override
	public List<ElectricMeterMonth> findArea(ElectricMeterMonth em) {
		return selectList("findArea", em);
	}
	@Override
	public List<ElectricMeterMonth> getMonthElectric(ElectricMeterMonth em) {
		return selectList("getMonthElectric", em);
	}
	@Override
	public List<ElectricMeterMonth> findMaxTime(ElectricMeterMonth em) {
		return selectList("findMaxTime",em);
	}
	@Override
	public int save(List<ElectricMeterMonth> em) {
		return insert("insert", em);
	}
	@Override
	public List<ElectricMeterMonth> findByArea(ElectricMeterMonth em) {
		return selectList("findByArea", em);
	}
	@Override
	public Pageable<ElectricMeterMonth> findByCondition(
			Pageable<ElectricMeterMonth> pager, ElectricMeterMonth condition) {
		return findByPager(pager,"findByCondition","countByCondition",condition);
	}
	@Override
	public Pageable<ElectricMeterMonth> findByDeptCondition(
			Pageable<ElectricMeterMonth> pager, ElectricMeterMonth condition) {
		return findByPager(pager,"findByDeptCondition","countByDeptCondition",condition);
	}
	
	@Override
	public List<ElectricMeterMonth> getParentAreaMonthElectricty(
			ElectricMeterMonth em) {
		return selectList("getParentAreaMonthElectricty",em);
	}
	
	@Override
	public List<ElectricMeterMonth> getChildrenAreaMonthElectricty(
			ElectricMeterMonth em) {
		return selectList("getChildrenAreaMonthElectricty",em);
	}
	
	@Override
	public List<ElectricMeterMonth> getChildrenDeptMonthElectricty(
			ElectricMeterMonth em) {
		return selectList("getChildrenDeptMonthElectricty",em);
	}

	@Override
	public void deleteByMonth(ElectricMeterMonth emm) {
		delete("deleteByMonth",emm);
	}
	@Override
	protected String getNamespace() {
		return ElectricMeterMonth.class.getName();
	}
	@Override
	public List<ElectricMeterMonth> getMonthTotal(ElectricMeterMonth em) {
		return selectList("getMonthTotal", em);
	}
	@Override
	public List<ElectricMeterMonth> getParentDeptMonthElectricty(
			ElectricMeterMonth em) {
		return selectList("getParentDeptMonthElectricty",em);
	}
	@Override
	public List<ElectricMeterMonth> getChildDeptMonthElectricty(
			ElectricMeterMonth em) {
		return selectList("getChildDeptMonthElectricty",em);
	}
}
