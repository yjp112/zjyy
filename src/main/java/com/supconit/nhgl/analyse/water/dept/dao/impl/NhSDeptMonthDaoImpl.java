package com.supconit.nhgl.analyse.water.dept.dao.impl;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;
import hc.orm.AbstractBasicDaoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.analyse.water.dept.dao.NhSDeptMonthDao;
import com.supconit.nhgl.analyse.water.dept.entities.NhSDeptMonth;

@Repository
public class NhSDeptMonthDaoImpl extends AbstractBasicDaoImpl<NhSDeptMonth, Long> implements NhSDeptMonthDao {

	private static final String NAMESPACE = NhSDeptMonth.class.getName();
	
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public List<NhSDeptMonth> getDeptTree(NhSDeptMonth dept) {
		return selectList("getDeptTree", dept);
	}

	@Override
	public Pageable<NhSDeptMonth> findByDeptCondition(
			Pagination<NhSDeptMonth> pager, NhSDeptMonth condition) {
		return findByPager(pager, "findByDeptCondition", "countByCondition", condition);
	}

	@Override
	public List<NhSDeptMonth> getParentDeptMonthWater(NhSDeptMonth deptWMonth) {
		return selectList("getParentDeptMonthWater", deptWMonth);
	}

	@Override
	public List<NhSDeptMonth> getDeptList(NhSDeptMonth dept) {
		return selectList("getDeptList",dept);
	}

	@Override
	public List<NhSDeptMonth> getDeptMonthList(NhSDeptMonth dept) {
		return selectList("getDeptMonthList",dept);
	}

	@Override
	public NhSDeptMonth getDeptDayNight(NhSDeptMonth dept) {
		return selectOne("getDeptDayNight",dept);
	}
	public List<NhSDeptMonth> getUntilDeptWater(NhSDeptMonth deptWMonth) {
		return selectList("getUntilDeptWater",deptWMonth);
	}

	@Override
	public List<NhSDeptMonth> getDeptSubWater(NhSDeptMonth dept) {
		return selectList("getDeptSubWater",dept);
	}

	@Override
	public Pageable<NhSDeptMonth> findByDeptConditionForNhgs(
			Pagination<NhSDeptMonth> pager, NhSDeptMonth condition) {
		return findByPager(pager,"findByDeptConditionForNhgs","countByConditionForNhgs",condition);
	}

}
