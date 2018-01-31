package com.supconit.maintain.services;

import hc.base.domains.Pageable;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.maintain.entities.MaintainTask;

public interface MaintainTaskService extends BaseBusinessService<MaintainTask, Long>{
	void startProcess(MaintainTask task);
	/**
	 * 分页查询
	 */
	Pageable<MaintainTask> findByCondition(Pageable<MaintainTask> pager,MaintainTask condition);
	/**
	 * 保存
	 */
	void update(MaintainTask task,String[] fileorignal,String[] filename,String[] delfiles,String fileLength);
	/**
	 * 填写保养单提交
	 */
	void submit(MaintainTask task,String[] fileorignal,String[] filename,String[] delfiles,String fileLength);
	/**
	 * 主管工程师审核提交
	 */
	void submit(MaintainTask task);
	/**
	 * 查询设备第一次保养
	 */
	MaintainTask getBydeviceId(Long deviceId);
}
