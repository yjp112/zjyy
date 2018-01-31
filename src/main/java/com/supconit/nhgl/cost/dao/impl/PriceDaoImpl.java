package com.supconit.nhgl.cost.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.cost.dao.PriceDao;
import com.supconit.nhgl.cost.entities.Price;

import hc.orm.AbstractBasicDaoImpl;

 
@Repository
public class PriceDaoImpl extends AbstractBasicDaoImpl<Price,Long> implements PriceDao{

	private static final String NAMESPACE=Price.class.getName();
	
	 
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
    @Override
	public List<Price> findByYearMonth(Price price) {
		return selectList("findByYearMonth",price);
	}

    @Override
    public Price findByMonth(String month) {
        return selectOne("findByMonth",month);
    }
	@Override
	public List<Price> findByYear(String year) {
		return selectList("findByYear",year);
	}
	@Override
	public int insertNextYear(String nextYear) {
		return insert("insertNextYear", nextYear);
	}
}
