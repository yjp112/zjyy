package com.supconit.nhgl.query.collect.water.dao;

import java.util.List;

import com.supconit.nhgl.query.collect.water.entities.WaterMeterMonth;

import hc.base.domains.Pageable;


public interface WaterMeterMonthDao {

	public Pageable<WaterMeterMonth> findByCondition(Pageable<WaterMeterMonth> pager,WaterMeterMonth condition);
	public Pageable<WaterMeterMonth> findByDeptCondition(Pageable<WaterMeterMonth> pager,WaterMeterMonth condition);
	public WaterMeterMonth findByConc(WaterMeterMonth em);
	public WaterMeterMonth findByConp(WaterMeterMonth em);
	public List<WaterMeterMonth> findByDept(WaterMeterMonth em);
	public List<WaterMeterMonth> findByArea(WaterMeterMonth em);
	public List<WaterMeterMonth> findByDeptAndFx(WaterMeterMonth em);
	public List<WaterMeterMonth>  findDept(WaterMeterMonth em);
	public List<WaterMeterMonth>  findArea(WaterMeterMonth em);
	public List<WaterMeterMonth> findMaxTime(WaterMeterMonth em);
	public int save(List<WaterMeterMonth> em);
	//获取每个月的耗电量王海波
	public List<WaterMeterMonth> getMonthWater(WaterMeterMonth em);
	List<WaterMeterMonth> getChildrenAreaMonthWater(
			WaterMeterMonth em);
	List<WaterMeterMonth> getParentAreaMonthWater(WaterMeterMonth em);
	List<WaterMeterMonth> getChildrenDeptMonthWater(
			WaterMeterMonth em);
	List<WaterMeterMonth> getSubWaterMonthChildren(WaterMeterMonth emm);
	List<WaterMeterMonth> getSubWaterMonthParent(WaterMeterMonth emm);

	void deleteByMonthKey(WaterMeterMonth emm);
	public List<WaterMeterMonth> getMonthTotal(WaterMeterMonth emm);
	List<WaterMeterMonth> getParentDeptMonthWater(WaterMeterMonth em);
	List<WaterMeterMonth> getChildDeptMonthWater(WaterMeterMonth em);
}
