
package com.supconit.base.services;

import com.supconit.base.entities.DevicePionts;
import com.supconit.common.services.BaseBusinessService;

import hc.base.domains.Pageable;


public interface DevicePiontsService extends BaseBusinessService<DevicePionts, Long> {	

	/**
	 * Query by condition
	 * @param pageNo  page number
	 * @param pageSize  page size
	 * @param condition Query condition
	 * @return
	 */
	Pageable<DevicePionts> findByCondition(Pageable<DevicePionts> pager, DevicePionts condition);



   /**
	 * delete objects 
	 * @param ids  object ID array	 
	 * @return 
	 */
	void deleteByIds(Long[] ids);

    /**
	 * save
	 * @param devicePionts  object instance 
	 * @return
	 */
	void save(DevicePionts devicePionts);

}

