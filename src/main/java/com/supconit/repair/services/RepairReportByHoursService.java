package com.supconit.repair.services;

import java.util.List;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.repair.entities.RepairReportByHours;

/**
 * @文件名: DeviceReportService
 * @创建日期: 13-9-9
 * @版权: Copyright(c)2013
 * @开发人员:
 * @版本:
 * @描述:
 */
public interface RepairReportByHoursService extends BaseBusinessService<RepairReportByHours,Long>{

	List<RepairReportByHours> queryRepairByHours(RepairReportByHours rrbc);

}
