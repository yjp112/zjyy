
package com.supconit.base.services;

import java.util.List;
import java.util.Map;

import com.supconit.base.entities.DeviceProperty;
import com.supconit.common.services.BaseBusinessService;

import hc.base.domains.Pageable;


public interface DevicePropertyService extends BaseBusinessService<DeviceProperty, Long> {	

	/**
	 * Query by condition
	 * @param pageNo  page number
	 * @param pageSize  page size
	 * @param condition Query condition
	 * @return
	 */
	Pageable<DeviceProperty> findByCondition(Pageable<DeviceProperty> pager, DeviceProperty condition);

    Long findByCode1(DeviceProperty condition);
    Long findByName1(DeviceProperty condition);

   /**
	 * delete objects 
	 * @param ids  object ID array	 
	 * @return 
	 */
	void deleteByIds(Long[] ids);

    /**
	 * save
	 * @param deviceProperty  object instance 
	 * @return
	 */
	void save(DeviceProperty deviceProperty);
	/*public List<DeviceProperty> findAll();
	public List<DeviceProperty> findByCategoryId(Long id);*/
	public List<DeviceProperty> findList(String sql,Map map);
}

