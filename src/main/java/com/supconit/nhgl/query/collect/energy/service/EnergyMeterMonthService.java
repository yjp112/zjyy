package com.supconit.nhgl.query.collect.energy.service;

import java.util.List;

import com.supconit.nhgl.query.collect.energy.entities.EnergyMeterMonth;

import hc.base.domains.Pageable;


public interface EnergyMeterMonthService {
	public EnergyMeterMonth findByConc(EnergyMeterMonth em);
	public EnergyMeterMonth findByConp(EnergyMeterMonth em);
	public List<EnergyMeterMonth> findByArea(EnergyMeterMonth em) ;
	public List<EnergyMeterMonth> findByDeptAndFx(EnergyMeterMonth em);
	public List<EnergyMeterMonth> findDept(EnergyMeterMonth em);
	public List<EnergyMeterMonth> findArea(EnergyMeterMonth em);
	public List<EnergyMeterMonth> findMaxTime(EnergyMeterMonth em);
	public int save(List<EnergyMeterMonth> em);
	//获取每月的耗电量
	public List<EnergyMeterMonth> getMonthEnergy(EnergyMeterMonth em);
	
	public Pageable<EnergyMeterMonth> findByCondition(Pageable<EnergyMeterMonth> pager,EnergyMeterMonth condition);
	public Pageable<EnergyMeterMonth> findByDeptCondition(Pageable<EnergyMeterMonth> pager,EnergyMeterMonth condition);
	public List<EnergyMeterMonth> getParentAreaMonthEnergy(EnergyMeterMonth em);
	public List<EnergyMeterMonth> getChildrenAreaMonthEnergy(EnergyMeterMonth em);
	List<EnergyMeterMonth> getChildrenDeptMonthEnergy(EnergyMeterMonth em);
	public List<EnergyMeterMonth> findByDept(EnergyMeterMonth em);
	List<EnergyMeterMonth> getParentDeptMonthEnergy(EnergyMeterMonth em);
	List<EnergyMeterMonth> getChildDeptMonthEnergy(EnergyMeterMonth em);
}
