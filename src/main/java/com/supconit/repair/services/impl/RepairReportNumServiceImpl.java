package com.supconit.repair.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.repair.daos.RepairReportNumDao;
import com.supconit.repair.entities.RepairReportNum;
import com.supconit.repair.services.RepairReportNumService;

@Service
public class RepairReportNumServiceImpl extends AbstractBaseBusinessService<RepairReportNum,Long> implements RepairReportNumService {

	@Autowired
	RepairReportNumDao repairReportNumDao;
	
	@Override
	public RepairReportNum getById(Long id) {
		return null;
	}

	@Override
	public void insert(RepairReportNum entity) {
		
	}

	@Override
	public void update(RepairReportNum entity) {
		
	}

	@Override
	public void deleteById(Long id) {
		
	}

	@Override
	public List<RepairReportNum> queryRepairByYearFinished(
			RepairReportNum condition) {
		return repairReportNumDao.queryRepairByYearFinished(condition);
	}
	
	@Override
	public List<RepairReportNum> queryRepairByYearAll(RepairReportNum condition) {
		return repairReportNumDao.queryRepairByYearAll(condition);
	}

	@Override
	public List<RepairReportNum> queryRepairByMonthFinished(
			RepairReportNum condition) {
		return repairReportNumDao.queryRepairByMonthFinished(condition);
	}

	@Override
	public List<RepairReportNum> queryRepairByMonthAll(RepairReportNum condition) {
		return repairReportNumDao.queryRepairByMonthAll(condition);
	}
}
