
package com.supconit.base.services;

import java.util.List;

import com.supconit.base.entities.DeviceCategory;
import com.supconit.common.services.BaseBusinessService;

import hc.base.domains.Pageable;


public interface DeviceCategoryService extends BaseBusinessService<DeviceCategory, Long> {	

	/**
	 * Query by condition
	 * @param pageNo  page number
	 * @param pageSize  page size
	 * @param condition Query condition
	 * @return
	 */
	Pageable<DeviceCategory> findByCondition(Pageable<DeviceCategory> pager, DeviceCategory condition);



   /**
	 * delete objects 
	 * @param ids  object ID array	 
	 * @return 
	 */
	void deleteByIds(Long[] ids);

    /**
	 * save
	 * @param deviceCategory  object instance 
	 * @return
	 */
	void save(DeviceCategory deviceCategory);
	public List<DeviceCategory> findAll();
	//public void insert(DeviceCategory deviceCategory,Long[] propertyIds,Long[] propertySorts);
	//public void update(DeviceCategory deviceCategory,Long[] propertyIds,Long[] propertySorts);
 	public void insert(DeviceCategory deviceCategory,Long[] propertyIds,Long[] propertySorts,String[] fileorignal,String[] filename,String[] delfiles, String fileLength);
 	public void update(DeviceCategory deviceCategory,Long[] propertyIds,Long[] propertySorts,String[] fileorignal,String[] filename,String[] delfiles, String fileLength);
	public DeviceCategory getByCode(String code);
	public List<DeviceCategory> findByCategoryCode(String categoryCode);
	List<DeviceCategory> findByParentId(long parentId);
}

