package com.supconit.inspection.daos;

import hc.base.domains.Pageable;

import java.util.List;

import com.supconit.common.daos.BaseDao;
import com.supconit.inspection.entities.InspectionTask;

public interface InspectionTaskDao extends BaseDao<InspectionTask, Long>{
	public void readyForTask();
	public List<InspectionTask> findReadyTask();
	/**
	 * 分页查询
	 */
	Pageable<InspectionTask> findByCondition(Pageable<InspectionTask> pager,InspectionTask condition);
	/**
	 * 查询任务列表
	 */
	List<InspectionTask> selectInspectionTaskList(InspectionTask condition);
	/**
	 * 查询流程ID
	 */
	List<Long> findProcessIds(List<Long> ids);
	/**
	 * 查询设备第一次巡检
	 */
	InspectionTask getBydeviceId(Long deviceId);
}
