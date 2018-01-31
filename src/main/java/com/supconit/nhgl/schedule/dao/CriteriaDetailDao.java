/**
 * 
 */
package com.supconit.nhgl.schedule.dao;

import java.util.List;

import com.supconit.nhgl.schedule.entites.CriteriaDetail;

import hc.base.domains.Pageable;

/**
 * @author 
 * @create 2014-06-16 18:01:37
 * @since 
 * 
 */
 
public interface CriteriaDetailDao extends hc.orm.BasicDao<CriteriaDetail, Long> {

	/**
	 * @param pager
	 * @param condition
	 * @return
	 */
	Pageable<CriteriaDetail> findByPager(Pageable<CriteriaDetail> pager, CriteriaDetail condition);
	List<CriteriaDetail> selectByTaskCode(String taskCode);
	CriteriaDetail selectByTaskCodeAndScore(CriteriaDetail criteriaDetail);
	int deleteByTaskCode(String taskCode);
}
