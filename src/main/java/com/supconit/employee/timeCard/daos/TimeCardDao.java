package com.supconit.employee.timeCard.daos;

import hc.base.domains.Pageable;

import com.supconit.common.daos.BaseDao;
import com.supconit.employee.timeCard.entities.TimeCard;

public interface TimeCardDao extends BaseDao<TimeCard, Long>{
	/**
	 * 分页查询
	 */
	Pageable<TimeCard> findByPage(Pageable<TimeCard> pager,TimeCard condition);
}
