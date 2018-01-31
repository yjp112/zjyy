
package com.supconit.base.daos;

import java.util.List;

import com.supconit.base.entities.DevicePionts;
import com.supconit.common.daos.BaseDao;

import hc.base.domains.Pageable;

public interface DevicePiontsDao extends BaseDao<DevicePionts, Long>{
	
	public Pageable<DevicePionts> findByCondition(Pageable<DevicePionts> pager, DevicePionts condition);

	public int deleteByIds(Long[] ids);
    
    public void insert(List<DevicePionts> list);
    
    public void deleteByDeviceId(Long id);
}
