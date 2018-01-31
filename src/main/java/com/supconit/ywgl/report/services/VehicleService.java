package com.supconit.ywgl.report.services;


import com.supconit.common.services.BaseBusinessService;
import com.supconit.ywgl.report.entities.Vehicle;

import hc.base.domains.Pageable;


public interface VehicleService extends BaseBusinessService<Vehicle, Long>{ 
	/**
	 * Query by condition
	 * @param pageNo  page number
	 * @param pageSize  page size
	 * @param condition Query condition
	 * @return
	 */
	Pageable<Vehicle> findByCondition(Pageable<Vehicle> pager, Vehicle condition);
	
}
