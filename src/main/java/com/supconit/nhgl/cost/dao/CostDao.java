package com.supconit.nhgl.cost.dao;

import java.util.List;

import com.supconit.nhgl.cost.entities.Cost;
import com.supconit.nhgl.cost.entities.RealTimeCost;

import hc.orm.BasicDao;

public interface CostDao extends BasicDao<Cost,Long>{
	public List<Cost> findByCondition(Cost condition);
	/**
	 * 中控大厦用电量报表统计
	 */
	List<RealTimeCost> findZkCost(RealTimeCost condition);
	List<Cost> findByConditionEletricity(Cost condition);
	List<Cost> findByConditionWater(Cost condition);
}
