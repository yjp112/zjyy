package com.supconit.inspection.daos;

import hc.base.domains.Pageable;

import com.supconit.common.daos.BaseDao;
import com.supconit.inspection.entities.InspectionPlan;

public interface InspectionPlanDao extends BaseDao<InspectionPlan, Long>{
	/**
	 * 生成计划
	 */
	void generatePlan();
	/**
	 * 分页查询(周)
	 */
	Pageable<InspectionPlan> findByCondition(Pageable<InspectionPlan> pager,InspectionPlan condition);
	/**
	 * 分页查询(月)
	 */
	Pageable<InspectionPlan> findByConditionMonth(Pageable<InspectionPlan> pager,InspectionPlan condition);
}
