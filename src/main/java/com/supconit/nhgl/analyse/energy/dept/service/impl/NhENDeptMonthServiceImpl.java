package com.supconit.nhgl.analyse.energy.dept.service.impl;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.nhgl.analyse.energy.dept.dao.NhENDeptMonthDao;
import com.supconit.nhgl.analyse.energy.dept.entities.NhENDeptMonth;
import com.supconit.nhgl.analyse.energy.dept.service.NhENDeptMonthService;

@Service
public class NhENDeptMonthServiceImpl implements NhENDeptMonthService {

	@Autowired
	private NhENDeptMonthDao dao;

	@Override
	public List<NhENDeptMonth> getDeptTree(NhENDeptMonth dept) {
		return dao.getDeptTree(dept);
	}

	@Override
	public Pageable<NhENDeptMonth> findByDeptCondition(
			Pagination<NhENDeptMonth> pager, NhENDeptMonth condition) {
		return dao.findByDeptCondition(pager, condition);
	}

	@Override
	public List<NhENDeptMonth> getDeptList(NhENDeptMonth dept) {
		return dao.getDeptList(dept);
	}

	@Override
	public List<NhENDeptMonth> getDeptMonthList(NhENDeptMonth dept) {
		return dao.getDeptMonthList(dept);
	}

	@Override
	public NhENDeptMonth getDeptDayNight(NhENDeptMonth dept) {
		return dao.getDeptDayNight(dept);
	}

	@Override
	public List<NhENDeptMonth> getDeptSubEnergy(NhENDeptMonth dept) {
		return dao.getDeptSubEnergy(dept);
	}

}
