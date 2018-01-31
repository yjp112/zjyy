package com.supconit.nhgl.analyse.water.dept.dao;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.List;

import com.supconit.nhgl.analyse.water.dept.entities.NhSDeptMonth;

public interface NhSDeptMonthDao {

	List<NhSDeptMonth> getDeptSubWater(NhSDeptMonth dept);
	List<NhSDeptMonth> getDeptTree(NhSDeptMonth dept);
	List<NhSDeptMonth> getDeptList(NhSDeptMonth dept);
	List<NhSDeptMonth> getDeptMonthList(NhSDeptMonth dept);
	NhSDeptMonth getDeptDayNight(NhSDeptMonth dept);

	Pageable<NhSDeptMonth> findByDeptCondition(Pagination<NhSDeptMonth> pager,
			NhSDeptMonth condition);
	Pageable<NhSDeptMonth> findByDeptConditionForNhgs(Pagination<NhSDeptMonth> pager,
			NhSDeptMonth condition);

	List<NhSDeptMonth> getParentDeptMonthWater(NhSDeptMonth deptWMonth);

	List<NhSDeptMonth> getUntilDeptWater(NhSDeptMonth deptWMonth);

}
