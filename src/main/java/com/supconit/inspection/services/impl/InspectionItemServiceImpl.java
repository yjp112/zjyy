package com.supconit.inspection.services.impl;

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
import com.supconit.inspection.daos.InspectionItemDao;
import com.supconit.inspection.entities.InspectionItem;
import com.supconit.inspection.services.InspectionItemService;

@Service
public class InspectionItemServiceImpl extends AbstractBaseBusinessService<InspectionItem, Long> implements InspectionItemService{

	@Autowired
	private InspectionItemDao inspectionItemDao;
	@Autowired
	private DeviceCategoryDao deviceCategoryDao;
	@Autowired
	private GeoAreaDao geoAreaDao;
	
	@Override
	public InspectionItem getById(Long id) {
		return null;
	}

	@Override
	@Transactional
	public void insert(InspectionItem entity) {
		List<InspectionItem> inspectionItemList = new ArrayList<InspectionItem>();
		//设置计划开始时间
		for (InspectionItem device : entity.getDeviceList()) {
			for (InspectionItem item : entity.getInspectionItemList()) {
				InspectionItem tmp = new InspectionItem();
				tmp.setItemContent(item.getItemContent());
				tmp.setCycleUnit(item.getCycleUnit());
				tmp.setCycle(item.getCycle());
				tmp.setInspectionGroupId(item.getInspectionGroupId());
				tmp.setInspectionGroupName(item.getInspectionGroupName());
				tmp.setRemark(item.getRemark());
				tmp.setStartTime(device.getStartTime());
				tmp.setDeviceId(device.getDeviceId());
				tmp.setCreateId(entity.getCreateId());
				tmp.setCreator(entity.getCreator());
				tmp.setCreateDate(entity.getCreateDate());
				inspectionItemList.add(tmp);
			}
			//更新已存在的设备时间
			inspectionItemDao.updateStartTimeByDeviceId(device);
		}
		
		//批量插入
		for (InspectionItem inspectionItem : inspectionItemList) {
			inspectionItemDao.insert(inspectionItem);
		}
	}

	@Override
	@Transactional
	public void update(InspectionItem entity) {
		//删除设备下旧的巡检项
		inspectionItemDao.deleteByDeviceId(entity.getDeviceId());
		
		List<InspectionItem> deviceList = entity.getDeviceList();
		InspectionItem device = deviceList.get(0);
		//新增巡检项
		List<InspectionItem> inspectionItemList = entity.getInspectionItemList();
		if(inspectionItemList.size() > 0){
			for (InspectionItem inspectionItem : inspectionItemList) {
				inspectionItem.setStartTime(device.getStartTime());
				inspectionItem.setDeviceId(entity.getDeviceId());
				inspectionItem.setCreateId(entity.getCreateId());
				inspectionItem.setCreator(entity.getCreator());
				inspectionItem.setCreateDate(entity.getCreateDate());
				inspectionItemDao.insert(inspectionItem);
			}
		}
	}

	@Override
	public void deleteById(Long id) {
		
	}

	@Override
	public Pageable<InspectionItem> findByCondition(Pageable<InspectionItem> pager,
			InspectionItem condition) {
		setParameter(condition);
		return inspectionItemDao.findByCondition(pager, condition);
	}

	/**
	 * 设置(类别\区域)
	 */
	public void setParameter(InspectionItem condition){
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
	public InspectionItem getItem(Long deviceId) {
		InspectionItem item = new InspectionItem();
		List<InspectionItem> deviceList = inspectionItemDao.getDeviceByDeviceId(deviceId);
		List<InspectionItem> itemList = inspectionItemDao.getItemByDeviceId(deviceId);
		deviceList.get(0).setStartTime(itemList.get(0).getStartTime());
		item.setDeviceList(deviceList);
		item.setInspectionItemList(itemList);
		item.setDeviceId(deviceId);
		return item;
	}
	
	@Override
	public void deleteByDeviceId(Long deviceId) {
		inspectionItemDao.deleteByDeviceId(deviceId);
	}

	

}
