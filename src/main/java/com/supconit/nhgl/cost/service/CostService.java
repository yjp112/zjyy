package com.supconit.nhgl.cost.service;

import java.util.List;

import com.supconit.nhgl.cost.entities.Cost;
import com.supconit.nhgl.cost.entities.RealTimeCost;

import hc.orm.BasicOrmService;

public interface CostService extends BasicOrmService<Cost,Long>{
	public List<Cost> findByCondition(Cost condition);
	/**
	 * 中控大厦用电量报表统计
	 */
	List<RealTimeCost> findZkCost(RealTimeCost condition);
	
	List<Cost> findByConditionEletricity(Cost condition);
	List<Cost> findByConditionWater(Cost condition);
}
