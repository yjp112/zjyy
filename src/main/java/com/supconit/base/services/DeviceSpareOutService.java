package com.supconit.base.services;

import java.util.List;

import com.supconit.base.entities.DeviceSpareOut;
import com.supconit.common.services.BaseBusinessService;

public interface DeviceSpareOutService extends BaseBusinessService<DeviceSpareOut, Long>{
	public void insertlst(List<DeviceSpareOut> dlst);
}
