package com.supconit.repair.daos.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.repair.daos.RepairReportBySpareDao;
import com.supconit.repair.entities.RepairReportBySpare;

@Repository
public class RepairReportBySpareDaoImpl extends AbstractBaseDao<RepairReportBySpare,Long> implements RepairReportBySpareDao{
	private static final String	NAMESPACE	=RepairReportBySpare.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	@Override
	public List<RepairReportBySpare> queryRepairBySpare(
			RepairReportBySpare rrbc) {
		return selectList("queryRepairBySpare", rrbc);
	}

}
