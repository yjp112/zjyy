package com.supconit.employee.todo.daos;

import hc.base.domains.Pageable;

import com.supconit.common.daos.BaseDao;
import com.supconit.employee.todo.entities.ExUserTask;

import java.util.List;

public interface ExUserTaskDao extends BaseDao<ExUserTask, Long>{
	/**
	 * 分页查询
	 */ 
	Pageable<ExUserTask> findByPage(Pageable<ExUserTask> pager,ExUserTask condition);

	/**
	 * 手机端使用
	 */
	List<ExUserTask> selectUserTasks(ExUserTask condition);

	long countUserTasks(ExUserTask condition);
}

