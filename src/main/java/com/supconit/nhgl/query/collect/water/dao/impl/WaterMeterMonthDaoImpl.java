package com.supconit.nhgl.query.collect.water.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.query.collect.water.dao.WaterMeterMonthDao;
import com.supconit.nhgl.query.collect.water.entities.WaterMeterMonth;

import hc.base.domains.Pageable;
import hc.orm.AbstractBasicDaoImpl;

@Repository
public class WaterMeterMonthDaoImpl extends AbstractBasicDaoImpl<WaterMeterMonth,Long> implements WaterMeterMonthDao {
	private static final String	NAMESPACE=WaterMeterMonth.class.getName();
	
	@Override
	public WaterMeterMonth findByConc(WaterMeterMonth em) {
		return selectOne("findByConc", em);
	}
	@Override
	public WaterMeterMonth findByConp(WaterMeterMonth em) {
		return selectOne("findByConp", em);
	}
	@Override
	public List<WaterMeterMonth> findByDept(WaterMeterMonth em) {
		return selectList("findByDept", em);
	}
	@Override
	public List<WaterMeterMonth> findByDeptAndFx(WaterMeterMonth em) {
		return selectList("findByDeptAndFx", em);
	}
	@Override
	public List<WaterMeterMonth> findDept(WaterMeterMonth em) {
		return selectList("findDept", em);
	}
	@Override
	public List<WaterMeterMonth> findArea(WaterMeterMonth em) {
		return selectList("findArea", em);
	}
	@Override
	public List<WaterMeterMonth> getMonthWater(WaterMeterMonth em) {
		return selectList("getMonthWater", em);
	}
	@Override
	public List<WaterMeterMonth> findMaxTime(WaterMeterMonth em) {
		return selectList("findMaxTime",em);
	}
	@Override
	public int save(List<WaterMeterMonth> em) {
		return insert("insert", em);
	}
	@Override
	public List<WaterMeterMonth> findByArea(WaterMeterMonth em) {
		return selectList("findByArea", em);
	}
	@Override
	public Pageable<WaterMeterMonth> findByCondition(
			Pageable<WaterMeterMonth> pager, WaterMeterMonth condition) {
		return findByPager(pager,"findByCondition","countByCondition",condition);
	}
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	@Override
	public Pageable<WaterMeterMonth> findByDeptCondition(
			Pageable<WaterMeterMonth> pager, WaterMeterMonth condition) {
		return findByPager(pager,"findByDeptCondition","countByDeptCondition",condition);
	}
	
	@Override
	public List<WaterMeterMonth> getParentAreaMonthWater(
			WaterMeterMonth em) {
		return selectList("getParentAreaMonthWater",em);
	}
	
	@Override
	public List<WaterMeterMonth> getChildrenAreaMonthWater(
			WaterMeterMonth em) {
		return selectList("getChildrenAreaMonthWater",em);
	}
	
	@Override
	public List<WaterMeterMonth> getChildrenDeptMonthWater(
			WaterMeterMonth em) {
		return selectList("getChildrenDeptMonthWater",em);
	}
	@Override
	public List<WaterMeterMonth> getSubWaterMonthChildren(WaterMeterMonth emm) {
		return selectList("getSubWaterMonthChildren",emm);
	}
	@Override
	public List<WaterMeterMonth> getSubWaterMonthParent(WaterMeterMonth emm) {
		return selectList("getSubWaterMonthParent",emm);
	}

	@Override
	public void deleteByMonthKey(WaterMeterMonth emm){
		delete("deleteByMonthKey", emm);
	}
	@Override
	public List<WaterMeterMonth> getMonthTotal(WaterMeterMonth emm) {
		return selectList("getMonthTotal", emm);
	}
	@Override
	public List<WaterMeterMonth> getParentDeptMonthWater(WaterMeterMonth em) {
		return selectList("getParentDeptMonthWater",em);
	}
	@Override
	public List<WaterMeterMonth> getChildDeptMonthWater(WaterMeterMonth em) {
		return selectList("getChildDeptMonthWater",em);
	}
}
