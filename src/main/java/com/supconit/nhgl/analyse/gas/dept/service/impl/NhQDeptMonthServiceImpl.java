package com.supconit.nhgl.analyse.gas.dept.service.impl;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.nhgl.analyse.gas.dept.dao.NhQDeptMonthDao;
import com.supconit.nhgl.analyse.gas.dept.entities.NhQDeptMonth;
import com.supconit.nhgl.analyse.gas.dept.service.NhQDeptMonthService;

@Service
public class NhQDeptMonthServiceImpl implements NhQDeptMonthService {

	@Autowired
	private NhQDeptMonthDao dao;

	@Override
	public List<NhQDeptMonth> getDeptTree(NhQDeptMonth dept) {
		return dao.getDeptTree(dept);
	}

	@Override
	public Pageable<NhQDeptMonth> findByDeptCondition(
			Pagination<NhQDeptMonth> pager, NhQDeptMonth condition) {
		return dao.findByDeptCondition(pager, condition);
	}

	@Override
	public List<NhQDeptMonth> getDeptList(NhQDeptMonth dept) {
		return dao.getDeptList(dept);
	}

	@Override
	public List<NhQDeptMonth> getDeptMonthList(NhQDeptMonth dept) {
		return dao.getDeptMonthList(dept);
	}

	@Override
	public NhQDeptMonth getDeptDayNight(NhQDeptMonth dept) {
		return dao.getDeptDayNight(dept);
	}

	@Override
	public List<NhQDeptMonth> getDeptSubGas(NhQDeptMonth dept) {
		return dao.getDeptSubGas(dept);
	}

}
