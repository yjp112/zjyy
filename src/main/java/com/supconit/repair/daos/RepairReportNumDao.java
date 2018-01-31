package com.supconit.repair.daos;

import java.util.List;

import com.supconit.common.daos.BaseDao;
import com.supconit.repair.entities.RepairReportNum;

public interface RepairReportNumDao extends BaseDao<RepairReportNum,Long>{
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
