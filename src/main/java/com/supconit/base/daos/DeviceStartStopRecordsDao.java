
package com.supconit.base.daos;

import java.util.List;

import com.supconit.base.entities.DeviceStartStopRecords;
import com.supconit.common.daos.BaseDao;

import hc.base.domains.Pageable;

public interface DeviceStartStopRecordsDao extends BaseDao<DeviceStartStopRecords, Long>{
	
	Pageable<DeviceStartStopRecords> findByCondition(Pageable<DeviceStartStopRecords> pager, DeviceStartStopRecords condition);

	int deleteByIds(Long[] ids);
    
    public  DeviceStartStopRecords findById(Long id); 
	public List<DeviceStartStopRecords> findLastData() ;
}
