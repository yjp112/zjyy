package com.supconit.nhgl.cost.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.cost.dao.CostDao;
import com.supconit.nhgl.cost.entities.Cost;
import com.supconit.nhgl.cost.entities.RealTimeCost;

import hc.orm.AbstractBasicDaoImpl;

@Repository
public class CostDaoImpl extends AbstractBasicDaoImpl<Cost, Long> implements
		CostDao {
	private static final String NAMESPACE = Cost.class.getName();

	@Override
	public List<Cost> findByCondition(Cost condition) {
		return selectList("findByCondition", condition);
	}

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public List<RealTimeCost> findZkCost(RealTimeCost condition) {
		Map param = new HashMap();
		param.put("startTime", condition.getStartTime());
		param.put("startTime1", condition.getStartTime1());
		param.put("endTime", condition.getEndTime());
		param.put("endTime1", condition.getEndTime1());
		return selectList("findZkCost", param);
	} 

	@Override
	public List<Cost> findByConditionEletricity(Cost condition) {
		return selectList("findByConditionEletricity", condition);
	}

	public List<Cost> findByConditionWater(Cost condition) {
		return selectList("findByConditionWater", condition);
	}

}
