package com.supconit.nhgl.analyse.gas.dept.dao;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.List;

import com.supconit.nhgl.analyse.gas.dept.entities.NhQDeptMonth;

public interface NhQDeptMonthDao {

	List<NhQDeptMonth> getDeptSubGas(NhQDeptMonth dept);
	List<NhQDeptMonth> getDeptTree(NhQDeptMonth dept);
	List<NhQDeptMonth> getDeptList(NhQDeptMonth dept);
	List<NhQDeptMonth> getDeptMonthList(NhQDeptMonth dept);
	NhQDeptMonth getDeptDayNight(NhQDeptMonth dept);

	Pageable<NhQDeptMonth> findByDeptCondition(Pagination<NhQDeptMonth> pager,
			NhQDeptMonth condition);

}
