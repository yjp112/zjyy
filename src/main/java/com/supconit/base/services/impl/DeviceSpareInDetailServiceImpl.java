package com.supconit.base.services.impl;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.base.daos.DeviceSpareInDetailDao;
import com.supconit.base.entities.DeviceSpareInDetail;
import com.supconit.base.services.DeviceSpareInDetailService;
import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.common.utils.UtilTool;
@Service
public class DeviceSpareInDetailServiceImpl extends AbstractBaseBusinessService<DeviceSpareInDetail, Long> implements
DeviceSpareInDetailService{
	@Autowired
	private DeviceSpareInDetailDao deviceSpareInDetailDao;
	

	@Override
	public Pageable<DeviceSpareInDetail> findByCondition(Pagination<DeviceSpareInDetail> pager,DeviceSpareInDetail condition) {
		return deviceSpareInDetailDao.findByCondition(pager,condition);
	}


	@Override
	public void deleteById(Long arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public DeviceSpareInDetail getById(Long arg0) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void insert(DeviceSpareInDetail arg0) {
		deviceSpareInDetailDao.insert(arg0);
	}


	@Override
	public void update(DeviceSpareInDetail arg0) {
		DeviceSpareInDetail eviceSpareInDetail =deviceSpareInDetailDao.findById(arg0.getId());
		arg0.setRemainder(((Integer.parseInt(arg0.getTotal())-Integer.parseInt(eviceSpareInDetail.getTotal()))+Integer.parseInt(eviceSpareInDetail.getRemainder()))+""); 
		deviceSpareInDetailDao.update(arg0);
	}


	@Override
	public DeviceSpareInDetail findById(Long id) {
		return deviceSpareInDetailDao.findById(id);
	}


	@Override
	public DeviceSpareInDetail findTopOne() {
		return deviceSpareInDetailDao.findTopOne();
	}


	@Override
	public void deleteByIds(Long[] ids) {
		deviceSpareInDetailDao.deleteByIds(ids);
		
	}


	@Override
	public void updatelst(List<DeviceSpareInDetail> ddlst) {
		if(!UtilTool.isEmptyList(ddlst)){
			for(DeviceSpareInDetail d: ddlst){
				deviceSpareInDetailDao.update(d);
			}
		}
	}


	@Override
	public List<DeviceSpareInDetail> findListBySpareId(Long spareId) {
		return deviceSpareInDetailDao.findListBySpareId(spareId);
	}

}
