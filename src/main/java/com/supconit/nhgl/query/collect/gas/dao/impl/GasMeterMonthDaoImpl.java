package com.supconit.nhgl.query.collect.gas.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.query.collect.gas.dao.GasMeterMonthDao;
import com.supconit.nhgl.query.collect.gas.entities.GasMeterMonth;

import hc.base.domains.Pageable;
import hc.orm.AbstractBasicDaoImpl;

@Repository
public class GasMeterMonthDaoImpl extends AbstractBasicDaoImpl<GasMeterMonth,Long> implements GasMeterMonthDao{
	private static final String NAMESPACE=GasMeterMonth.class.getName();
	
	@Override
	public Pageable<GasMeterMonth> findByCondition(
			Pageable<GasMeterMonth> pager, GasMeterMonth condition) {
		return findByPager(pager,"findByCondition","countByCondition",condition);
	}

	@Override
	public Pageable<GasMeterMonth> findByDeptCondition(
			Pageable<GasMeterMonth> pager, GasMeterMonth condition) {
		return findByPager(pager,"findByDeptCondition","countByDeptCondition",condition);
	}

	@Override
	public GasMeterMonth findByConc(GasMeterMonth em) {
		return selectOne("findByConc", em);
	}

	@Override
	public GasMeterMonth findByConp(GasMeterMonth em) {
		return selectOne("findByConp",em);
	}


	@Override
	public List<GasMeterMonth> findByArea(GasMeterMonth em) {
		return selectList("findByArea",em);
	}

	@Override
	public List<GasMeterMonth> findByDeptAndFx(GasMeterMonth em) {
		return selectList("findByDeptAndFx",em);
	}


	@Override
	public List<GasMeterMonth> findArea(GasMeterMonth em) {
		return selectList("findArea",em);
	}

	@Override
	public List<GasMeterMonth> findMaxTime(GasMeterMonth em) {
		return selectList("findMaxTime",em);
	}

	@Override
	public int save(List<GasMeterMonth> em) {
		return insert("insert",em);
	}

	@Override
	public List<GasMeterMonth> getMonthGas(GasMeterMonth em) {
		return selectList("getMonthElectric",em);
	}

	@Override
	public List<GasMeterMonth> getChildrenAreaMonthGas(
			GasMeterMonth em) {
		return selectList("getChildrenAreaMonthGas",em);
	}

	@Override
	public List<GasMeterMonth> getParentAreaMonthGas(
			GasMeterMonth em) {
		return selectList("getParentAreaMonthGas",em);
	}

	@Override
	public List<GasMeterMonth> getChildrenDeptMonthGas(
			GasMeterMonth em) {
		return selectList("getChildrenDeptMonthGas",em);
	}

	@Override
	public void deleteByMonth(GasMeterMonth emm) {
			delete("deleteByMonth",emm);
	}

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public List<GasMeterMonth> findByDept(GasMeterMonth em) {
		return selectList("findByDept",em);
	}

	@Override
	public List<GasMeterMonth> findDept(GasMeterMonth em) {
		return selectList("findDept",em);
	}

	@Override
	public List<GasMeterMonth> getParentDeptMonthGas(GasMeterMonth em) {
		return selectList("getParentDeptMonthGas",em);
	}

	@Override
	public List<GasMeterMonth> getChildDeptMonthGas(GasMeterMonth em) {
		return selectList("getChildDeptMonthGas",em);
	}

}
