package com.supconit.nhgl.analyse.water.dept.service;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.List;

import com.supconit.nhgl.analyse.water.dept.entities.NhSDeptMonth;

public interface NhSDeptMonthService {
	List<NhSDeptMonth> getDeptSubWater(NhSDeptMonth dept);
	List<NhSDeptMonth> getDeptTree(NhSDeptMonth dept);

	Pageable<NhSDeptMonth> findByDeptCondition(Pagination<NhSDeptMonth> pager,
			NhSDeptMonth condition);
	Pageable<NhSDeptMonth> findByDeptConditionForNhgs(Pagination<NhSDeptMonth> pager,
			NhSDeptMonth condition);
	List<NhSDeptMonth> getDeptMonthList(NhSDeptMonth dept);
	NhSDeptMonth getDeptDayNight(NhSDeptMonth dept);
	/**
	 * 获取部门用水量前10
	 * @param deptWMonth
	 * @return
	 */
	List<NhSDeptMonth> getParentDeptMonthWater(NhSDeptMonth deptWMonth);
	List<NhSDeptMonth> getDeptList(NhSDeptMonth dept);
	List<NhSDeptMonth> getUntilDeptWater(NhSDeptMonth deptWMonth);
}
