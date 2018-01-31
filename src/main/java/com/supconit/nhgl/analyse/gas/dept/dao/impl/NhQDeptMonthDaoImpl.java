package com.supconit.nhgl.analyse.gas.dept.dao.impl;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;
import hc.orm.AbstractBasicDaoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.analyse.gas.dept.dao.NhQDeptMonthDao;
import com.supconit.nhgl.analyse.gas.dept.entities.NhQDeptMonth;

@Repository
public class NhQDeptMonthDaoImpl extends AbstractBasicDaoImpl<NhQDeptMonth, Long> implements NhQDeptMonthDao {

	private static final String NAMESPACE = NhQDeptMonth.class.getName();
	
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public List<NhQDeptMonth> getDeptTree(NhQDeptMonth dept) {
		return selectList("getDeptTree", dept);
	}

	@Override
	public Pageable<NhQDeptMonth> findByDeptCondition(
			Pagination<NhQDeptMonth> pager, NhQDeptMonth condition) {
		return findByPager(pager, "findByDeptCondition", "countByCondition", condition);
	}

	@Override
	public List<NhQDeptMonth> getDeptList(NhQDeptMonth dept) {
		return selectList("getDeptList",dept);
	}

	@Override
	public List<NhQDeptMonth> getDeptMonthList(NhQDeptMonth dept) {
		return selectList("getDeptMonthList",dept);
	}

	@Override
	public NhQDeptMonth getDeptDayNight(NhQDeptMonth dept) {
		return selectOne("getDeptDayNight",dept);
	}

	@Override
	public List<NhQDeptMonth> getDeptSubGas(NhQDeptMonth dept) {
		return selectList("getDeptSubGas",dept);
	}

}
