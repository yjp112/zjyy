/**
 * 
 */
package com.supconit.nhgl.schedule.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.supconit.nhgl.schedule.entites.CriteriaDetail;

import hc.base.domains.Pageable;

/**
 * @author 
 * @create 2014-06-16 18:01:37
 * @since 
 * 
 */
 
@Repository("dlhmc_ycynjc_bzmx_dao")
public class CriteriaDetailDaoImpl extends hc.orm.AbstractBasicDaoImpl<CriteriaDetail, Long> implements com.supconit.nhgl.schedule.dao.CriteriaDetailDao {

	private static final String	NAMESPACE	= CriteriaDetail.class.getName();
	private transient static final Logger	log	= LoggerFactory.getLogger(CriteriaDetailDaoImpl.class);

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	
	/*
	 * @see com.supconit.nhgl.ycynjc.dao.BzmxDao#findByPager(hc.base.domains.Pageable, com.supconit.nhgl.ycynjc.entities.CriteriaDetail)
	 */
	@Override
	public Pageable<CriteriaDetail> findByPager(Pageable<CriteriaDetail> pager, CriteriaDetail condition) {
		return findByPager(pager, "selectPager", "countPager", condition);
	}
	@Override
	public int deleteByTaskCode(String taskCode) {
		return delete("deleteByTaskCode", taskCode);
	}

	@Override
	public List<CriteriaDetail> selectByTaskCode(String taskCode) {
		return selectList("selectByTaskCode", taskCode);
	}

	@Override
	public CriteriaDetail selectByTaskCodeAndScore(CriteriaDetail criteriaDetail) {
		return selectOne("selectByTaskCodeAndScore",criteriaDetail);
	}
}
