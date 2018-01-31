package com.supconit.nhgl.analyse.water.dept.service.impl;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.nhgl.analyse.water.dept.dao.NhSDeptMonthDao;
import com.supconit.nhgl.analyse.water.dept.entities.NhSDeptMonth;
import com.supconit.nhgl.analyse.water.dept.service.NhSDeptMonthService;

@Service
public class NhSDeptMonthServiceImpl implements NhSDeptMonthService {

	@Autowired
	private NhSDeptMonthDao dao;

	@Override
	public List<NhSDeptMonth> getDeptTree(NhSDeptMonth dept) {
		return dao.getDeptTree(dept);
	}

	@Override
	public Pageable<NhSDeptMonth> findByDeptCondition(
			Pagination<NhSDeptMonth> pager, NhSDeptMonth condition) {
		return dao.findByDeptCondition(pager, condition);
	}

	@Override
	public List<NhSDeptMonth> getParentDeptMonthWater(NhSDeptMonth deptWMonth) {
		return dao.getParentDeptMonthWater(deptWMonth);
	}

	@Override
	public List<NhSDeptMonth> getDeptList(NhSDeptMonth dept) {
		return dao.getDeptList(dept);
	}

	@Override
	public List<NhSDeptMonth> getDeptMonthList(NhSDeptMonth dept) {
		return dao.getDeptMonthList(dept);
	}

	@Override
	public NhSDeptMonth getDeptDayNight(NhSDeptMonth dept) {
		return dao.getDeptDayNight(dept);
	}
	public List<NhSDeptMonth> getUntilDeptWater(NhSDeptMonth deptWMonth) {
		return dao.getUntilDeptWater(deptWMonth);
	}

	@Override
	public List<NhSDeptMonth> getDeptSubWater(NhSDeptMonth dept) {
		return dao.getDeptSubWater(dept);
	}

	@Override
	public Pageable<NhSDeptMonth> findByDeptConditionForNhgs(
			Pagination<NhSDeptMonth> pager, NhSDeptMonth condition) {
		return dao.findByDeptConditionForNhgs(pager, condition);
	}

}
