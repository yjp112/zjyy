package com.supconit.inspection.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.inspection.daos.InspectionSpareDao;
import com.supconit.inspection.entities.InspectionSpare;
import com.supconit.inspection.services.InspectionSpareService;

@Service
public class InspectionSpareServiceImpl extends AbstractBaseBusinessService<InspectionSpare, Long> implements InspectionSpareService{

	@Autowired
	private InspectionSpareDao inspectionSpareDao;

	@Override
	public InspectionSpare getById(Long arg0) {
		return null;
	}

	@Override
	public void insert(InspectionSpare entity) {
		
	}

	@Override
	public void update(InspectionSpare entity) {
		
	}

	@Override
	public void deleteById(Long id) {
		
	}
	
	@Override
	public List<InspectionSpare> selectInspectionSpareList(
			String inspectionCode) {
		return inspectionSpareDao.selectInspectionSpareList(inspectionCode);
	}

}
