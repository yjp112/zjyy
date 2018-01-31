package com.supconit.repair.services;

import java.util.List;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.repair.entities.DeviceGoodRate;

/**
 * @文件名: DeviceReportService
 * @创建日期: 13-9-9
 * @版权: Copyright(c)2013
 * @开发人员:
 * @版本:
 * @描述:
 */
public interface DeviceGoodRateService extends BaseBusinessService<DeviceGoodRate,Long>{

	List<DeviceGoodRate> getDeviceGoodRate(DeviceGoodRate searchParam);

}
