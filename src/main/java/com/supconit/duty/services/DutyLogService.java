package com.supconit.duty.services;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.duty.entities.DutyLog;

import hc.base.domains.Pageable;

public interface DutyLogService extends BaseBusinessService<DutyLog, Long>{
	/**
	 * Query by condition
	 * @param pageNo  page number
	 * @param pageSize  page size
	 * @param condition Query condition
	 * @return
	 */
	Pageable<DutyLog> findByCondition(Pageable<DutyLog> pager, DutyLog condition);
	

	   /**
		 * delete objects 
		 * @param ids  object ID array	 
	 * @param type 
		 * @return 
		 */
		void deleteByIds(Long[] ids, Long type);
	
}
