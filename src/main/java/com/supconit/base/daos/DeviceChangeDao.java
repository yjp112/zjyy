
package com.supconit.base.daos;

import com.supconit.base.entities.DeviceChange;
import com.supconit.common.daos.BaseDao;

import hc.base.domains.Pageable;

public interface DeviceChangeDao extends BaseDao<DeviceChange, Long>{
	
	Pageable<DeviceChange> findByCondition(Pageable<DeviceChange> pager, DeviceChange condition);

	int deleteByIds(Long[] ids);
    
    public  DeviceChange findById(Long id); 
    public int deleteByDeviceId(Long deviceId) ;
}
