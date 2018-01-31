package com.supconit.nhgl.analyse.energy.dept.dao.impl;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;
import hc.orm.AbstractBasicDaoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.analyse.energy.dept.dao.NhENDeptMonthDao;
import com.supconit.nhgl.analyse.energy.dept.entities.NhENDeptMonth;

@Repository
public class NhENDeptMonthDaoImpl extends AbstractBasicDaoImpl<NhENDeptMonth, Long> implements NhENDeptMonthDao {

	private static final String NAMESPACE = NhENDeptMonth.class.getName();
	
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public List<NhENDeptMonth> getDeptTree(NhENDeptMonth dept) {
		return selectList("getDeptTree", dept);
	}

	@Override
	public Pageable<NhENDeptMonth> findByDeptCondition(
			Pagination<NhENDeptMonth> pager, NhENDeptMonth condition) {
		return findByPager(pager, "findByDeptCondition", "countByCondition", condition);
	}

	@Override
	public List<NhENDeptMonth> getDeptList(NhENDeptMonth dept) {
		return selectList("getDeptList",dept);
	}

	@Override
	public List<NhENDeptMonth> getDeptMonthList(NhENDeptMonth dept) {
		return selectList("getDeptMonthList",dept);
	}

	@Override
	public NhENDeptMonth getDeptDayNight(NhENDeptMonth dept) {
		return selectOne("getDeptDayNight",dept);
	}

	@Override
	public List<NhENDeptMonth> getDeptSubEnergy(NhENDeptMonth dept) {
		return selectList("getDeptSubEnergy",dept);
	}

}
