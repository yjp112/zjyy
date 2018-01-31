package com.supconit.nhgl.cost.service;

import java.util.List;

import com.supconit.nhgl.cost.entities.Price;

import hc.orm.BasicOrmService;


public interface PriceService extends BasicOrmService<Price,Long>{
	public List<Price> findByYearMonth(Price price);

    Price queryPriceByMonth(String month);
	 void update(List<Price> lst) ;
}
