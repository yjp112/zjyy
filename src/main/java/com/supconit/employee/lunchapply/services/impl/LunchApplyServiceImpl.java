package com.supconit.employee.lunchapply.services.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.employee.lunchapply.daos.LunchApplyDao;
import com.supconit.employee.lunchapply.entities.LunchApply;
import com.supconit.employee.lunchapply.services.LunchApplyService;

import hc.base.domains.Pageable;
import hc.bpm.context.annotations.BpmSupport;

@Service
public class LunchApplyServiceImpl extends AbstractBaseBusinessService<LunchApply, Long> implements LunchApplyService{

	@Autowired
	private LunchApplyDao lunchApplyDao;
	
	@Override
	public LunchApply getById(Long id) {
		return lunchApplyDao.getById(id);
	}

	@Override
	public void insert(LunchApply entity) {
		lunchApplyDao.insert(entity);
	}

	@Override
	public void update(LunchApply entity) {
		lunchApplyDao.update(entity);
	}

	@Override
	public void deleteById(Long id) {
		lunchApplyDao.deleteById(id);
	}

	@Override
	@Transactional
	@BpmSupport(businessKey = "#task.id", businessDomain = "#task", variableKeys = "{'task'}", variables = "{#task}")
	public void insertLunchApply(LunchApply task) {
		insert(task);
	}

	@Override
	@Transactional
	@BpmSupport(businessKey = "#task.id", businessDomain = "#task", variableKeys = "{'task'}", variables = "{#task}")
	public void updateLunchApply(LunchApply task) {
		update(task);
	}

	@Override
	public Pageable<LunchApply> findByPage(Pageable<LunchApply> pager,
			LunchApply condition) {
		return lunchApplyDao.findByPage(pager, condition);
	}

	@Override
	public int countLunchNumOfCurrentDay(Date currentDay) {
		return lunchApplyDao.countLunchNumOfCurrentDay(currentDay);
	}

}
