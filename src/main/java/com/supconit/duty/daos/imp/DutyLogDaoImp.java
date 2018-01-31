package com.supconit.duty.daos.imp;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.duty.daos.DutyLogDao;
import com.supconit.duty.entities.DutyLog;

import hc.base.domains.Pageable;
@Repository
public class DutyLogDaoImp extends AbstractBaseDao<DutyLog,Long> implements DutyLogDao{

	private static final String	NAMESPACE = DutyLog.class.getName();
	@Override
	protected String getNamespace() {
		// TODO Auto-generated method stub
		return NAMESPACE;
	}

	@Override
	public Pageable<DutyLog> findByCondition(Pageable<DutyLog> pager,
			DutyLog condition) {
		// TODO Auto-generated method stub
		return findByPager(pager, "findByConditions", "countByConditions", condition);
	}

	@Override
	public void deleteByIds(Long[] ids) {
		// TODO Auto-generated method stub
		
	}

}
