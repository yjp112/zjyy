
package com.supconit.base.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.base.daos.DevicePiontsDao;
import com.supconit.base.entities.DevicePionts;
import com.supconit.base.services.DevicePiontsService;
import com.supconit.common.services.AbstractBaseBusinessService;

import hc.base.domains.Pageable;

@Service
public class DevicePiontsServiceImpl extends AbstractBaseBusinessService<DevicePionts, Long> implements DevicePiontsService{

	@Autowired
	private DevicePiontsDao		devicePiontsDao;	

    /**
	 * Get devicePionts by  ID
	 * @param id  devicePionts id
	 * @return object instance
	 */
	@Override
	@Transactional(readOnly = true)
	public DevicePionts getById(Long id) {
		if (null == id || id <= 0)
			return null;
		DevicePionts devicePionts = devicePiontsDao.getById(id);
		
		return devicePionts;
	}	

    /**
	 * delete DevicePionts by ID 
	 * @param id  devicePionts  ID 	 
	 * @return 
	 */
    @Override
	@Transactional
	public void deleteById(Long id) {
        
		if(!isAllowDelete(id))
            throw new NullPointerException("devicePionts  ?????");
		
		devicePiontsDao.deleteById(id);
	}


    /**
	 * delete DevicePionts by ID array
	 * @param ids  devicePionts ID array	 
	 * @return 
	 */
	@Override
	@Transactional
	public void deleteByIds(Long[] ids) {
		for(int i =0;i<ids.length;i++)
        {
            if(!isAllowDelete(ids[i]))
                throw new NullPointerException("devicePionts  ?????");
        }  
        
        devicePiontsDao.deleteByIds(ids);
	}

    /**
	 * Find DevicePionts list by condition
	 * @param pageNo  page number
	 * @param pageSize  page size
	 * @param condition Query condition
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public Pageable<DevicePionts> findByCondition(Pageable<DevicePionts> pager, DevicePionts condition) {
		return devicePiontsDao.findByCondition(pager, condition);
	}

	    
    
    /**
	 * update DevicePionts
	 * @param devicePionts  instance 
	 * @return
	 */
    @Override
	@Transactional
	public void update(DevicePionts devicePionts) {
		 if(!isAllowSave(devicePionts))
            throw new NullPointerException("devicePionts  ?????");
            
		devicePiontsDao.update(devicePionts);
	}
    
    /**
	 * insert DevicePionts
	 * @param devicePionts  instance	 
	 * @return
	 */
    @Override
	@Transactional
	public void insert(DevicePionts devicePionts) {
        
         if(!isAllowSave(devicePionts))
            throw new NullPointerException("devicePionts  ?????");
            
		devicePiontsDao.insert(devicePionts);
	}
    
     //Check that allows you to delete 
    private boolean isAllowDelete(Long id)
    {
        return true;
    }
    //Check that allows you to save
    private boolean isAllowSave(DevicePionts devicePionts)
    {
        return true;
    }

}
