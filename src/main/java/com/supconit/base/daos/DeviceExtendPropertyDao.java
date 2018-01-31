
package com.supconit.base.daos;

import java.util.List;

import com.supconit.base.entities.DeviceExtendProperty;
import com.supconit.common.daos.BaseDao;

import hc.base.domains.Pageable;

public interface DeviceExtendPropertyDao extends BaseDao<DeviceExtendProperty, Long>{
	
	Pageable<DeviceExtendProperty> findByCondition(Pageable<DeviceExtendProperty> pager, DeviceExtendProperty condition);

	int deleteByIds(Long[] ids);
    
    public  DeviceExtendProperty findById(Long id); 
    public void insert(List<DeviceExtendProperty> list);
        
    public void deleteByDeviceId(Long id);
}
