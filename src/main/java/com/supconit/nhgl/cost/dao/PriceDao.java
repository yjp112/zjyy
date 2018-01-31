package com.supconit.nhgl.cost.dao;

import java.util.List;

import com.supconit.nhgl.cost.entities.Price;

import hc.orm.BasicDao;


public interface PriceDao extends BasicDao<Price,Long>{
	public List<Price> findByYearMonth(Price price);

    Price findByMonth(String month);
    List<Price> findByYear(String year);
   int insertNextYear(String nextYear);
}
