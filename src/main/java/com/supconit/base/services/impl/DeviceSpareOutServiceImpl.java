package com.supconit.base.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.base.daos.DeviceSpareOutDao;
import com.supconit.base.entities.DeviceSpareOut;
import com.supconit.base.services.DeviceSpareOutService;
import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.common.utils.UtilTool;
@Service
public class DeviceSpareOutServiceImpl extends AbstractBaseBusinessService<DeviceSpareOut, Long> implements
DeviceSpareOutService{
	@Autowired
	private DeviceSpareOutDao deviceSpareOutDao;
	
	@Override
	public void deleteById(Long arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DeviceSpareOut getById(Long id) {
		return deviceSpareOutDao.getById(id);
	}

	@Override
	public void insert(DeviceSpareOut arg0) {
		deviceSpareOutDao.insert(arg0);
	}

	@Override
	public void update(DeviceSpareOut arg0) {
		deviceSpareOutDao.update(arg0);
	}

	@Override
	public void insertlst(List<DeviceSpareOut> dlst) {
		if(!UtilTool.isEmptyList(dlst)){
			for(DeviceSpareOut d :dlst){
				deviceSpareOutDao.insert(d);
			}
		}
	}


}
