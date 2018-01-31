package com.supconit.base.services.impl;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.base.daos.AttachmentDao;
import com.supconit.base.daos.DeviceCategoryDao;
import com.supconit.base.daos.DeviceSpareInDao;
import com.supconit.base.daos.GeoAreaDao;
import com.supconit.base.entities.DeviceSpareIn;
import com.supconit.base.entities.GeoArea;
import com.supconit.base.services.DeviceSpareInService;
import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.common.utils.Constant;
import com.supconit.common.utils.UtilTool;
@Service
public class DeviceSpareInServiceImpl extends AbstractBaseBusinessService<DeviceSpareIn, Long> implements
	DeviceSpareInService{
	@Autowired
	private DeviceSpareInDao deviceSpareInDao;
	@Autowired
	private GeoAreaDao geoAreaDao;
	@Autowired
	private DeviceCategoryDao deviceCategoryDao;
	@Autowired
	private AttachmentDao		attachmentDao;	
	
	@Override
	public void deleteById(Long arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DeviceSpareIn getById(Long id) {
		return deviceSpareInDao.getById(id);
	}

	@Override
	public void insert(DeviceSpareIn arg0) {
		deviceSpareInDao.insert(arg0);
	}

	@Override
	public void update(DeviceSpareIn arg0) {
		deviceSpareInDao.update(arg0);
	}

	@Override
	public Pageable<DeviceSpareIn> findByCondition(Pagination<DeviceSpareIn> pager,DeviceSpareIn condition) {
		setParameter(condition);
		Pageable<DeviceSpareIn> ss=deviceSpareInDao.findByCondition(pager,condition);
		return ss;
	}
	public void setParameter(DeviceSpareIn condition){
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

	@Override
	public DeviceSpareIn findById(Long id,String modelType) { 
		return deviceSpareInDao.findById(id,modelType);
	}
	
	@Override
	public DeviceSpareIn findByDeviceId(Long deviceId) {
		return deviceSpareInDao.findByDeviceId(deviceId);
	}

	@Override
	public DeviceSpareIn findTopOne() {
		return deviceSpareInDao.findTopOne();
	}

	@Override
	public void documentSave(Long deviceSpareId, String[] fileorignal,
			String[] filename, String[] delfiles,String fileLength) {
		//判断是否已经存在
		if(attachmentDao.getAttachmentByFid(deviceSpareId, Constant.SPARE_DEVICE).size()>0){ 
			attachmentDao.deleteByObj(deviceSpareId, Constant.SPARE_DEVICE);
		}
		attachmentDao.saveAttachements(deviceSpareId, Constant.SPARE_DEVICE, fileorignal, filename, delfiles, fileLength); 
		
		
	}

	@Override
	public DeviceSpareIn findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
