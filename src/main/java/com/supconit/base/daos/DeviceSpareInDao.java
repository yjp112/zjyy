package com.supconit.base.daos;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.List;

import com.supconit.base.entities.DeviceSpareIn;
import com.supconit.base.entities.DeviceSpareInDetail;
import com.supconit.common.daos.BaseDao;

public interface DeviceSpareInDao extends BaseDao<DeviceSpareIn, Long>{
	Pageable<DeviceSpareIn> findByCondition(Pagination<DeviceSpareIn> pager,DeviceSpareIn condition);
	public DeviceSpareIn findById(Long id,String modelType);
	public DeviceSpareIn findById(Long id);
	public DeviceSpareIn findByDeviceId(Long deviceId);
	public DeviceSpareIn findTopOne();
}
