package com.supconit.repair.daos.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.repair.daos.RepairReportByCategoryDao;
import com.supconit.repair.entities.RepairReportByCategory;

@Repository
public class RepairReportByCategoryDaoImpl extends AbstractBaseDao<RepairReportByCategory,Long> implements RepairReportByCategoryDao{
	private static final String	NAMESPACE	=RepairReportByCategory.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	@Override
	public List<RepairReportByCategory> queryRepairByCategory(
			RepairReportByCategory rrbc) {
		return selectList("queryRepairByCategory", rrbc);
	}

}
