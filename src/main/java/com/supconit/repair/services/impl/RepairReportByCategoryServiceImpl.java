package com.supconit.repair.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.repair.daos.RepairReportByCategoryDao;
import com.supconit.repair.entities.RepairReportByCategory;
import com.supconit.repair.services.RepairReportByCategoryService;

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
public class RepairReportByCategoryServiceImpl extends AbstractBaseBusinessService<RepairReportByCategory,Long> implements RepairReportByCategoryService {
	@Autowired
	private RepairReportByCategoryDao	reportDao;

	@Override
	public RepairReportByCategory getById(Long arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(RepairReportByCategory arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(RepairReportByCategory arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<RepairReportByCategory> queryRepairByCategory(
			RepairReportByCategory rrbc) {
		return reportDao.queryRepairByCategory(rrbc);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}



}
