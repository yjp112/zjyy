package com.supconit.repair.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.repair.daos.RepairReportBySpareDao;
import com.supconit.repair.entities.RepairReportBySpare;
import com.supconit.repair.services.RepairReportBySpareService;

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
public class RepairReportBySpareServiceImpl extends AbstractBaseBusinessService<RepairReportBySpare,Long> implements RepairReportBySpareService {
	@Autowired
	private RepairReportBySpareDao	reportDao;

	@Override
	public RepairReportBySpare getById(Long arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(RepairReportBySpare arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(RepairReportBySpare arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<RepairReportBySpare> queryRepairBySpare(
			RepairReportBySpare rrbc) {
		return reportDao.queryRepairBySpare(rrbc);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}



}
