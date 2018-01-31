package com.supconit.nhgl.analyse.electric.dept.dao.impl;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;
import hc.orm.AbstractBasicDaoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.analyse.electric.dept.dao.NhDDeptMonthDao;
import com.supconit.nhgl.analyse.electric.dept.entities.NhDDeptMonth;

@Repository
public class NhDDeptMonthDaoImpl extends AbstractBasicDaoImpl<NhDDeptMonth, Long> implements NhDDeptMonthDao {

	private static final String NAMESPACE = NhDDeptMonth.class.getName();
	
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public List<NhDDeptMonth> getDeptTree(NhDDeptMonth dept) {
		return selectList("getDeptTree", dept);
	}

	@Override
	public Pageable<NhDDeptMonth> findByDeptCondition(
			Pagination<NhDDeptMonth> pager, NhDDeptMonth condition) {
		return findByPager(pager, "findByDeptCondition", "countByCondition", condition);
	}

	@Override
	public List<NhDDeptMonth> getParentDeptMonthElectricty(
			NhDDeptMonth deptEMonth) {
		return selectList("getParentDeptMonthElectricty", deptEMonth);
	}

	@Override
	public List<NhDDeptMonth> getDeptList(NhDDeptMonth dept) {
		return selectList("getDeptList",dept);
	}

	@Override
	public List<NhDDeptMonth> getDeptMonthList(NhDDeptMonth dept) {
		return selectList("getDeptMonthList",dept);
	}

	@Override
	public NhDDeptMonth getDeptDayNight(NhDDeptMonth dept) {
		return selectOne("getDeptDayNight",dept);
	}

	@Override
	public List<NhDDeptMonth> getUntilDeptElectricty(NhDDeptMonth deptEMonth) {
		return selectList("getUntilDeptElectricty", deptEMonth);
	}

	@Override
	public List<NhDDeptMonth> getDeptSubElectricty(NhDDeptMonth dept) {
		return selectList("getDeptSubElectricty",dept);
	}

	@Override
	public Pageable<NhDDeptMonth> findByDeptConditionForNhgs(
			Pagination<NhDDeptMonth> pager, NhDDeptMonth condition) {
		return findByPager(pager,"findByDeptConditionForNhgs","countByConditionForNhgs",condition);
	}
}
