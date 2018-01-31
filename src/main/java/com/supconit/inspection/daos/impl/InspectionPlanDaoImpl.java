package com.supconit.inspection.daos.impl;

import hc.base.domains.Pageable;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.inspection.daos.InspectionPlanDao;
import com.supconit.inspection.entities.InspectionPlan;

@Repository
public class InspectionPlanDaoImpl extends AbstractBaseDao<InspectionPlan, Long> implements InspectionPlanDao{
	private static final String	NAMESPACE = InspectionPlan.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	
	@Override
	public Pageable<InspectionPlan> findByCondition(Pageable<InspectionPlan> pager,
			InspectionPlan condition) {
		return findByPager(pager, "findByConditions", "countByConditions", condition);
	}
	
	@Override
	public void generatePlan() {
		update("readyForPlan");
	}

	@Override
	public Pageable<InspectionPlan> findByConditionMonth(
			Pageable<InspectionPlan> pager, InspectionPlan condition) {
		return findByPager(pager, "findByConditionsMonth", "countByConditionsMonth", condition);
	}
}
