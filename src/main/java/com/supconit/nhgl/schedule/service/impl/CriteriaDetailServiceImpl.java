/**
 * 
 */
package com.supconit.nhgl.schedule.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.nhgl.schedule.dao.CriteriaDetailDao;
import com.supconit.nhgl.schedule.entites.CriteriaDetail;

import hc.base.domains.Pageable;

/**
 * @author 
 * @create 2014-06-16 18:01:37 
 * @since 
 * 
 */
@Service("dlhmc_ycynjc_bzmx_service")
public class CriteriaDetailServiceImpl extends hc.orm.AbstractBasicOrmService<CriteriaDetail, Long> implements com.supconit.nhgl.schedule.service.CriteriaDetailService {
	
	private transient static final Logger	logger	= LoggerFactory.getLogger(CriteriaDetailServiceImpl.class);
	
	@Autowired
	private CriteriaDetailDao		bzmxDao;
	
	@Override
	@Transactional(readOnly = true)
	public CriteriaDetail getById(Long id) {
		return this.bzmxDao.getById(id);
	}

	@Override
	@Transactional
	public void insert(CriteriaDetail entity) {
		this.bzmxDao.insert(entity);
	}

	@Override
	@Transactional
	public void update(CriteriaDetail entity) {
		this.bzmxDao.update(entity);
	}

	@Override
	@Transactional
	public void delete(CriteriaDetail entity) {
		this.bzmxDao.delete(entity);
	}
	
	/*
	 * @see com.supconit.nhgl.ycynjc.services.CriteriaDetailService#find(hc.base.domains.Pageable, com.supconit.nhgl.ycynjc.entities.CriteriaDetail)
	 */
	@Override
	@Transactional(readOnly = true)
	public Pageable<CriteriaDetail> find(Pageable<CriteriaDetail> pager, CriteriaDetail condition) {
		
		return this.bzmxDao.findByPager(pager, condition);
	}

	@Override
	public int deleteByTaskCode(String taskCode) {
		return bzmxDao.deleteByTaskCode(taskCode);
	}

	@Override
	public List<CriteriaDetail> selectByTaskCode(String taskCode) {
		return bzmxDao.selectByTaskCode(taskCode);
	}

	@Override
	public CriteriaDetail selectByTaskCodeAndScore(CriteriaDetail criteriaDetail) {
		return bzmxDao.selectByTaskCodeAndScore(criteriaDetail);
	}
}
