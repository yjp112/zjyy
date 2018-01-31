package com.supconit.employee.timeCard.services.impl;

import java.text.SimpleDateFormat;
import java.util.Iterator;

import hc.base.domains.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.employee.timeCard.daos.TimeCardDao;
import com.supconit.employee.timeCard.entities.TimeCard;
import com.supconit.employee.timeCard.services.TimeCardService;

@Service
public class TimeCardServiceImpl extends AbstractBaseBusinessService<TimeCard, Long> implements TimeCardService{
	@Autowired
	private TimeCardDao timeCardDao;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public TimeCard getById(Long arg0) {
		return null;
	}

	@Override
	public void insert(TimeCard entity) {
		
	}

	@Override
	public void update(TimeCard entity) {
		
	}

	@Override
	public void deleteById(Long id) {
		
	}

	@Override
	public Pageable<TimeCard> findByPage(Pageable<TimeCard> pager,
			TimeCard condition) {
		timeCardDao.findByPage(pager, condition);
		Iterator<TimeCard> it = pager.iterator();
		while(it.hasNext()){
			TimeCard t = it.next();
			t.setEventTimes(sdf.format(t.getEventTime()));
		}
		return pager;
	}

}
