
package com.supconit.base.services;

import com.supconit.base.entities.DeviceExtendProperty;
import com.supconit.common.services.BaseBusinessService;

import hc.base.domains.Pageable;


public interface DeviceExtendPropertyService extends BaseBusinessService<DeviceExtendProperty, Long> {	

	/**
	 * Query by condition
	 * @param pageNo  page number
	 * @param pageSize  page size
	 * @param condition Query condition
	 * @return
	 */
	Pageable<DeviceExtendProperty> findByCondition(Pageable<DeviceExtendProperty> pager, DeviceExtendProperty condition);



   /**
	 * delete objects 
	 * @param ids  object ID array	 
	 * @return 
	 */
	void deleteByIds(Long[] ids);

    /**
	 * save
	 * @param deviceExtendProperty  object instance 
	 * @return
	 */
	void save(DeviceExtendProperty deviceExtendProperty);

}

