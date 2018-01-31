
package com.supconit.base.services;

import com.supconit.base.entities.DeviceCategoryProperty;
import com.supconit.common.services.BaseBusinessService;

import hc.base.domains.Pageable;


public interface DeviceCategoryPropertyService extends BaseBusinessService<DeviceCategoryProperty, Long> {	

	/**
	 * Query by condition
	 * @param pageNo  page number
	 * @param pageSize  page size
	 * @param condition Query condition
	 * @return
	 */
	Pageable<DeviceCategoryProperty> findByCondition(Pageable<DeviceCategoryProperty> pager, DeviceCategoryProperty condition);



   /**
	 * delete objects 
	 * @param ids  object ID array	 
	 * @return 
	 */
	void deleteByIds(Long[] ids);

    /**
	 * save
	 * @param deviceCategoryProperty  object instance 
	 * @return
	 */
	void save(DeviceCategoryProperty deviceCategoryProperty);

}

