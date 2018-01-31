package com.supconit.nhgl.query.collect.dao;

import java.util.List;

import com.supconit.nhgl.query.collect.entities.ElectricMeterMonth;

import hc.base.domains.Pageable;


public interface ElectricMeterMonthDao {

	public Pageable<ElectricMeterMonth> findByCondition(Pageable<ElectricMeterMonth> pager,ElectricMeterMonth condition);
	public Pageable<ElectricMeterMonth> findByDeptCondition(Pageable<ElectricMeterMonth> pager,ElectricMeterMonth condition);
	public ElectricMeterMonth findByConc(ElectricMeterMonth em);
	public ElectricMeterMonth findByConp(ElectricMeterMonth em);
	public List<ElectricMeterMonth> findByDept(ElectricMeterMonth em);
	public List<ElectricMeterMonth> findByArea(ElectricMeterMonth em);
	public List<ElectricMeterMonth> findByDeptAndFx(ElectricMeterMonth em);
	public List<ElectricMeterMonth>  findDept(ElectricMeterMonth em);
	public List<ElectricMeterMonth>  findArea(ElectricMeterMonth em);
	public List<ElectricMeterMonth> findMaxTime(ElectricMeterMonth em);
	public int save(List<ElectricMeterMonth> em);
	//获取每个月的耗电量王海波
	public List<ElectricMeterMonth> getMonthElectric(ElectricMeterMonth em);
	List<ElectricMeterMonth> getChildrenAreaMonthElectricty(
			ElectricMeterMonth em);
	List<ElectricMeterMonth> getParentAreaMonthElectricty(ElectricMeterMonth em);
	List<ElectricMeterMonth> getChildrenDeptMonthElectricty(ElectricMeterMonth em);
	List<ElectricMeterMonth> getParentDeptMonthElectricty(ElectricMeterMonth em);
	List<ElectricMeterMonth> getChildDeptMonthElectricty(ElectricMeterMonth em);

	void deleteByMonth(ElectricMeterMonth emm);
	public List<ElectricMeterMonth> getMonthTotal(ElectricMeterMonth em);
}
