
package com.supconit.base.daos;

import java.util.List;

import com.supconit.base.entities.DeviceStartStopOldRecords;
import com.supconit.common.daos.BaseDao;

import hc.base.domains.Pageable;

public interface DeviceStartStopOldRecordsDao extends BaseDao<DeviceStartStopOldRecords, Long>{
	
	Pageable<DeviceStartStopOldRecords> findByCondition(Pageable<DeviceStartStopOldRecords> pager, DeviceStartStopOldRecords condition);

	int deleteByIds(Long[] ids);
    
    public  DeviceStartStopOldRecords findById(Long id); 
	public List<DeviceStartStopOldRecords> findNewData() ;
}
