
package com.supconit.base.services.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.base.daos.DeviceChangeDao;
import com.supconit.base.daos.DeviceDao;
import com.supconit.base.entities.Device;
import com.supconit.base.entities.DeviceChange;
import com.supconit.base.services.DeviceChangeService;
import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.utils.StringUtil;
import com.supconit.common.utils.UtilTool;

import hc.base.domains.Pageable;

@Service
public class DeviceChangeServiceImpl extends AbstractBaseBusinessService<DeviceChange, Long> implements DeviceChangeService{

	@Autowired
	private DeviceChangeDao		deviceChangeDao;	
	@Autowired
	private DeviceDao		deviceDao;	

    /**
	 * Get deviceChange by  ID
	 * @param id  deviceChange id
	 * @return object instance
	 */
	@Override
	@Transactional(readOnly = true)
	public DeviceChange getById(Long id) {
		if (null == id || id <= 0)
			return null;
		DeviceChange deviceChange = deviceChangeDao.getById(id);
		
		return deviceChange;
	}	

    /**
	 * delete DeviceChange by ID 
	 * @param id  deviceChange  ID 	 
	 * @return 
	 */
    @Override
	@Transactional
	public void deleteById(Long id) {
        
		if(!isAllowDelete(id))
            throw new NullPointerException("deviceChange  ?????");
		
		deviceChangeDao.deleteById(id);
	}


    /**
	 * delete DeviceChange by ID array
	 * @param ids  deviceChange ID array	 
	 * @return 
	 */
	@Override
	@Transactional
	public void deleteByIds(Long[] ids) {
		for(int i =0;i<ids.length;i++)
        {
            if(!isAllowDelete(ids[i]))
                throw new NullPointerException("deviceChange  ?????");
        }  
        
        deviceChangeDao.deleteByIds(ids);
	}

    /**
	 * Find DeviceChange list by condition
	 * @param pageNo  page number
	 * @param pageSize  page size
	 * @param condition Query condition
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public Pageable<DeviceChange> findByCondition(Pageable<DeviceChange> pager, DeviceChange condition) {
		return deviceChangeDao.findByCondition(pager, condition);
	}

	    
    
    /**
	 * update DeviceChange
	 * @param deviceChange  instance 
	 * @return
	 */
    @Override
	@Transactional
	public void update(DeviceChange deviceChange) {
		 if(!isAllowSave(deviceChange))
            throw new NullPointerException("deviceChange  ?????");
            
		deviceChangeDao.update(deviceChange);
	}
    
    /**
	 * insert DeviceChange
	 * @param deviceChange  instance	 
	 * @return
	 */
    @Override
	@Transactional
	public void insert(DeviceChange deviceChange) {
        
         if(!isAllowSave(deviceChange))
            throw new NullPointerException("deviceChange  ?????");
            
		deviceChangeDao.insert(deviceChange);
	}
    /**
	 * insert DeviceChange
	 * @param deviceChange  instance	 
	 * @return
	 */
    @Override
	@Transactional
	public void insert(DeviceChange deviceChange,Device dNew) {
    	Device dOld =  deviceDao.getById(dNew.getId());
    	boolean isChange = false;
    	if(!isSame(dOld.getUseDepartmentId(),dNew.getUseDepartmentId())){
    		DeviceChange dc =  deviceChange;
    		dc.setChangeProperty("使用部门");
    		dc.setOldValue(dOld.getUseDepartmentName()==null?"":dOld.getUseDepartmentName().toString());	
    		dc.setNewValue(dNew.getUseDepartmentName()==null?"":dNew.getUseDepartmentName().toString());	
    		deviceChangeDao.insert(dc);//插入设备变更表
    		dOld.setUseDepartmentId(dNew.getUseDepartmentId());
    		isChange = true;
    	}
    	if(!isSame(dOld.getManagePersonIds(),dNew.getManagePersonIds())){
    		DeviceChange dc =  deviceChange;
    		dc.setChangeProperty("管理负责人");
    		dc.setOldValue(UtilTool.nullToEmpty(dOld.getManagePersonName()));
    		dc.setNewValue(UtilTool.nullToEmpty(dNew.getManagePersonName()));	
    		deviceChangeDao.insert(dc);
    		dOld.setManagePersonIds(dNew.getManagePersonIds());
    		dOld.setManagePersonName(dNew.getManagePersonName());
    		isChange = true;
    	}
    	if(!isSame(dOld.getManageDepartmentId(),dNew.getManageDepartmentId())){
    		DeviceChange dc =  deviceChange;
    		dc.setChangeProperty("管理部门");
    		dc.setOldValue(dOld.getManageDepartmentName()==null?"":dOld.getManageDepartmentName().toString());	
    		dc.setNewValue(dNew.getManageDepartmentName()==null?"":dNew.getManageDepartmentName().toString());	
    		deviceChangeDao.insert(dc);
    		dOld.setManageDepartmentId(dNew.getManageDepartmentId());
    		isChange = true;
    	}
    	if(!isSame(dOld.getLocationId(),dNew.getLocationId())){
    		DeviceChange dc =  deviceChange;
    		dc.setChangeProperty("安装位置");
    		dc.setOldValue(UtilTool.nullToEmpty(dOld.getLocationName()));
    		dc.setNewValue(UtilTool.nullToEmpty(dNew.getLocationName()));
    		deviceChangeDao.insert(dc);
    		dOld.setLocationId(dNew.getLocationId());
    		dOld.setLocationName(dNew.getLocationName());
    		isChange = true;
    	}
    	if(!isSame(dOld.getMaitainCycle(),dNew.getMaitainCycle())){
    		DeviceChange dc =  deviceChange;
    		dc.setChangeProperty("检修周期");
    		dc.setOldValue(dOld.getMaitainCycle()==null?"":dOld.getMaitainCycle().toString());	
    		dc.setNewValue(dNew.getMaitainCycle()==null?"":dNew.getMaitainCycle().toString());
    		deviceChangeDao.insert(dc);
    		dOld.setMaitainCycle(dNew.getMaitainCycle());
    		isChange = true;
    	}
    	if(!isSame(dOld.getBarcode(),StringUtil.nvlTo(dNew.getBarcode()))){
    		DeviceChange dc =  deviceChange;
    		dc.setChangeProperty("设备条码");
    		dc.setOldValue(UtilTool.nullToEmpty(dOld.getBarcode()));
    		dc.setNewValue(UtilTool.nullToEmpty(dNew.getBarcode()));	
    		deviceChangeDao.insert(dc);
    		dOld.setBarcode(dNew.getBarcode());
    		isChange = true;
    	}
    	if(!isSame(dOld.getAssetsCost(),dNew.getAssetsCost())){
    		DecimalFormat df=new DecimalFormat("###############.##");
    		DeviceChange dc =  deviceChange;
    		dc.setChangeProperty("资产原值");
    		dc.setOldValue(dOld.getAssetsCost()==null?"":df.format(dOld.getAssetsCost()));//转String前，去掉科学计数形式  8.88888888E7	
    		dc.setNewValue(dNew.getAssetsCost()==null?"":df.format(dNew.getAssetsCost()));
    		deviceChangeDao.insert(dc);
    		dOld.setAssetsCost(dNew.getAssetsCost());
    		isChange = true;
    	}
    	if(!isSame(dOld.getAssetsCode(),StringUtil.nvlTo(dNew.getAssetsCode()))){
    		DeviceChange dc =  deviceChange;
    		dc.setChangeProperty("资产编码");
    		dc.setOldValue(UtilTool.nullToEmpty(dOld.getAssetsCode()));
    		dc.setNewValue(UtilTool.nullToEmpty(dNew.getAssetsCode()));	
    		deviceChangeDao.insert(dc);
    		Map map = new HashMap();
    		map.put("assetsCode", UtilTool.nullToEmpty(dNew.getAssetsCode()));
    		map.put("oldAssetsCode", UtilTool.nullToEmpty(dOld.getAssetsCode()));
    		deviceDao.updateAssetsCode(map);
    		dOld.setAssetsCode(dNew.getAssetsCode());
    		isChange = true;
    	}
    	if(!isSame(dOld.getStatus(),dNew.getStatus())){
    		DeviceChange dc =  deviceChange;
    		dc.setChangeProperty("使用状态");
    		String statusNameNew = DictUtils.getDictLabel(DictTypeEnum.DEVICE_STATUS,dNew.getStatus().toString());
    		dc.setOldValue(dOld.getStatusName());	
    		dc.setNewValue(statusNameNew);
    		deviceChangeDao.insert(dc);
    		dOld.setStatus(dNew.getStatus());
    		isChange = true;
    	}
    	//更新设备表
    	if(isChange){
    		deviceDao.update(dOld);
    	}
		
	}
    //Check that allows you to delete 
   private boolean isSame(Object a,Object b)
   {
	   return (a==null && b==null) || (a!=null && a.equals(b));
   }
   private boolean isSameBigDecimal(BigDecimal a,BigDecimal b)
   {
	   boolean bol =  false;
	   try{
		   if((a==null && b==null) || a.compareTo(b)==0){
			   bol =true;
		   }
	   }catch(Exception e){
		   bol =false;
	   }
	   return bol;
   }
     //Check that allows you to delete 
    private boolean isAllowDelete(Long id)
    {
        return true;
    }
    //Check that allows you to save
    private boolean isAllowSave(DeviceChange deviceChange)
    {
        return true;
    }

}
