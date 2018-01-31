package com.supconit.inspection.services;

import hc.base.domains.Pageable;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.inspection.entities.InspectionPlan;

public interface InspectionPlanService extends BaseBusinessService<InspectionPlan, Long>{
	/**
	 * 分页查询(周)
	 */
	Pageable<InspectionPlan> findByCondition(Pageable<InspectionPlan> pager,InspectionPlan condition);
	/**
	 * 分页查询(月)
	 */
	void findByConditionMonth(Pageable<InspectionPlan> pager,InspectionPlan condition);
	/**
	 * 生成计划
	 */
	void generatePlan();
}
