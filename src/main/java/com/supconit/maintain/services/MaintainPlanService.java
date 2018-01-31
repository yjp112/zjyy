package com.supconit.maintain.services;

import hc.base.domains.Pageable;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.maintain.entities.MaintainPlan;

public interface MaintainPlanService extends BaseBusinessService<MaintainPlan, Long>{
	/**
	 * 分页查询(月)
	 */
	Pageable<MaintainPlan> findByCondition(Pageable<MaintainPlan> pager,MaintainPlan condition);
	/**
	 * 分页查询(年)
	 */
	void findByConditionYear(Pageable<MaintainPlan> pager,MaintainPlan condition);
	/**
	 * 生成计划
	 */
	void generatePlan();
}
