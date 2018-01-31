package com.supconit.employee.todo.services;

import hc.base.domains.Pageable;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.employee.todo.entities.BpmApprovalRecord;

import java.util.List;

public interface BpmApprovalRecordService extends BaseBusinessService<BpmApprovalRecord, Long> {
	/**
	 * 分页查询
	 */
	Pageable<BpmApprovalRecord> findByPage(Pageable<BpmApprovalRecord> pager,BpmApprovalRecord condition);

	/**
	 * 手机端使用
	 */
	List<BpmApprovalRecord> selectHistoryBpms(BpmApprovalRecord condition);

	long countHistoryBpms(BpmApprovalRecord condition);

	List<BpmApprovalRecord> selectApprovalRecords(BpmApprovalRecord condition);
}
