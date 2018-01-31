package com.supconit.nhgl.query.collect.gas.service;

import java.util.List;

import com.supconit.nhgl.query.collect.gas.entities.GasMeterMonth;

import hc.base.domains.Pageable;


public interface GasMeterMonthService {
	public GasMeterMonth findByConc(GasMeterMonth em);
	public GasMeterMonth findByConp(GasMeterMonth em);
	public List<GasMeterMonth> findByArea(GasMeterMonth em) ;
	public List<GasMeterMonth> findByDeptAndFx(GasMeterMonth em);
	public List<GasMeterMonth> findArea(GasMeterMonth em);
	public List<GasMeterMonth> findMaxTime(GasMeterMonth em);
	public int save(List<GasMeterMonth> em);
	//获取每月的耗电量
	public List<GasMeterMonth> getMonthGas(GasMeterMonth em);
	
	public Pageable<GasMeterMonth> findByCondition(Pageable<GasMeterMonth> pager,GasMeterMonth condition);
	public Pageable<GasMeterMonth> findByDeptCondition(Pageable<GasMeterMonth> pager,GasMeterMonth condition);
	public List<GasMeterMonth> getParentAreaMonthGas(GasMeterMonth em);
	public List<GasMeterMonth> getChildrenAreaMonthGas(GasMeterMonth em);
	List<GasMeterMonth> getChildrenDeptMonthGas(GasMeterMonth em);
	public List<GasMeterMonth> findByDept(GasMeterMonth em) ;
	public List<GasMeterMonth> findDept(GasMeterMonth em);
	List<GasMeterMonth> getParentDeptMonthGas(GasMeterMonth em);
	List<GasMeterMonth> getChildDeptMonthGas(GasMeterMonth em);
}
