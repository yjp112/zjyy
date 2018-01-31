package com.supconit.nhgl.analyse.electric.dept.service;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.List;

import com.supconit.nhgl.analyse.electric.dept.entities.NhDDeptMonth;

public interface NhDDeptMonthService {

	/**
	 * 获取部门树
	 * @param dept
	 * @return
	 */
	List<NhDDeptMonth> getDeptTree(NhDDeptMonth dept);

	/**
	 * 获取区域树
	 * @param pager
	 * @param condition
	 * @return
	 */
	Pageable<NhDDeptMonth> findByDeptCondition(Pagination<NhDDeptMonth> pager,
			NhDDeptMonth condition);

	/**
	 * 获取一级部门用电量前10
	 * @param deptEMonth
	 * @return
	 */
	List<NhDDeptMonth> getParentDeptMonthElectricty(NhDDeptMonth deptEMonth);
	List<NhDDeptMonth> getDeptList(NhDDeptMonth dept);
	List<NhDDeptMonth> getDeptMonthList(NhDDeptMonth dept);
	NhDDeptMonth getDeptDayNight(NhDDeptMonth dept);
	List<NhDDeptMonth> getUntilDeptElectricty(NhDDeptMonth deptEMonth);
	List<NhDDeptMonth> getDeptSubElectricty(NhDDeptMonth dept);
	Pageable<NhDDeptMonth> findByDeptConditionForNhgs(Pagination<NhDDeptMonth> pager,
			NhDDeptMonth condition);
}
