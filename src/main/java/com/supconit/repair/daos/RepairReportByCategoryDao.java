package com.supconit.repair.daos;

import java.util.List;

import com.supconit.common.daos.BaseDao;
import com.supconit.repair.entities.RepairReportByCategory;

/**
 * @文件名: DeviceReportDao
 * @创建日期: 13-9-9
 * @版权: Copyright(c)2013
 * @开发人员:
 * @版本:
 * @描述:
 */
public interface RepairReportByCategoryDao extends BaseDao<RepairReportByCategory,Long>{

	List<RepairReportByCategory> queryRepairByCategory(RepairReportByCategory rrbc);

}
