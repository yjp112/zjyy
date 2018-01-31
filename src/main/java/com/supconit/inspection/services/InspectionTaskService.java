package com.supconit.inspection.services;

import hc.base.domains.Pageable;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.inspection.entities.InspectionTask;

public interface InspectionTaskService extends BaseBusinessService<InspectionTask, Long>{
	void startProcess(InspectionTask task);
	/**
	 * 分页查询
	 */
	Pageable<InspectionTask> findByCondition(Pageable<InspectionTask> pager,InspectionTask condition);
	/**
	 * 保存
	 */
	void update(InspectionTask task,String[] fileorignal,String[] filename,String[] delfiles,String fileLength);
	/**
	 * 填写巡检单提交
	 */
	void submit(InspectionTask task,String[] fileorignal,String[] filename,String[] delfiles,String fileLength);
	/**
	 * 主管工程师审核提交
	 */
	void submit(InspectionTask task);
	/**
	 * 查询设备第一次巡检
	 */
	InspectionTask getBydeviceId(Long deviceId);
}
