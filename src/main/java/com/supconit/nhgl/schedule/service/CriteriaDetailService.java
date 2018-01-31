/**
 * 
 */
package com.supconit.nhgl.schedule.service;

import java.util.List;

import com.supconit.nhgl.schedule.entites.CriteriaDetail;

import hc.base.domains.Pageable;

/**
 * @author 
 * @create 2014-06-16 18:01:37 
 * @since 
 * 
 */
public interface CriteriaDetailService extends hc.orm.BasicOrmService<CriteriaDetail, Long> {

	/**
	 * 分页按条件查询。
	 * @param pager
	 * @param condition
	 * @return
	 */
	Pageable<CriteriaDetail> find(Pageable<CriteriaDetail> pager, CriteriaDetail condition);
	List<CriteriaDetail> selectByTaskCode(String taskCode);
	CriteriaDetail selectByTaskCodeAndScore(CriteriaDetail criteriaDetail);
	int deleteByTaskCode(String taskCode);
}
