package com.supconit.maintain.daos;

import hc.base.domains.Pageable;

import com.supconit.common.daos.BaseDao;
import com.supconit.maintain.entities.MaintainPlan;

public interface MaintainPlanDao extends BaseDao<MaintainPlan, Long>{
	/**
	 * 生成计划
	 */
	void generatePlan();
	/**
	 * 分页查询(月)
	 */
	Pageable<MaintainPlan> findByCondition(Pageable<MaintainPlan> pager,MaintainPlan condition);
	/**
	 * 分页查询(年)
	 */
	Pageable<MaintainPlan> findByConditionYear(Pageable<MaintainPlan> pager,MaintainPlan condition);
	
}
