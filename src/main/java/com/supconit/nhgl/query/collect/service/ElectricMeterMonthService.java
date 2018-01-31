package com.supconit.nhgl.query.collect.service;

import java.util.List;

import com.supconit.nhgl.query.collect.entities.ElectricMeterMonth;

import hc.base.domains.Pageable;


public interface ElectricMeterMonthService {
	
	public ElectricMeterMonth findByConc(ElectricMeterMonth em);
	public ElectricMeterMonth findByConp(ElectricMeterMonth em);
	public List<ElectricMeterMonth> findByDept(ElectricMeterMonth em) ;
	public List<ElectricMeterMonth> findByArea(ElectricMeterMonth em) ;
	public List<ElectricMeterMonth> findByDeptAndFx(ElectricMeterMonth em);
	public List<ElectricMeterMonth> findDept(ElectricMeterMonth em);
	public List<ElectricMeterMonth> findArea(ElectricMeterMonth em);
	public List<ElectricMeterMonth> findMaxTime(ElectricMeterMonth em);
	public int save(List<ElectricMeterMonth> em);
	//获取每月的耗电量
	public List<ElectricMeterMonth> getMonthElectric(ElectricMeterMonth em);
	
	public Pageable<ElectricMeterMonth> findByCondition(Pageable<ElectricMeterMonth> pager,ElectricMeterMonth condition);
	public Pageable<ElectricMeterMonth> findByDeptCondition(Pageable<ElectricMeterMonth> pager,ElectricMeterMonth condition);
	public List<ElectricMeterMonth> getParentAreaMonthElectricty(ElectricMeterMonth em);
	public List<ElectricMeterMonth> getChildrenAreaMonthElectricty(ElectricMeterMonth em);
	List<ElectricMeterMonth> getChildrenDeptMonthElectricty(ElectricMeterMonth em);
	List<ElectricMeterMonth> getMonthTotal(ElectricMeterMonth em);
	List<ElectricMeterMonth> getParentDeptMonthElectricty(ElectricMeterMonth em);
	List<ElectricMeterMonth> getChildDeptMonthElectricty(ElectricMeterMonth em);
}
