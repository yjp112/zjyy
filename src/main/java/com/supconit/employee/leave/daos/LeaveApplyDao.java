package com.supconit.employee.leave.daos;

import com.supconit.common.daos.BaseDao;
import com.supconit.employee.leave.entities.LeaveApply;

import hc.base.domains.Pageable;


public interface LeaveApplyDao extends BaseDao<LeaveApply, Long>{
	/**
	 * 分页查询
	 */
	Pageable<LeaveApply> findByPage(Pageable<LeaveApply> pager,LeaveApply condition);
	/**
     * 查询一段时间内的请假天数和小时
     */
	LeaveApply sumByConditions(LeaveApply condition);
}
