package com.supconit.maintain.daos;

import hc.base.domains.Pageable;

import java.util.List;

import com.supconit.common.daos.BaseDao;
import com.supconit.maintain.entities.MaintainTask;

public interface MaintainTaskDao extends BaseDao<MaintainTask, Long>{
	void readyForTask();
	List<MaintainTask> findReadyTask();
	/**
	 * 分页查询
	 */
	Pageable<MaintainTask> findByCondition(Pageable<MaintainTask> pager,MaintainTask condition);
	/**
	 * 查询任务列表
	 */
	List<MaintainTask> selectMaintainTaskList(MaintainTask condition);
	/**
	 * 查询流程ID
	 */
	List<Long> findProcessIds(List<Long> ids);
	/**
	 * 查询设备第一次保养
	 */
	MaintainTask getBydeviceId(Long deviceId);
}
