package com.supconit.nhgl.query.collect.gas.dao;

import java.util.List;

import com.supconit.nhgl.query.collect.gas.entities.GasMeterMonth;

import hc.base.domains.Pageable;


public interface GasMeterMonthDao {
	public Pageable<GasMeterMonth> findByCondition(Pageable<GasMeterMonth> pager,GasMeterMonth condition);
	public Pageable<GasMeterMonth> findByDeptCondition(Pageable<GasMeterMonth> pager,GasMeterMonth condition);
	public GasMeterMonth findByConc(GasMeterMonth em);
	public GasMeterMonth findByConp(GasMeterMonth em);
	public List<GasMeterMonth> findByArea(GasMeterMonth em);
	public List<GasMeterMonth> findByDeptAndFx(GasMeterMonth em);
	public List<GasMeterMonth>  findArea(GasMeterMonth em);
	public List<GasMeterMonth> findMaxTime(GasMeterMonth em);
	public int save(List<GasMeterMonth> em);
	public List<GasMeterMonth> getMonthGas(GasMeterMonth em);
	List<GasMeterMonth> getChildrenAreaMonthGas(
			GasMeterMonth em);
	List<GasMeterMonth> getParentAreaMonthGas(GasMeterMonth em);
	List<GasMeterMonth> getChildrenDeptMonthGas(
			GasMeterMonth em);

	void deleteByMonth(GasMeterMonth emm);
	public List<GasMeterMonth> findByDept(GasMeterMonth em);
	public List<GasMeterMonth> findDept(GasMeterMonth em);
	List<GasMeterMonth> getParentDeptMonthGas(GasMeterMonth em);
	List<GasMeterMonth> getChildDeptMonthGas(GasMeterMonth em);
}
