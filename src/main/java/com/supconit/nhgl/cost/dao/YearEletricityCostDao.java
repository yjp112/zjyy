package com.supconit.nhgl.cost.dao;

import java.util.List;

import com.supconit.nhgl.cost.entities.YearEletricityCost;

import hc.orm.BasicDao;

public interface YearEletricityCostDao extends BasicDao<YearEletricityCost,Long>{
	public List<YearEletricityCost> findYearEletricityCostByCondition(YearEletricityCost condition);

}
