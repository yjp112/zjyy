package com.supconit.meeting.services;

import java.util.List;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.meeting.entities.RoomInfo;

import hc.base.domains.Pageable;

public interface RoomInfoService extends BaseBusinessService<RoomInfo, Long> {
	/**
	 * 分页查询
	 */
	Pageable<RoomInfo> findByPage(Pageable<RoomInfo> pager,RoomInfo condition);
	
	/**
	 * 查询预定情况
	 */
	Pageable<RoomInfo> findReservation(Pageable<RoomInfo> pager,RoomInfo condition,boolean b,long deptId);
	
	/**
	 * 会议统计
	 */
	Pageable<RoomInfo> meetingReport(Pageable<RoomInfo> pager,RoomInfo condition);
	
	/**
	 * 查询部门下的子部门
	 */
	List<Long> selectDeptChildren(long useDeptId);
}
