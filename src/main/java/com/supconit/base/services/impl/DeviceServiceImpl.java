
package com.supconit.base.services.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.base.daos.AttachmentDao;
import com.supconit.base.daos.DeviceCategoryDao;
import com.supconit.base.daos.DeviceChangeDao;
import com.supconit.base.daos.DeviceDao;
import com.supconit.base.daos.DeviceExtendPropertyDao;
import com.supconit.base.daos.GeoAreaDao;
import com.supconit.base.daos.SubDeviceDao;
import com.supconit.base.entities.Device;
import com.supconit.base.entities.DeviceExtendProperty;
import com.supconit.base.entities.DeviceTree;
import com.supconit.base.entities.GeoArea;
import com.supconit.base.entities.SubDevice;
import com.supconit.base.services.DeviceService;
import com.supconit.common.domains.TreeNode;
import com.supconit.common.exceptions.BusinessDoneException;
import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.common.services.AbstractBaseTreeServiceImpl;
import com.supconit.common.services.BaseTreeService;
import com.supconit.common.services.IInsertCommand;
import com.supconit.common.utils.Constant;
import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.ListUtils;
import com.supconit.common.utils.UtilTool;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.nhgl.basic.ngArea.entities.NgArea;

import hc.base.domains.Pageable;

@Service
public class DeviceServiceImpl extends AbstractBaseBusinessService<Device, Long> implements DeviceService{

	@Autowired
	private DeviceDao		deviceDao;
    @Autowired
    private SubDeviceDao		subDeviceDao;	
    @Autowired
    private AttachmentDao		attachmentDao;	
    @Autowired
    private DeviceExtendPropertyDao		deviceExtendPropertyDao;	
    @Autowired
	private GeoAreaDao geoAreaDao;
    @Autowired
	private DeviceCategoryDao deviceCategoryDao;
    @Autowired
	private DeviceChangeDao deviceChangeDao;
    
    private static final int batchMaxSize=100;
    
    /**
	 * Get device by  ID
	 * @param id  device id
	 * @return object instance
	 */
	@Override
	@Transactional(readOnly = true)
	public Device getById(Long id) {
		if (null == id || id <= 0)
			return null;
		Device device = deviceDao.getById(id);
		
		return device;
	}	

    /**
	 * delete Device by ID 
	 * @param id  device  ID 	 
	 * @return 
	 */
    @Override
	@Transactional
	public void deleteById(Long id) {
		
        isAllowDelete(id);
		
        deviceDao.deleteById(id);
        subDeviceDao.deleteByDeviceId(id);
	}


    /**
	 * delete Device by ID array
	 * @param ids  device ID array	 
	 * @return 
	 */
	@Override
	@Transactional
	public void deleteByIds(Long[] ids) {
		for(int i =0;i<ids.length;i++)
        {
            isAllowDelete(ids[i]);
        }  
		BaseTreeService<Device> treeService=getTreeService();
		for(int i =0;i<ids.length;i++)
        {    
            //deviceDao.deleteById(ids[i]);
            treeService.delete(ids[i]);
            subDeviceDao.deleteByDeviceId(ids[i]);
        	//delete 扩展属性
        	deviceExtendPropertyDao.deleteByDeviceId(ids[i]);
        	//delete 设备变更
        	deviceChangeDao.deleteByDeviceId(ids[i]);
        	//delete 设备随机档案
        	attachmentDao.deleteByObj(ids[i], Constant.ATTACHEMENT_DEVICE_DOS);
        	//delete 设备随机图片
        	attachmentDao.deleteByObj(ids[i], Constant.ATTACHEMENT_DEVICE_IMG);
        }  
        
		
	}

    /**
	 * Find Device list by condition
	 * @param pageNo  page number
	 * @param pageSize  page size
	 * @param condition Query condition
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public Pageable<Device> findByCondition(Pageable<Device> pager, Device condition) {
		setParameter(condition);
		return deviceDao.findByCondition(pager , condition);
	}
	@Override
	@Transactional(readOnly = true)
	public Pageable<Device> findRunReportByCondition(Pageable<Device> pager, Device condition) {
		setParameter(condition);
		return deviceDao.findRunReportByCondition(pager, condition);
	}
	public void setParameter(Device condition){
        if(condition.getLocationId()!=null){//递归查询子节点
            List<GeoArea> listGeoAreas = geoAreaDao.findById(condition.getLocationId());
            List<Long> lstLocationId = new ArrayList<Long>();
            if(!UtilTool.isEmptyList(listGeoAreas)){
            	for(GeoArea g:listGeoAreas){
            		lstLocationId.add(g.getId());
            	}
            }
            condition.setLstLocationId(lstLocationId);
          }	
        if(condition.getCategoryId()!=null){//递归查询子节点
            List<Long> lstCategoryId=deviceCategoryDao.findChildIds(condition.getCategoryId());
            condition.setLstCategoryId(lstCategoryId);
          }
	}
	 /**
	 * insert Device
	 * @param device  instance	 
	 * @return
	 */
    @Override
	@Transactional
	public void insert(Device device) {	//没用到
		
        isAllowSave(device);
            
        SubDevice subDevice;
        List<SubDevice> subDeviceList = device.getSubDeviceList();
        
        //save master datatable 
        deviceDao.insert(device);
        
        //Save detail datatable 
		Iterator<SubDevice> iter= subDeviceList.iterator();
		while(iter.hasNext()){
			subDevice=iter.next();			
			//subDevice.setDeviceId(device.getId());
		}        
        
        //update detail datatable 
		for (List<SubDevice> list : ListUtils.partition(subDeviceList, batchMaxSize)) {
			subDeviceDao.insert(list);
		}		
	}
    /**
	 * insert Device
	 * @param device  instance	 
	 * @return
	 */
    @Override
	@Transactional
	public void insert(Device device,final String[] fileorignal,final String[] filename,final String[] delfiles,final String[] fileorignal1,final String[] filename1,final String[] delfiles1){
		
       // isAllowSave(device);
    	getTreeService().insertNode(device, device.getParentId(), new IInsertCommand<Device>() {
			@Override
			public Device insert(Device device) {
		        List<SubDevice> subDeviceList = device.getSubDeviceList();
		        List<DeviceExtendProperty>  extendPropertyList= device.getExtendPropertyList();
		        
		        //save master datatable 
		        deviceDao.insert(device);
		        
		        //Save 子设备
		        if(!UtilTool.isEmptyList(subDeviceList)){
		    		for(int i=0;i<subDeviceList.size();i++){
		    			subDeviceList.get(i).setDeviceId(device.getId());
		        		
		    			if(UtilTool.isEmpty(subDeviceList.get(i).getSubDeviceCode()) || UtilTool.isEmpty(subDeviceList.get(i).getSubDeviceName())){
		        			subDeviceList.remove(i);//除掉空行
		        			i--;
		        		}
		    		}
		    		for (List<SubDevice> list : ListUtils.partition(subDeviceList, batchMaxSize)) {
		    			subDeviceDao.insert(list);
		    		}
		        }
		        //Save 扩展属性
		        if(!UtilTool.isEmptyList(extendPropertyList)){
			        for(int j=0;j<extendPropertyList.size();j++){
			        	extendPropertyList.get(j).setDeviceId(device.getId());
			        }
					for (List<DeviceExtendProperty> list : ListUtils.partition(extendPropertyList, batchMaxSize)) {
						deviceExtendPropertyDao.insert(list);
					}
		        }	
		        //Save 随机档案

				attachmentDao.saveAttachements(device.getId(),Constant.ATTACHEMENT_DEVICE_DOS,fileorignal, filename, delfiles,DictUtils.getDictValue(DictUtils.getDictList(DictTypeEnum.FILELENGTH), "默认"));
				//Save 设备图片
				attachmentDao.saveAttachements(device.getId(),Constant.ATTACHEMENT_DEVICE_IMG,fileorignal1, filename1, delfiles1,DictUtils.getDictValue(DictUtils.getDictList(DictTypeEnum.FILELENGTH), "默认"));
				return device;
			}
		});
	}


    /**
	 * update Device
	 * @param device  instance 
	 * @return
	 */
    @Override
	@Transactional
	public void update(Device device) {	//没用到	
        
		List<SubDevice> subDeviceList = device.getSubDeviceList();
		
        //delete Detail DataTable
        subDeviceDao.deleteByDeviceId(device.getId());
        
        //update master datatable 
        deviceDao.update(device);
        
        //Save detail datatable 
        SubDevice subDevice;
		Iterator<SubDevice> iter= subDeviceList.iterator();
		while(iter.hasNext()){
			subDevice=iter.next();			
			//subDevice.setDeviceId(device.getId());
		}
        
		//update detail datatable 
		for (List<SubDevice> list : ListUtils.partition(subDeviceList, batchMaxSize)) {
			subDeviceDao.insert(list);
		}
        
	}
    /**
   	 * update Device
   	 * @param device  instance 
   	 * @return
     * @throws Exception 
   	 */
    @Override
   	@Transactional
   	public void update(Device device,String[] fileorignal,String[] filename,String[] delfiles,String[] fileorignal1,String[] filename1,String[] delfiles1){
    	isAllowSave(device);
    	Device oldDevice=deviceDao.findById(device.getId());
    	if((oldDevice.getParentId()==null&&device.getParentId()!=null)||oldDevice.getParentId().equals(device.getParentId())){
    		//父节点发生变化
    		getTreeService().changeParent(device.getId(), device.getParentId());
    	}
    	List<SubDevice> subDeviceList = device.getSubDeviceList();
    	List<DeviceExtendProperty>  extendPropertyList= device.getExtendPropertyList();

    	//delete 子设备
    	subDeviceDao.deleteByDeviceId(device.getId());
    	//delete 扩展属性
    	deviceExtendPropertyDao.deleteByDeviceId(device.getId());
    	//update master datatable 
    	deviceDao.update(device);

    	//update 子设备
    	if(!UtilTool.isEmptyList(subDeviceList)){
    		for(int i=0;i<subDeviceList.size();i++){
    			subDeviceList.get(i).setDeviceId(device.getId());
        		
    			if(UtilTool.isEmpty(subDeviceList.get(i).getSubDeviceCode()) || UtilTool.isEmpty(subDeviceList.get(i).getSubDeviceName())){
        			subDeviceList.remove(i);//除掉空行
        			i--;
        		}
    		}
    		for (List<SubDevice> list : ListUtils.partition(subDeviceList, batchMaxSize)) {
    			subDeviceDao.insert(list);
    		}
    	}
    	//update 扩展属性
    	if(!UtilTool.isEmptyList(extendPropertyList)){
    		for(int j=0;j<extendPropertyList.size();j++){
    			extendPropertyList.get(j).setDeviceId(device.getId());
    		}
    		for (List<DeviceExtendProperty> list : ListUtils.partition(extendPropertyList, batchMaxSize)) {
    			deviceExtendPropertyDao.insert(list);
    		}
    	}
    	//update 随机档案

    	attachmentDao.saveAttachements(device.getId(),Constant.ATTACHEMENT_DEVICE_DOS,fileorignal, filename, delfiles,DictUtils.getDictValue(DictUtils.getDictList(DictTypeEnum.FILELENGTH), "默认"));
    	//update 设备图片
    	attachmentDao.saveAttachements(device.getId(),Constant.ATTACHEMENT_DEVICE_IMG,fileorignal1, filename1, delfiles1,DictUtils.getDictValue(DictUtils.getDictList(DictTypeEnum.FILELENGTH), "默认"));

    	//attachmentDao.saveAttachements(device.getId(),Constant.ATTACHEMENT_DEVICE_DOS,fileorignal, filename, delfiles);
    	//update 设备图片
    	//attachmentDao.saveAttachements(device.getId(),Constant.ATTACHEMENT_DEVICE_IMG,fileorignal1, filename1, delfiles1);

    }
   
    //Check that allows you to delete 
    private void isAllowDelete(Long id)
    {
    	Device device = deviceDao.getById(id);
    	List<Device> lst =deviceDao.getDeviceUseCount(device);
    	if(!UtilTool.isEmptyList(lst) && lst.size()>0){
    		throw new BusinessDoneException("该设备已使用，不能删除。");
    	}        
    }
    //Check that allows you to save
    private void isAllowSave(Device device)
    {
    	List<Device> list = new ArrayList<Device>();
		if(device.getHpid()!=null){
			list = deviceDao.findByHpid(device.getHpid());
			
			if (null != list && list.size() >= 1) {
				if (list.size() > 1) {
					throw new BusinessDoneException("模型编码[" + device.getHpid()+ "]已经被占用。");
				} else {
					if (device.getId() != null) {
						Device old = list.get(0);
						if (device.getId().longValue() == old.getId().longValue()) {
						} else {
							throw new BusinessDoneException("模型编码["+ device.getHpid() + "]已经被占用。");
						}
					} else {
						throw new BusinessDoneException("模型编码["+device.getHpid()+"]已经被占用。");  
					}
	
				}
			}
		}
		List<Device> listdp=deviceDao.findByCode(device.getDeviceCode());
    	if (null != listdp && listdp.size() >= 1) {
			if (listdp.size() > 1) {
				throw new BusinessDoneException("设备编码[" + device.getDeviceCode()+ "]已经被占用。");
			} else {
				// list.size()==1
				if (device.getId() != null) {
					// update
					Device old = listdp.get(0);
					if (device.getId().longValue() == old.getId().longValue()) {
						// ok
					} else {
						throw new BusinessDoneException("设备编码["+ device.getDeviceCode() + "]已经被占用。");
					}
				  } else {
					// insert
					throw new BusinessDoneException("设备编码["+ device.getDeviceCode() + "]已经被占用。");
				  }
			 }
		  }
    	List<SubDevice> subDeviceList = device.getSubDeviceList();
    	
    	for (SubDevice subDevice : subDeviceList){
    		
          if(subDevice.getSubDeviceCode() != "" || subDevice.getSubDeviceName() != "" || subDevice.getNum() != null || subDevice.getRemark() != "") {
	    		if(subDevice.getSubDeviceCode() == null || subDevice.getSubDeviceCode() == "" )
	            	throw new BusinessDoneException("附属设备编码不能为空。");
	            	
	    		if(subDevice.getSubDeviceName() == null || subDevice.getSubDeviceName()  == "" )
	            	throw new BusinessDoneException("附属设备名称不能为空。");
	           	
    	    }
    	}
    	
    }
    
    @Override
	public Device findById(Long id) {
		// TODO Auto-generated method stub
		return deviceDao.findById(id);
	}

	//按设备编码
	@Override
	public Device getByDeviceCode(String deviceCode){
        return  deviceDao.getByDeviceCode(deviceCode);
	}
	//按设备模型编码
	@Override
	public Device getByHpid(String hpid){
        return  deviceDao.getByHpid(hpid);
	}
	@Override
	public Pageable<Device> findByCodeName_g( Pageable<Device> pager, Device condition) {
		/*long l = 0L;
		List<GeoArea> lst = geoAreaDao.findByCode(condition.getAssetsCode());
		if(!UtilTool.isEmptyList(lst)){
			l=lst.get(0).getId();
		}
		condition.setLocationId(l);*/
        return  deviceDao.findByCodeName_g(pager , condition) ;
	}
	@Override
	public List<Device> findByCodeName_g(Device condition) {
        return  deviceDao.findByCodeName_g(condition);
	}
	public Pageable<Device> findByKuangxuan_g(Pageable<Device> pager, Device device,String[] codes){
		return  deviceDao.findByKuangxuan_g(pager ,device, codes) ;
	}
	@Override
	public List findMjZnzm(String geoareaCode ,List<Long> lstHaveOpenStatus ,List<Long> lstHaveAlarmStatus,boolean bol){
		// TODO Auto-generated method stub
		return deviceDao.findMjZnzm(geoareaCode,lstHaveOpenStatus,lstHaveAlarmStatus,bol);
	}
	//查设备图片
	@Override
	public Device getDeviceImg(Device device) {
        return  deviceDao.getDeviceImg(device);
	}
	//设备查询轮询状态
	@Override
	public List<Device> findStatusByCodes(Device device) {
		return  deviceDao.findStatusByCodes(device);
	}

    @Override
    public List<Device> findByParam(Device device){
        return deviceDao.findByParam(device);
    }
    @Override
    public List<Device> findByCategory(String categoryCode){
    	return deviceDao.findByCategory(categoryCode);
    }
    @Override
    public List<Device> findByIds(List<Long> ids){
        return deviceDao.findByIds(ids);
    }
    @Override
    public boolean check(List<Long> ids){
        List<Device> deviceList = deviceDao.findGroupByCategoryId(ids);
        if(deviceList.size()>1){
            return false;
        }
        deviceList = deviceDao.findGroupByLocationId(ids);
        if(deviceList.size()>1){
            return false;
        }

        return true;
    }
    @Override
    public Long getByCategoryTotal(String categoryCode){
        return deviceDao.getByCategoryTotal( categoryCode);
    }
	@Override
	public Pageable<Device> findByCategoryArea(Pageable<Device> pager, Device condition) {
		return deviceDao.findByCategoryArea(pager, condition);
	}

	@Override
    public List<DeviceTree> findByParent(Device condition) {
        List<DeviceTree> result= new ArrayList<DeviceTree>();
        List<DeviceTree> trees= deviceDao.findByPid(condition);
        result.addAll(trees);
        if(condition.getSearch()) {
            Map<Long,DeviceTree>  treeMap = new HashMap<Long, DeviceTree>();
            List<DeviceTree>deviceTres = new ArrayList<DeviceTree>();
            for (DeviceTree deviceTree : trees) {
                treeMap.put(deviceTree.getId(),deviceTree);
                deviceTres.add(deviceTree);
            }
            if(deviceTres.size()>0) {
                List<DeviceTree> childs = deviceDao.findByPid(deviceTres);
                for (DeviceTree DeviceTree : childs) {
                    if (!treeMap.containsKey(DeviceTree.getId())) {
                        result.add(DeviceTree);
                        treeMap.put(DeviceTree.getId(), DeviceTree);
                    }
                }
            }
        }
        return result;
    }


    //=================
	//add by dingyg

	@Override
	public List<Device> findByExpDevice(Device device) {
		setParameter2(device);
		return deviceDao.findByExpDevice(device);
	}

	@Override
	public void insertForImp(List<Device> lstDevice) {
    	for(Device d:lstDevice){
    		deviceDao.insert(d);
    	}
	}

	private void setParameter2(Device condition) {
        if(condition.getLocationId()!=null){//递归查询子节点
            List<GeoArea> listGeoAreas = geoAreaDao.findById(condition.getLocationId());
            List<Long> lstLocationId = new ArrayList<Long>();
            if(!UtilTool.isEmptyList(listGeoAreas)){
            	for(GeoArea g:listGeoAreas){
            		lstLocationId.add(g.getId());
            	}
            }
            condition.setLstLocationId(lstLocationId);
          }	
        if(condition.getCategoryId()!=null){//递归查询子节点
            List<Long> lstCatagoryId=deviceCategoryDao.findChildIds(condition.getCategoryId());
            if(!UtilTool.isEmptyList(lstCatagoryId)){
            	condition.setLstCategoryId(lstCatagoryId);
            }
            
          }	
		
	}
	
	@Override
	public List<Device> findCate(List<Long> lstSystemRuleId) {
		return deviceDao.findCate(lstSystemRuleId);
	}

	@Override
	public Pageable<Device> findDeptByElectric(Pageable<Device> pager,
			Device condition) {
		return deviceDao.findDeptByElectricSys(pager, condition);
	}

	@Override
	public Pageable<Device> findDeptByEnergy(Pageable<Device> pager,
			Device condition) {
		return deviceDao.findDeptByEnergySys(pager, condition);
	}

	@Override
	public Pageable<Device> findDeptByGas(Pageable<Device> pager,
			Device condition) {
		return deviceDao.findDeptByGasSys(pager, condition);
	}

	@Override
	public Pageable<Device> findDeptByWater(Pageable<Device> pager,
			Device condition) {
		return deviceDao.findDeptByWaterSys(pager, condition);
	}
	private BaseTreeService<Device> getTreeService() {
		BaseTreeService<Device> treeService=new AbstractBaseTreeServiceImpl<Device>() {
    		@Override
    		public String getTableName() {
    			return "DEVICE";
    		}

    		@Override
    		public String getNameColumn() {
    			return "DEVICE_NAME";
    		}

    		@Override
    		public String getIdColumn() {
    			return "ID";
    		}

    		@Override
    		public String getPIDColumn() {
    			return "PARENT_ID";
    		}
    	};
		return treeService;
	}

	@Override
	@Transactional
	public void rebuildDeviceTree() {
		getTreeService().reBuildTree(0L, 1L);
	}
	
	@Override
	public List<Device> findAll() {
		return deviceDao.findAll();
	}

	@Override
	public List<Device> countDeviceBySystemRuleId(List<Long> systemRuleIds,Long floorId) {
		List<Device> countDevice=deviceDao.countDeviceBySystemRuleId(systemRuleIds,floorId);
		if(countDevice!=null&&countDevice.size()>0){
			List<Device> alarmCountDevice=deviceDao.countAlarmDeviceBySystemRuleId(systemRuleIds,floorId);
			for (Device alarm : alarmCountDevice) {
				for (Device device : countDevice) {
					if(alarm.getCategoryId().equals(device.getCategoryId())){
						device.setAlarmDeviceCount(alarm.getAlarmDeviceCount());
						break;
					}
				}
			}
		}
		for (Device device : countDevice) {
			device.setCategoryNames(device.getCategoryName().substring(0, device.getCategoryName().length()>=8? 8:device.getCategoryName().length()));
		}
		return countDevice;
		
	}
	
	@Override
	@Transactional
	public List<Device> searchAllDevice() {
		return this.deviceDao.searchAllDevice();
	}

	@Override
	public List<Device> selectDevices(Device device) {
		return this.deviceDao.selectDevices(device);
	}

	@Override
	public long countDevices(Device device) {
		return this.deviceDao.countDevices(device);
	}
       
}
