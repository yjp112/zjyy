package com.supconit.repair.daos.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.repair.daos.RepairReportNumDao;
import com.supconit.repair.entities.RepairReportNum;

@Repository
public class RepairReportNumDaoImpl extends AbstractBaseDao<RepairReportNum, Long> implements RepairReportNumDao {
	private static final String NAMESPACE = RepairReportNum.class.getName();

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public List<RepairReportNum> queryRepairByYearFinished(RepairReportNum condition) {
		return selectList("queryRepairByYearFinished", condition);
	}

	@Override
	public List<RepairReportNum> queryRepairByYearAll(RepairReportNum condition) {
		return selectList("queryRepairByYearAll", condition);
	}

	@Override
	public List<RepairReportNum> queryRepairByMonthFinished(RepairReportNum condition) {
		return selectList("queryRepairByMonthFinished", condition);
	}

	@Override
	public List<RepairReportNum> queryRepairByMonthAll(RepairReportNum condition) {
		return selectList("queryRepairByMonthAll", condition);
	}

}
