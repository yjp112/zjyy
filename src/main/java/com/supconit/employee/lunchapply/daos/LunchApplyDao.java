package com.supconit.employee.lunchapply.daos;

import java.util.Date;

import com.supconit.common.daos.BaseDao;
import com.supconit.employee.lunchapply.entities.LunchApply;

import hc.base.domains.Pageable;

public interface LunchApplyDao extends BaseDao<LunchApply, Long>{
	/**
	 * 分页查询
	 */
	Pageable<LunchApply> findByPage(Pageable<LunchApply> pager,LunchApply condition);
	/**
	 * 查询当天预定工作餐数
	 */
	int countLunchNumOfCurrentDay(Date currentDay);
}
