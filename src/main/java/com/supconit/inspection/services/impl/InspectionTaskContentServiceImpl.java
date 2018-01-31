package com.supconit.inspection.services.impl;

import hc.base.domains.Pageable;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.inspection.daos.InspectionTaskContentDao;
import com.supconit.inspection.entities.InspectionTaskContent;
import com.supconit.inspection.services.InspectionTaskContentService;
import com.supconit.maintain.entities.MaintainTaskContent;

@Service
public class InspectionTaskContentServiceImpl extends AbstractBaseBusinessService<InspectionTaskContent, Long> implements InspectionTaskContentService{

	@Autowired
	private InspectionTaskContentDao inspectionTaskContentDao;

	@Override
	public InspectionTaskContent getById(Long arg0) {
		return null;
	}

	@Override
	public void insert(InspectionTaskContent entity) {
		
	}

	@Override
	public void update(InspectionTaskContent entity) {
		inspectionTaskContentDao.update(entity);
	}

	@Override
	public void deleteById(Long id) {
		
	}
	
	@Override
	public List<InspectionTaskContent> selectInspectionTaskContentList(
			String inspectionCode) {
		return inspectionTaskContentDao.selectInspectionTaskContentList(inspectionCode);
	}

	@Override
	public Pageable<InspectionTaskContent> findByCondition(Pageable<InspectionTaskContent> pager,
			InspectionTaskContent condition) {
		inspectionTaskContentDao.findByCondition(pager, condition);
		int num = 1;
		for (InspectionTaskContent inspectionTaskContent : pager) {
			inspectionTaskContent.setRowNum(num);
			num++;
		}
		return pager;
	}

}
