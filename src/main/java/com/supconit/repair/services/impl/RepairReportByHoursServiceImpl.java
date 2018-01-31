package com.supconit.repair.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.repair.daos.RepairReportByHoursDao;
import com.supconit.repair.entities.RepairReportByHours;
import com.supconit.repair.services.RepairReportByHoursService;

/**
 * @文件名: DeviceReportServiceImpl
 * @创建日期: 13-9-9
 * @版权: Copyright(c)2013
 * @开发人员:
 * @版本:
 * @描述:
 */
@Service
@Transactional
public class RepairReportByHoursServiceImpl extends AbstractBaseBusinessService<RepairReportByHours,Long> implements RepairReportByHoursService {
	@Autowired
	private RepairReportByHoursDao	reportDao;

	@Override
	public RepairReportByHours getById(Long arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(RepairReportByHours arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(RepairReportByHours arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<RepairReportByHours> queryRepairByHours(
			RepairReportByHours rrbc) {
		return reportDao.queryRepairByHours(rrbc);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}



}
