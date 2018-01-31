package com.supconit.employee.leave.services;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.employee.leave.entities.LeaveApply;

import hc.base.domains.Pageable;

public interface LeaveApplyService extends BaseBusinessService<LeaveApply, Long> {
	/**
	 * 分页查询
	 */
	Pageable<LeaveApply> findByPage(Pageable<LeaveApply> pager,LeaveApply condition);
	
	void insert(LeaveApply leave,String[] fileorignal,String[] filename,String[] delfiles,String fileLength);
	void update(LeaveApply leave,String[] fileorignal,String[] filename,String[] delfiles,String fileLength);
	
	void submitProcess(LeaveApply leave,String[] fileorignal,String[] filename,String[] delfiles,String fileLength);
	void startProcess(LeaveApply leave,String[] fileorignal,String[] filename,String[] delfiles, String fileLength); 
	
    void deleteProcessInstance(Long id);
    /**
     * 查询一段时间内的请假天数和小时
     */
    LeaveApply sumByConditions(LeaveApply condition);
}
