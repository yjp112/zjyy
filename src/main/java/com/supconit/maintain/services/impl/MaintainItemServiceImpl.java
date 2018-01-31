package com.supconit.maintain.services.impl;

import hc.base.domains.Pageable;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.base.daos.DeviceCategoryDao;
import com.supconit.base.daos.GeoAreaDao;
import com.supconit.base.entities.GeoArea;
import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.maintain.daos.MaintainItemDao;
import com.supconit.maintain.entities.MaintainItem;
import com.supconit.maintain.services.MaintainItemService;

@Service
public class MaintainItemServiceImpl extends AbstractBaseBusinessService<MaintainItem, Long> implements MaintainItemService{

	@Autowired
	private MaintainItemDao maintainItemDao;
	@Autowired
	private DeviceCategoryDao deviceCategoryDao;
	@Autowired
	private GeoAreaDao geoAreaDao;
	
	@Override
	public MaintainItem getById(Long id) {
		return null;
	}

	@Override
	@Transactional
	public void insert(MaintainItem entity) {
		List<MaintainItem> mainItemList = new ArrayList<MaintainItem>();
		//设置计划开始时间
		for (MaintainItem device : entity.getDeviceList()) {
			for (MaintainItem item : entity.getMaintainItemList()) {
				MaintainItem tmp = new MaintainItem();
				tmp.setItemContent(item.getItemContent());
				tmp.setCycleUnit(item.getCycleUnit());
				tmp.setCycle(item.getCycle());
				tmp.setMaintainGroupId(item.getMaintainGroupId());
				tmp.setMaintainGroupName(item.getMaintainGroupName());
				tmp.setRemark(item.getRemark());
				tmp.setStartTime(device.getStartTime());
				tmp.setDeviceId(device.getDeviceId());
				tmp.setCreateId(entity.getCreateId());
				tmp.setCreator(entity.getCreator());
				tmp.setCreateDate(entity.getCreateDate());
				mainItemList.add(tmp);
			}
			//更新已存在的设备时间
			maintainItemDao.updateStartTimeByDeviceId(device);
		}
		
		//批量插入
		for (MaintainItem maintainItem : mainItemList) {
			maintainItemDao.insert(maintainItem);
		}
	}

	@Override
	@Transactional
	public void update(MaintainItem entity) {
		//删除设备下旧的保养项
		maintainItemDao.deleteByDeviceId(entity.getDeviceId());
		
		List<MaintainItem> deviceList = entity.getDeviceList();
		MaintainItem device = deviceList.get(0);
		//新增保养项
		List<MaintainItem> mainItemList = entity.getMaintainItemList();
		if(mainItemList.size() > 0){
			for (MaintainItem maintainItem : mainItemList) {
				maintainItem.setStartTime(device.getStartTime());
				maintainItem.setDeviceId(entity.getDeviceId());
				maintainItem.setCreateId(entity.getCreateId());
				maintainItem.setCreator(entity.getCreator());
				maintainItem.setCreateDate(entity.getCreateDate());
				maintainItemDao.insert(maintainItem);
			}
		}
	}

	@Override
	public void deleteById(Long id) {
		
	}

	@Override
	public Pageable<MaintainItem> findByCondition(Pageable<MaintainItem> pager,
			MaintainItem condition) {
		setParameter(condition);
		return maintainItemDao.findByCondition(pager, condition);
	}

	/**
	 * 设置(类别\区域)
	 */
	public void setParameter(MaintainItem condition){
        //类别
        if(null != condition.getCategoryId()){
            List<Long> categoryIds=deviceCategoryDao.findChildIds(condition.getCategoryId());
            condition.setDeviceCategoryChildIds(categoryIds);
        }
        //区域
        if(null != condition.getAreaId()){
        	//查出每个节点对应的子节点（包括自己）
    		List<GeoArea> listGeoAreas = new ArrayList<GeoArea>();
    		if(condition.getAreaId().longValue()==0l){//如果是根节点
    			listGeoAreas = geoAreaDao.findByRoot(condition.getAreaId());
    		}else{
    			listGeoAreas = geoAreaDao.findById(condition.getAreaId());
    		}
    		List<Long> areaIds = new ArrayList<Long>();
    		for (int i=0;i<listGeoAreas.size();i++){
    			areaIds.add(listGeoAreas.get(i).getId());
    		}
    		condition.setGeoAreaChildIds(areaIds);
        }	
    }

	@Override
	public MaintainItem getItem(Long deviceId) {
		MaintainItem item = new MaintainItem();
		List<MaintainItem> deviceList = maintainItemDao.getDeviceByDeviceId(deviceId);
		List<MaintainItem> itemList = maintainItemDao.getItemByDeviceId(deviceId);
		deviceList.get(0).setStartTime(itemList.get(0).getStartTime());
		item.setDeviceList(deviceList);
		item.setMaintainItemList(itemList);
		item.setDeviceId(deviceId);
		return item;
	}
	
	@Override
	public void deleteByDeviceId(Long deviceId) {
		maintainItemDao.deleteByDeviceId(deviceId);
	}

	

}
