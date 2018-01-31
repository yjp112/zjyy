package com.supconit.employee.timeCard.services;

import hc.base.domains.Pageable;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.employee.timeCard.entities.TimeCard;

public interface TimeCardService extends BaseBusinessService<TimeCard, Long> {
	/**
	 * 分页查询
	 */
	Pageable<TimeCard> findByPage(Pageable<TimeCard> pager,TimeCard condition);
}
