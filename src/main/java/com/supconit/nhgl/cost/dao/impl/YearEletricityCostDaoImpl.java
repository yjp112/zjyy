package com.supconit.nhgl.cost.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.cost.dao.YearEletricityCostDao;
import com.supconit.nhgl.cost.entities.YearEletricityCost;

import hc.orm.AbstractBasicDaoImpl;
@Repository
public class YearEletricityCostDaoImpl extends AbstractBasicDaoImpl<YearEletricityCost,Long> implements YearEletricityCostDao{
	private static final String NAMESPACE=YearEletricityCost.class.getName();
	@Override
	public List<YearEletricityCost> findYearEletricityCostByCondition(YearEletricityCost condition) {
		return selectList("findYearEletricityCostByCondition",condition);
	}

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}

}
