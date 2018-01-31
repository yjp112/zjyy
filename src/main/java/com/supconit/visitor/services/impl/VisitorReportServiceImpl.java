package com.supconit.visitor.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.visitor.daos.VisitorReportDao;
import com.supconit.visitor.entities.VisitorReport;
import com.supconit.visitor.services.VisitorReportService;

@Service
public class VisitorReportServiceImpl extends AbstractBaseBusinessService<VisitorReport, Long> implements VisitorReportService {
	@Autowired
	private VisitorReportDao reportDao;

	@Override
	public void deleteById(Long arg0) {
		
	}

	@Override
	public VisitorReport getById(Long arg0) {
		return null;
	}

	@Override
	public void insert(VisitorReport arg0) {
	}

	@Override
	public void update(VisitorReport arg0) {
		
	}

	@Override
	public List<VisitorReport> findList(VisitorReport dept, int type){
		return reportDao.findList(dept, type);
	}

	
}
