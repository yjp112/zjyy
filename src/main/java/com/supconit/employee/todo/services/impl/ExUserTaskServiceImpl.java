package com.supconit.employee.todo.services.impl;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;
import hc.bpm.entities.UserTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.employee.todo.daos.BpmApprovalRecordDao;
import com.supconit.employee.todo.daos.ExUserTaskDao;
import com.supconit.employee.todo.entities.BpmApprovalRecord;
import com.supconit.employee.todo.entities.ExUserTask;
import com.supconit.employee.todo.services.BpmApprovalRecordService;
import com.supconit.employee.todo.services.ExUserTaskService;

import java.util.List;

@Service
public class ExUserTaskServiceImpl extends AbstractBaseBusinessService<ExUserTask, Long> implements ExUserTaskService{
	@Autowired
	private ExUserTaskDao exuserTaskDao;

	@Override
	public ExUserTask getById(Long arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(ExUserTask entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(ExUserTask entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Pageable<ExUserTask> findByPage(Pagination<ExUserTask> pager,
			ExUserTask condition) {
		// TODO Auto-generated method stub
		return exuserTaskDao.findByPage(pager, condition);
	}

	@Override
	public List<ExUserTask> selectUserTasks(ExUserTask condition) {
		return this.exuserTaskDao.selectUserTasks(condition);
	}

	@Override
	public long countUserTasks(ExUserTask condition) {
		return this.exuserTaskDao.countUserTasks(condition);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}
}
