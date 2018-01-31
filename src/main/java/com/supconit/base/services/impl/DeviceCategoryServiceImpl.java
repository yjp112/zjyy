
package com.supconit.base.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.base.daos.AttachmentDao;
import com.supconit.base.daos.DeviceCategoryDao;
import com.supconit.base.daos.DeviceCategoryPropertyDao;
import com.supconit.base.daos.DeviceDao;
import com.supconit.base.entities.DeviceCategory;
import com.supconit.base.entities.DeviceCategoryProperty;
import com.supconit.base.services.DeviceCategoryService;
import com.supconit.common.exceptions.BusinessDoneException;
import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.common.utils.Constant;
import com.supconit.common.utils.UtilTool;
import com.supconit.repair.entities.RepairEvtCategory;

import hc.base.domains.Pageable;

@Service
public class DeviceCategoryServiceImpl extends AbstractBaseBusinessService<DeviceCategory, Long> implements DeviceCategoryService{

	@Autowired
	private DeviceCategoryDao		deviceCategoryDao;	
	@Autowired
	private DeviceCategoryPropertyDao		deviceCategoryPropertyDao;	
	@Autowired
	private AttachmentDao		attachmentDao;	
	@Autowired
	private DeviceDao		deviceDao;	
	
    /**
	 * Get deviceCategory by  ID
	 * @param id  deviceCategory id
	 * @return object instance
	 */
	@Override
	@Transactional(readOnly = true)
	public DeviceCategory getById(Long id) {
		if (null == id || id <= 0)
			return null;
		DeviceCategory deviceCategory = deviceCategoryDao.getById(id);
		
		return deviceCategory;
	}	
    @Override
	public DeviceCategory getByCode(String code) {
		return deviceCategoryDao.getByCode(code);
	}
    /**
	 * delete DeviceCategory by ID 
	 * @param id  deviceCategory  ID 	 
	 * @return 
	 */
    @Override
	@Transactional
	public void deleteById(Long id) {
        
    	isAllowDelete(id);
		
		deviceCategoryDao.deleteByIds(new Long[]{id});
	}


    /**
	 * delete DeviceCategory by ID array
	 * @param ids  deviceCategory ID array	 
	 * @return 
	 */
	@Override
	@Transactional
	public void deleteByIds(Long[] ids) {
		for(int i =0;i<ids.length;i++)
        {
            isAllowDelete(ids[i]);
        }          
        deviceCategoryDao.deleteByIds(ids);
		for(int i =0;i<ids.length;i++)
        {
            deviceCategoryPropertyDao.deleteByCategoryId(ids[i]);
            attachmentDao.deleteByObj(ids[i], Constant.ATTACHEMENT_DEVICE_CATEGORY);
        }  
        
	}

    /**
	 * Find DeviceCategory list by condition
	 * @param pageNo  page number
	 * @param pageSize  page size
	 * @param condition Query condition
	 * @return
	 */
	@Override
	public Pageable<DeviceCategory> findByCondition(Pageable<DeviceCategory> pager, DeviceCategory condition) {
        if(condition.getParentId()!=null){//递归查询子节点
            List<Long> childIds=deviceCategoryDao.findChildIds(condition.getParentId());
            condition.setChildIds(childIds);
          }	
		return deviceCategoryDao.findByCondition(pager, condition);
	}

	    
    
    /**
	 * update DeviceCategory
	 * @param deviceCategory  instance 
	 * @return
	 */
    @Override
	@Transactional
	public void update(DeviceCategory deviceCategory) {        
		deviceCategoryDao.update(deviceCategory);
	}
    /*@Override
	@Transactional
	public void update(DeviceCategory deviceCategory,Long[] propertyIds,Long[] propertySorts) {
		 if(!isAllowSave(deviceCategory))
            throw new NullPointerException("deviceCategory  ?????");
            
		deviceCategoryDao.update(deviceCategory);
		updateDeviceCategoryProperty(deviceCategory,propertyIds,propertySorts);	
	}*/
    @Override
	@Transactional
	public void update(DeviceCategory deviceCategory,Long[] propertyIds,Long[] propertySorts,String[] fileorignal,String[] filename,String[] delfiles,String fileLength){
    	long pid = deviceCategory.getParentId();
    	if(pid !=0 ){
    		List<Long> list = deviceCategoryDao.findChildIds(deviceCategory.getId());
        	if(list.contains(pid)){
        		throw new BusinessDoneException("上级类别不能设置为自身或自身的子类别"); 
        	}
    	}
    	String FName = deviceCategory.getCategoryName();
		while (pid != 0L){
			DeviceCategory tempDevice = deviceCategoryDao.getById(pid);
			if (tempDevice != null ){
				FName = tempDevice.getCategoryName()+ "→" + FName ;
				pid = tempDevice.getParentId();
			}					
		}
		deviceCategory.setFullLevelName(FName);
		deviceCategoryDao.update(deviceCategory);
		List<DeviceCategory> list = deviceCategoryDao.findChildIdsEntity(deviceCategory.getId());
    	for (DeviceCategory device : list) {
    		if(device.getId().longValue()==deviceCategory.getId()){
    			continue;
    		}
    		long pId = device.getParentId();
    		String FNames = device.getCategoryName();
    		while (pId != 0L){
    			DeviceCategory tempDevice = deviceCategoryDao.getById(pId);
    			if (tempDevice != null ){
    				FNames = tempDevice.getCategoryName()+ "→" + FNames ;
    				pId = tempDevice.getParentId();
    			}
    		}
    		device.setFullLevelName(FNames);
    		deviceCategoryDao.update(device);
		}
		updateDeviceCategoryProperty(deviceCategory,propertyIds,propertySorts);	
        //Save 随机档案
		if(fileorignal!=null && fileorignal.length ==1 && !UtilTool.isEmpty(fileorignal[0])){
  			attachmentDao.saveAttachements(deviceCategory.getId(),Constant.ATTACHEMENT_DEVICE_CATEGORY,fileorignal, filename,delfiles,fileLength);
  		}		
	}
    public void updateDeviceCategoryProperty(DeviceCategory deviceCategory,Long[] propertyIds,Long[] propertySorts){
    	deviceCategoryPropertyDao.deleteByCategoryId(deviceCategory.getId());
		if(deviceCategory.getLastNode()==1 && propertyIds!=null && propertySorts!=null && propertyIds.length==propertySorts.length){
			for(int i=0;i<propertyIds.length;i++){
				DeviceCategoryProperty dcp = new DeviceCategoryProperty();
				dcp.setCategoryId(deviceCategory.getId());
				dcp.setPropertyId(propertyIds[i]);
				dcp.setPropertySort(propertySorts[i]);
				deviceCategoryPropertyDao.insert(dcp);
			}
		}	
    }
    /**
	 * insert DeviceCategory
	 * @param deviceCategory  instance	 
	 * @return
	 */
    @Override
	@Transactional
	public void insert(DeviceCategory deviceCategory) {
        
        isAllowSave(deviceCategory);
            
		deviceCategoryDao.insert(deviceCategory);

	}
    /*@Override
	@Transactional
	public void insert(DeviceCategory deviceCategory,Long[] propertyIds,Long[] propertySorts) {
        
    	if(!isAllowSave(deviceCategory))
            throw new NullPointerException("deviceCategory  ?????");
		deviceCategoryDao.insert(deviceCategory);
		updateDeviceCategoryProperty(deviceCategory,propertyIds,propertySorts);	
	}*/
    @Override
  	@Transactional
  	public void insert(DeviceCategory deviceCategory,Long[] propertyIds,Long[] propertySorts,String[] fileorignal,String[] filename,String[] delfiles, String fileLength) {
      	isAllowSave(deviceCategory);
      	Long pId = deviceCategory.getParentId();
		String FName = deviceCategory.getCategoryName();
		while (pId != 0L){
			DeviceCategory tempDevice = deviceCategoryDao.getById(pId);
			if (tempDevice != null ){
				FName = tempDevice.getCategoryName()+ "→" + FName ;
				pId = tempDevice.getParentId();
			}					
		}
		deviceCategory.setFullLevelName(FName); 
  		deviceCategoryDao.insert(deviceCategory);
  		updateDeviceCategoryProperty(deviceCategory,propertyIds,propertySorts);	
        //Save 随机档案
  		if(fileorignal!=null && fileorignal.length ==1 && !UtilTool.isEmpty(fileorignal[0])){
  			attachmentDao.saveAttachements(deviceCategory.getId(),Constant.ATTACHEMENT_DEVICE_CATEGORY,fileorignal, filename,delfiles,fileLength);
  		}		
  	}
     //Check that allows you to delete 
    private void isAllowDelete(Long id)
    {
    	long count=deviceDao.findCountByCategoryId(id);
        if(count>0){
            throw new BusinessDoneException("该类别已使用，不能删除。");  
        }
        count=deviceCategoryDao.findChildrenCount(id);
        if(count>0){
            throw new BusinessDoneException("该类别含有子类别，不能删除。");
        }
        
    }
    //Check that allows you to save
    private void isAllowSave(DeviceCategory deviceCategory)
    {
    	if(deviceCategory.getId()==null){
    		//insert
    		DeviceCategory dp=deviceCategoryDao.getByCode(deviceCategory.getCategoryCode());
            if(dp!=null){
                throw new BusinessDoneException("类别编码["+deviceCategory.getCategoryCode()+"]已经被占用。");  
            }
    	}
    }
	@Override
	public List<DeviceCategory> findAll() {
		// TODO Auto-generated method stub
		return deviceCategoryDao.findAll();
	}
	@Override
	public List<DeviceCategory> findByCategoryCode(String categoryCode) {
		return deviceCategoryDao.findByCategoryCode(categoryCode);
	}

	@Override
	public List<DeviceCategory> findByParentId(long parentId) {
		return deviceCategoryDao.findByParentId(parentId);
	}
}
