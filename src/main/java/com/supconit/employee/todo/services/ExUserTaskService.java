package com.supconit.employee.todo.services;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.employee.todo.entities.ExUserTask;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.List;

public interface ExUserTaskService extends BaseBusinessService<ExUserTask, Long>{

	public Pageable<ExUserTask> findByPage(Pagination<ExUserTask> pager, ExUserTask condition);

	/**
	 * 手机端使用
	 */
	List<ExUserTask> selectUserTasks(ExUserTask condition);

	long countUserTasks(ExUserTask condition);
}
