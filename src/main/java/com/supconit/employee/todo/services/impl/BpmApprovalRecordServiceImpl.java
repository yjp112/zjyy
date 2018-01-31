package com.supconit.employee.todo.services.impl;

import hc.base.domains.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.employee.todo.daos.BpmApprovalRecordDao;
import com.supconit.employee.todo.entities.BpmApprovalRecord;
import com.supconit.employee.todo.services.BpmApprovalRecordService;

import java.util.List;

@Service
public class BpmApprovalRecordServiceImpl extends AbstractBaseBusinessService<BpmApprovalRecord, Long> implements BpmApprovalRecordService{

	@Autowired
	private BpmApprovalRecordDao bpmApprovalRecordDao;

	@Override
	public BpmApprovalRecord getById(Long arg0) {
		return null;
	}

	@Override
	public void insert(BpmApprovalRecord entity) {
	}

	@Override
	public void update(BpmApprovalRecord entity) {
	}

	@Override
	public void deleteById(Long id) {
	}

	@Override
	public Pageable<BpmApprovalRecord> findByPage(
			Pageable<BpmApprovalRecord> pager, BpmApprovalRecord condition) {
		return bpmApprovalRecordDao.findByPage(pager, condition);
	}

	@Override
	public List<BpmApprovalRecord> selectHistoryBpms(BpmApprovalRecord condition) {
		return bpmApprovalRecordDao.selectHistoryBpms(condition);
	}

	@Override
	public long countHistoryBpms(BpmApprovalRecord condition) {
		return bpmApprovalRecordDao.countHistoryBpms(condition);
	}

	@Override
	public List<BpmApprovalRecord> selectApprovalRecords(BpmApprovalRecord condition) {
		return this.bpmApprovalRecordDao.selectApprovalRecords(condition);
	}

}
