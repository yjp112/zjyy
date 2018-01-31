
package com.supconit.base.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.base.daos.DeviceExtendPropertyDao;
import com.supconit.base.entities.DeviceExtendProperty;
import com.supconit.base.services.DeviceExtendPropertyService;
import com.supconit.common.services.AbstractBaseBusinessService;

import hc.base.domains.Pageable;

@Service
public class DeviceExtendPropertyServiceImpl extends AbstractBaseBusinessService<DeviceExtendProperty, Long> implements DeviceExtendPropertyService{

	@Autowired
	private DeviceExtendPropertyDao		deviceExtendPropertyDao;	

    /**
	 * Get deviceExtendProperty by  ID
	 * @param id  deviceExtendProperty id
	 * @return object instance
	 */
	@Override
	@Transactional(readOnly = true)
	public DeviceExtendProperty getById(Long id) {
		if (null == id || id <= 0)
			return null;
		DeviceExtendProperty deviceExtendProperty = deviceExtendPropertyDao.getById(id);
		
		return deviceExtendProperty;
	}	

    /**
	 * delete DeviceExtendProperty by ID 
	 * @param id  deviceExtendProperty  ID 	 
	 * @return 
	 */
    @Override
	@Transactional
	public void deleteById(Long id) {
        
		if(!isAllowDelete(id))
            throw new NullPointerException("deviceExtendProperty  ?????");
		
		deviceExtendPropertyDao.deleteById(id);
	}


    /**
	 * delete DeviceExtendProperty by ID array
	 * @param ids  deviceExtendProperty ID array	 
	 * @return 
	 */
	@Override
	@Transactional
	public void deleteByIds(Long[] ids) {
		for(int i =0;i<ids.length;i++)
        {
            if(!isAllowDelete(ids[i]))
                throw new NullPointerException("deviceExtendProperty  ?????");
        }  
        
        deviceExtendPropertyDao.deleteByIds(ids);
	}

    /**
	 * Find DeviceExtendProperty list by condition
	 * @param pageNo  page number
	 * @param pageSize  page size
	 * @param condition Query condition
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public Pageable<DeviceExtendProperty> findByCondition(Pageable<DeviceExtendProperty> pager, DeviceExtendProperty condition) {
		return deviceExtendPropertyDao.findByCondition(pager, condition);
	}

	    
    
    /**
	 * update DeviceExtendProperty
	 * @param deviceExtendProperty  instance 
	 * @return
	 */
    @Override
	@Transactional
	public void update(DeviceExtendProperty deviceExtendProperty) {
		 if(!isAllowSave(deviceExtendProperty))
            throw new NullPointerException("deviceExtendProperty  ?????");
            
		deviceExtendPropertyDao.update(deviceExtendProperty);
	}
    
    /**
	 * insert DeviceExtendProperty
	 * @param deviceExtendProperty  instance	 
	 * @return
	 */
    @Override
	@Transactional
	public void insert(DeviceExtendProperty deviceExtendProperty) {
        
         if(!isAllowSave(deviceExtendProperty))
            throw new NullPointerException("deviceExtendProperty  ?????");
            
		deviceExtendPropertyDao.insert(deviceExtendProperty);
	}
    
     //Check that allows you to delete 
    private boolean isAllowDelete(Long id)
    {
        return true;
    }
    //Check that allows you to save
    private boolean isAllowSave(DeviceExtendProperty deviceExtendProperty)
    {
        return true;
    }

}
