
package com.supconit.base.services.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.base.daos.DeviceStartStopRecordsDao;
import com.supconit.base.entities.DeviceStartStopRecords;
import com.supconit.base.services.DeviceStartStopRecordsService;
import com.supconit.common.services.AbstractBaseBusinessService;

import hc.base.domains.Pageable;


@Service
public class DeviceStartStopRecordsServiceImpl extends AbstractBaseBusinessService<DeviceStartStopRecords, Long> implements DeviceStartStopRecordsService{

	@Autowired
	private DeviceStartStopRecordsDao		deviceStartStopRecordsDao;	
	
	 
    /**
	 * Find DeviceStartStopRecords list by condition
	 * @param pageNo  page number
	 * @param pageSize  page size
	 * @param condition Query condition
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public Pageable<DeviceStartStopRecords> findByCondition(Pageable<DeviceStartStopRecords> pager, DeviceStartStopRecords condition) {
		return deviceStartStopRecordsDao.findByCondition(pager, condition);
	}
    @Override
	@Transactional
	public void deleteById(Long id) {
		
    	deviceStartStopRecordsDao.deleteById(id);
	}
	@Override
	@Transactional(readOnly = true)
	public DeviceStartStopRecords getById(Long id) {
		if (null == id || id <= 0)
			return null;
		DeviceStartStopRecords deviceStartStopRecords = deviceStartStopRecordsDao.getById(id);
		
		return deviceStartStopRecords;
	}	
    @Override
	@Transactional
	public void update(DeviceStartStopRecords deviceStartStopRecords) {        

	}
    @Override
	@Transactional
	public void insert(DeviceStartStopRecords deviceStartStopRecords) {            

	}
}