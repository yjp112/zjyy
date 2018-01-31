package com.supconit.employee.todo.daos.impl;

import hc.base.domains.Pageable;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.employee.todo.daos.ExUserTaskDao;
import com.supconit.employee.todo.entities.ExUserTask;

import java.util.List;

@Repository
public class ExUserTaskDaoImpl extends AbstractBaseDao<ExUserTask, Long> implements ExUserTaskDao{
	private static final String	NAMESPACE = ExUserTask.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	@Override
	public Pageable<ExUserTask> findByPage(Pageable<ExUserTask> pager,
			ExUserTask condition) {
		// TODO Auto-generated method stub
		return findByPager(pager, "findByConditions", "countByConditions", condition);
	}

	@Override
	public List<ExUserTask> selectUserTasks(ExUserTask condition) {
		return this.selectList("selectUserTasks",condition);
	}

	@Override
	public long countUserTasks(ExUserTask condition) {
		return this.selectOne("countUserTasks",condition);
	}

}
