
package com.supconit.base.services;

import com.supconit.base.entities.Device;
import com.supconit.base.entities.DeviceChange;
import com.supconit.common.services.BaseBusinessService;

import hc.base.domains.Pageable;


public interface DeviceChangeService extends BaseBusinessService<DeviceChange, Long> {	

	/**
	 * Query by condition
	 * @param pageNo  page number
	 * @param pageSize  page size
	 * @param condition Query condition
	 * @return
	 */
	Pageable<DeviceChange> findByCondition(Pageable<DeviceChange> pager, DeviceChange condition);



   /**
	 * delete objects 
	 * @param ids  object ID array	 
	 * @return 
	 */
	void deleteByIds(Long[] ids);

    /**
	 * save
	 * @param deviceChange  object instance 
	 * @return
	 */
	void save(DeviceChange deviceChange);

	public void insert(DeviceChange deviceChange,Device device);
}

