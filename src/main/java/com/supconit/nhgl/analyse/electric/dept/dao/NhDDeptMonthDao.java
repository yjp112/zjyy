package com.supconit.nhgl.analyse.electric.dept.dao;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.List;

import com.supconit.nhgl.analyse.electric.dept.entities.NhDDeptMonth;

public interface NhDDeptMonthDao {

	List<NhDDeptMonth> getDeptSubElectricty(NhDDeptMonth dept);
	List<NhDDeptMonth> getDeptTree(NhDDeptMonth dept);
	List<NhDDeptMonth> getDeptList(NhDDeptMonth dept);
	List<NhDDeptMonth> getDeptMonthList(NhDDeptMonth dept);
	NhDDeptMonth getDeptDayNight(NhDDeptMonth dept);

	Pageable<NhDDeptMonth> findByDeptCondition(Pagination<NhDDeptMonth> pager,
			NhDDeptMonth condition);
	Pageable<NhDDeptMonth> findByDeptConditionForNhgs(Pagination<NhDDeptMonth> pager,
			NhDDeptMonth condition);

	List<NhDDeptMonth> getParentDeptMonthElectricty(NhDDeptMonth deptEMonth);

	List<NhDDeptMonth> getUntilDeptElectricty(NhDDeptMonth deptEMonth);

}
