package com.supconit.employee.todo.daos.impl;

import hc.base.domains.Pageable;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.employee.todo.daos.BpmApprovalRecordDao;
import com.supconit.employee.todo.entities.BpmApprovalRecord;

import java.util.List;

@Repository
public class BpmApprovalRecordImpl extends AbstractBaseDao<BpmApprovalRecord, Long> implements BpmApprovalRecordDao{
	private static final String	NAMESPACE = BpmApprovalRecord.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	@Override
	public Pageable<BpmApprovalRecord> findByPage(
			Pageable<BpmApprovalRecord> pager, BpmApprovalRecord condition) {
		return findByPager(pager, "findByConditions", "countByConditions", condition);
	}

	@Override
	public List<BpmApprovalRecord> selectHistoryBpms(BpmApprovalRecord condition) {
		return this.selectList("selectHistoryBpms",condition);
	}

	@Override
	public long countHistoryBpms(BpmApprovalRecord condition) {
		return this.selectOne("countHistoryBpms",condition);
	}

	@Override
	public List<BpmApprovalRecord> selectApprovalRecords(BpmApprovalRecord condition) {
		return this.selectList("selectApprovalRecords",condition);
	}

}
