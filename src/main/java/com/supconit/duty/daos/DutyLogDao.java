package com.supconit.duty.daos;

import com.supconit.common.daos.BaseDao;
import com.supconit.duty.entities.DutyLog;

import hc.base.domains.Pageable;


public interface DutyLogDao extends BaseDao<DutyLog, Long>{
	
		Pageable<DutyLog> findByCondition(Pageable<DutyLog> pager, DutyLog condition);
		

		void deleteByIds(Long[] ids);
}
