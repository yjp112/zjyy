
package com.supconit.base.services;
 

import com.supconit.base.entities.DeviceStartStopRecords;
import com.supconit.common.services.BaseBusinessService;

import hc.base.domains.Pageable;



public interface DeviceStartStopRecordsService extends BaseBusinessService<DeviceStartStopRecords, Long> {	

	/**
	 * Query by condition
	 * @param pageNo  page number
	 * @param pageSize  page size
	 * @param condition Query condition
	 * @return
	 */
	Pageable<DeviceStartStopRecords> findByCondition(Pageable<DeviceStartStopRecords> pager, DeviceStartStopRecords condition);

}

