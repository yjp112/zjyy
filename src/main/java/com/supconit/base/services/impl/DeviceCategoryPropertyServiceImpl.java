
package com.supconit.base.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.base.daos.DeviceCategoryPropertyDao;
import com.supconit.base.entities.DeviceCategoryProperty;
import com.supconit.base.services.DeviceCategoryPropertyService;
import com.supconit.common.services.AbstractBaseBusinessService;

import hc.base.domains.Pageable;

@Service
public class DeviceCategoryPropertyServiceImpl extends AbstractBaseBusinessService<DeviceCategoryProperty, Long> implements DeviceCategoryPropertyService{

	@Autowired
	private DeviceCategoryPropertyDao		deviceCategoryPropertyDao;	

    /**
	 * Get deviceCategoryProperty by  ID
	 * @param id  deviceCategoryProperty id
	 * @return object instance
	 */
	@Override
	@Transactional(readOnly = true)
	public DeviceCategoryProperty getById(Long id) {
		if (null == id || id <= 0)
			return null;
		DeviceCategoryProperty deviceCategoryProperty = deviceCategoryPropertyDao.getById(id);
		
		return deviceCategoryProperty;
	}	

    /**
	 * delete DeviceCategoryProperty by ID 
	 * @param id  deviceCategoryProperty  ID 	 
	 * @return 
	 */
    @Override
	@Transactional
	public void deleteById(Long id) {
        
		if(!isAllowDelete(id))
            throw new NullPointerException("deviceCategoryProperty  ?????");
		
		deviceCategoryPropertyDao.deleteByIds(new Long[]{id});
	}


    /**
	 * delete DeviceCategoryProperty by ID array
	 * @param ids  deviceCategoryProperty ID array	 
	 * @return 
	 */
	@Override
	@Transactional
	public void deleteByIds(Long[] ids) {
		for(int i =0;i<ids.length;i++)
        {
            if(!isAllowDelete(ids[i]))
                throw new NullPointerException("deviceCategoryProperty  ?????");
        }  
        
        deviceCategoryPropertyDao.deleteByIds(ids);
	}

    /**
	 * Find DeviceCategoryProperty list by condition
	 * @param pageNo  page number
	 * @param pageSize  page size
	 * @param condition Query condition
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public Pageable<DeviceCategoryProperty> findByCondition(Pageable<DeviceCategoryProperty> pager, DeviceCategoryProperty condition) {
		return deviceCategoryPropertyDao.findByCondition(pager, condition);
	}

	    
    
    /**
	 * update DeviceCategoryProperty
	 * @param deviceCategoryProperty  instance 
	 * @return
	 */
    @Override
	@Transactional
	public void update(DeviceCategoryProperty deviceCategoryProperty) {
		 if(!isAllowSave(deviceCategoryProperty))
            throw new NullPointerException("deviceCategoryProperty  ?????");
            
		deviceCategoryPropertyDao.update(deviceCategoryProperty);
	}
    
    /**
	 * insert DeviceCategoryProperty
	 * @param deviceCategoryProperty  instance	 
	 * @return
	 */
    @Override
	@Transactional
	public void insert(DeviceCategoryProperty deviceCategoryProperty) {
        
         if(!isAllowSave(deviceCategoryProperty))
            throw new NullPointerException("deviceCategoryProperty  ?????");
            
		deviceCategoryPropertyDao.insert(deviceCategoryProperty);
	}
    
     //Check that allows you to delete 
    private boolean isAllowDelete(Long id)
    {
        return true;
    }
    //Check that allows you to save
    private boolean isAllowSave(DeviceCategoryProperty deviceCategoryProperty)
    {
        return true;
    }

}
