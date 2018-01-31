package com.supconit.nhgl.analyse.electric.dept.service.impl;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.nhgl.analyse.electric.dept.dao.NhDDeptMonthDao;
import com.supconit.nhgl.analyse.electric.dept.entities.NhDDeptMonth;
import com.supconit.nhgl.analyse.electric.dept.service.NhDDeptMonthService;

@Service
public class NhDDeptMonthServiceImpl implements NhDDeptMonthService {

	@Autowired
	private NhDDeptMonthDao dao;

	@Override
	public List<NhDDeptMonth> getDeptTree(NhDDeptMonth dept) {
		return dao.getDeptTree(dept);
	}

	@Override
	public Pageable<NhDDeptMonth> findByDeptCondition(
			Pagination<NhDDeptMonth> pager, NhDDeptMonth condition) {
		return dao.findByDeptCondition(pager, condition);
	}

	@Override
	public List<NhDDeptMonth> getParentDeptMonthElectricty(
			NhDDeptMonth deptEMonth) {
		return dao.getParentDeptMonthElectricty(deptEMonth);
	}

	@Override
	public List<NhDDeptMonth> getDeptList(NhDDeptMonth dept) {
		return dao.getDeptList(dept);
	}

	@Override
	public List<NhDDeptMonth> getDeptMonthList(NhDDeptMonth dept) {
		return dao.getDeptMonthList(dept);
	}

	@Override
	public NhDDeptMonth getDeptDayNight(NhDDeptMonth dept) {
		return dao.getDeptDayNight(dept);
	}
	@Override
	public List<NhDDeptMonth> getUntilDeptElectricty(NhDDeptMonth deptEMonth) {
		return dao.getUntilDeptElectricty(deptEMonth);
	}

	@Override
	public List<NhDDeptMonth> getDeptSubElectricty(NhDDeptMonth dept) {
		return dao.getDeptSubElectricty(dept);
	}

	@Override
	public Pageable<NhDDeptMonth> findByDeptConditionForNhgs(
			Pagination<NhDDeptMonth> pager, NhDDeptMonth condition) {
		return dao.findByDeptConditionForNhgs(pager, condition);
	}

}
