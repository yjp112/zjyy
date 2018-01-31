package com.supconit.maintain.daos.impl;

import hc.base.domains.Pageable;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.maintain.daos.MaintainPlanDao;
import com.supconit.maintain.entities.MaintainPlan;

@Repository
public class MaintainPlanDaoImpl extends AbstractBaseDao<MaintainPlan, Long> implements MaintainPlanDao{
	private static final String	NAMESPACE = MaintainPlan.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	
	@Override
	public Pageable<MaintainPlan> findByCondition(Pageable<MaintainPlan> pager,
			MaintainPlan condition) {
		return findByPager(pager, "findByConditions", "countByConditions", condition);
	}
	
	@Override
	public void generatePlan() {
		update("readyForPlan");
	}

	@Override
	public Pageable<MaintainPlan> findByConditionYear(
			Pageable<MaintainPlan> pager, MaintainPlan condition) {
		return findByPager(pager, "findByConditionsYear", "countByConditionsYear", condition);
	}

}
