package com.supconit.nhgl.analyse.energy.dept.dao;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.List;

import com.supconit.nhgl.analyse.energy.dept.entities.NhENDeptMonth;

public interface NhENDeptMonthDao {

	List<NhENDeptMonth> getDeptSubEnergy(NhENDeptMonth dept);
	List<NhENDeptMonth> getDeptTree(NhENDeptMonth dept);
	List<NhENDeptMonth> getDeptList(NhENDeptMonth dept);
	List<NhENDeptMonth> getDeptMonthList(NhENDeptMonth dept);
	NhENDeptMonth getDeptDayNight(NhENDeptMonth dept);

	Pageable<NhENDeptMonth> findByDeptCondition(Pagination<NhENDeptMonth> pager,
			NhENDeptMonth condition);

}
