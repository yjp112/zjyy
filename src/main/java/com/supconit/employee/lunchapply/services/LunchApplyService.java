package com.supconit.employee.lunchapply.services;

import java.util.Date;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.employee.lunchapply.entities.LunchApply;

import hc.base.domains.Pageable;

public interface LunchApplyService extends BaseBusinessService<LunchApply, Long> {
	/**
	 * 新增(涉及流程)
	 */
	void insertLunchApply(LunchApply task);
	/**
	 * 修改(涉及流程)
	 */
	void updateLunchApply(LunchApply task);
	/**
	 * 分页查询
	 */
	Pageable<LunchApply> findByPage(Pageable<LunchApply> pager,LunchApply condition);
	/**
	 * 查询当天预定工作餐数
	 */
	int countLunchNumOfCurrentDay(Date currentDay);
}
