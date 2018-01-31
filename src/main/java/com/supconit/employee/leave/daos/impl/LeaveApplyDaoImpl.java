package com.supconit.employee.leave.daos.impl;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.employee.leave.daos.LeaveApplyDao;
import com.supconit.employee.leave.entities.LeaveApply;

import hc.base.domains.Pageable;


@Repository
public class LeaveApplyDaoImpl extends AbstractBaseDao<LeaveApply, Long> implements LeaveApplyDao {
	private static final String	NAMESPACE = LeaveApply.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	
	@Override
	public Pageable<LeaveApply> findByPage(Pageable<LeaveApply> pager,LeaveApply condition) {
		return findByPager(pager, "findByConditions", "countByConditions", condition);
	}

	@Override
	public LeaveApply sumByConditions(LeaveApply condition) {
		return selectOne("sumByConditions", condition);
	}
	
	

}
