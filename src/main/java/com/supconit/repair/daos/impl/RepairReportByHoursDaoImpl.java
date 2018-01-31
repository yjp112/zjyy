package com.supconit.repair.daos.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.repair.daos.RepairReportByHoursDao;
import com.supconit.repair.entities.RepairReportByHours;

@Repository
public class RepairReportByHoursDaoImpl extends AbstractBaseDao<RepairReportByHours,Long> implements RepairReportByHoursDao{
	private static final String	NAMESPACE	=RepairReportByHours.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	@Override
	public List<RepairReportByHours> queryRepairByHours(
			RepairReportByHours rrbc) {
		return selectList("queryRepairByHours", rrbc);
	}

}
