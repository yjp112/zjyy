package com.supconit.nhgl.cost.service.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.common.utils.UtilTool;
import com.supconit.nhgl.cost.dao.PriceDao;
import com.supconit.nhgl.cost.entities.Price;
import com.supconit.nhgl.cost.service.PriceService;
import com.supconit.ywgl.exceptions.UnsupportedBusinessException;

import hc.orm.AbstractBasicOrmService;

@Service
public class PriceServiceImpl extends AbstractBasicOrmService<Price,Long> implements PriceService{
	@Autowired
	private PriceDao priceDao;

	public List<Price> findByYearMonth(Price price){
		Calendar c = Calendar.getInstance();
		
		if(priceDao.findByYear(String.valueOf(c.get(Calendar.YEAR)+1)).size()==0){
			priceDao.insertNextYear(String.valueOf(c.get(Calendar.YEAR))+String.valueOf(c.get(Calendar.YEAR)+1));
		}
		return priceDao.findByYearMonth(price);
	}
	@Override
	public Price getById(Long arg0) {
		throw new UnsupportedBusinessException("暂不支持");
	}

	@Override
	public void insert(Price entity) {
		priceDao.insert(entity);
		
	}

	@Override
	public void update(Price entity) {
		priceDao.update(entity);
		
	}
	@Override
	@Transactional
	public void update(List<Price> lst) {
		if(!UtilTool.isEmptyList(lst)){
			for(Price p: lst){
				update(p);
			}
		}
		
	}
	@Override
	public void delete(Price entity) {
		throw new UnsupportedBusinessException("暂不支持");
		
	}

    @Override
    public Price queryPriceByMonth(String month){
        return priceDao.findByMonth(month);
    }

}
