
package com.supconit.base.services.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.base.daos.DevicePropertyDao;
import com.supconit.base.entities.DeviceProperty;
import com.supconit.base.services.DevicePropertyService;
import com.supconit.common.exceptions.BusinessDoneException;
import com.supconit.common.services.AbstractBaseBusinessService;

import hc.base.domains.Pageable;

@Service
public class DevicePropertyServiceImpl extends AbstractBaseBusinessService<DeviceProperty, Long> implements DevicePropertyService{

	@Autowired
	private DevicePropertyDao		devicePropertyDao;	

    /**
	 * Get deviceProperty by  ID
	 * @param id  deviceProperty id
	 * @return object instance
	 */
	@Override
	@Transactional(readOnly = true)
	public DeviceProperty getById(Long id) {
		if (null == id || id <= 0)
			return null;
		DeviceProperty deviceProperty = devicePropertyDao.getById(id);
		
		return deviceProperty;
	}	

    /**
	 * delete DeviceProperty by ID 
	 * @param id  deviceProperty  ID 	 
	 * @return 
	 */
    @Override
	@Transactional
	public void deleteById(Long id) {
        
		isAllowDelete(id);
		
		devicePropertyDao.deleteById(id);
	}


    /**
	 * delete DeviceProperty by ID array
	 * @param ids  deviceProperty ID array	 
	 * @return 
	 */
	@Override
	@Transactional
	public void deleteByIds(Long[] ids) {
		for(int i =0;i<ids.length;i++)
        {
            isAllowDelete(ids[i]);
        }  
        
        devicePropertyDao.deleteByIds(ids);
	}

    /**
	 * Find DeviceProperty list by condition
	 * @param pageNo  page number
	 * @param pageSize  page size
	 * @param condition Query condition
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public Pageable<DeviceProperty> findByCondition(Pageable<DeviceProperty> pager, DeviceProperty condition) {
		return devicePropertyDao.findByCondition(pager, condition);
	}

	    
    
    /**
	 * update DeviceProperty
	 * @param deviceProperty  instance 
	 * @return
	 */
    @Override
	@Transactional
	public void update(DeviceProperty deviceProperty) {
		isAllowSave(deviceProperty);         
		devicePropertyDao.update(deviceProperty);
	}
    
    /**
	 * insert DeviceProperty
	 * @param deviceProperty  instance	 
	 * @return
	 */
    @Override
	@Transactional
	public void insert(DeviceProperty deviceProperty) {  
    	isAllowSave(deviceProperty);      
		devicePropertyDao.insert(deviceProperty);
	}
    
     //Check that allows you to delete 
    private void isAllowDelete(Long id)
    {
    	long count=devicePropertyDao.findUseCount(id);
        if(count>0){
            throw new BusinessDoneException("该属性已在设备类别管理中使用，不能删除。");  
        }
    }
    //Check that allows you to save
    private void isAllowSave(DeviceProperty deviceProperty)
    {
    	DeviceProperty dp;
    	long count;
    	if(deviceProperty.getId()==null){
    		//insert
    		dp = new DeviceProperty();
        	dp.setPropertyCode(deviceProperty.getPropertyCode());
        	//count=devicePropertyDao.findByCondition(1, 10, dp).getTotal();
            count = devicePropertyDao.findByCode1(dp);
        	if(count>0){
                throw new BusinessDoneException("属性编码["+deviceProperty.getPropertyCode()+"]已经被占用。");  
            }

        	
        	dp.setPropertyName(deviceProperty.getPropertyName());
        	//count=devicePropertyDao.findByCondition(1, 10, dp).getTotal();
        	count = devicePropertyDao.findByName1(dp);
            if(count>0){
                throw new BusinessDoneException("属性名称["+deviceProperty.getPropertyName()+"]已经被占用。");  
            }
    	}else{
        	//update
        	dp = new DeviceProperty();
        	DeviceProperty dpOld =  devicePropertyDao.getById(deviceProperty.getId());
        	if(!dpOld.getPropertyName().equals(deviceProperty.getPropertyName())){
            	dp.setPropertyName(deviceProperty.getPropertyName());
            	//count=devicePropertyDao.findByCondition(1, 10, dp).getTotal();
            	count = devicePropertyDao.findByName1(dp);
            	if(count>0){
                    throw new BusinessDoneException("属性名称["+deviceProperty.getPropertyName()+"]已经被占用。");  
                }
        	}
    	}

    }
    /*@Override
	public List<DeviceProperty> findAll() {
		return devicePropertyDao.findAll();
	}
    @Override
	public List<DeviceProperty> findByCategoryId(Long id) {
		return devicePropertyDao.findByCategoryId(id);
	} */
    @Override
	public List<DeviceProperty> findList(String sql,Map map) {
		return devicePropertyDao.findList(sql,map);
	}

	@Override
	public Long findByCode1(DeviceProperty condition) {
		// TODO Auto-generated method stub
		return devicePropertyDao.findByCode1(condition);
	} 
	@Override
	public Long findByName1(DeviceProperty condition) {
		// TODO Auto-generated method stub
		return devicePropertyDao.findByName1(condition);
	} 
}
