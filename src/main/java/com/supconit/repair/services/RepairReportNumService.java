package com.supconit.repair.services;

import java.util.List;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.repair.entities.RepairReportNum;

public interface RepairReportNumService extends BaseBusinessService<RepairReportNum,Long>{
	/**
	 * 完成维修次数(年)
	 */
	List<RepairReportNum> queryRepairByYearFinished(RepairReportNum condition);
	/**
	 * 所有维修次数(年)
	 */
	List<RepairReportNum> queryRepairByYearAll(RepairReportNum condition);
	/**
	 * 完成维修次数(月)
	 */
	List<RepairReportNum> queryRepairByMonthFinished(RepairReportNum condition);
	/**
	 * 所有维修次数(月)
	 */
	List<RepairReportNum> queryRepairByMonthAll(RepairReportNum condition);
}
