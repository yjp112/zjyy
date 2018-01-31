package com.supconit.base.services;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import com.supconit.base.entities.DeviceSpareIn;
import com.supconit.base.entities.DeviceSpareInDetail;
import com.supconit.common.services.BaseBusinessService;

public interface DeviceSpareInService extends BaseBusinessService<DeviceSpareIn, Long>{
	Pageable<DeviceSpareIn> findByCondition(Pagination<DeviceSpareIn> pager,DeviceSpareIn condition);
	public DeviceSpareIn findById(Long id,String modelType);
	public DeviceSpareIn findById(Long id);
	public DeviceSpareIn findByDeviceId(Long deviceId);
	public DeviceSpareIn findTopOne();
	void documentSave(Long deviceSpareId, String[] fileorignal, String[] filename, String[] delfiles,String fileLength);
}
