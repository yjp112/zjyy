package com.supconit.meeting.services;

import java.util.List;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.meeting.entities.MeetingPerson;


public interface MeetingPersonService extends BaseBusinessService<MeetingPerson, Long> {
	/**
	 * 查询用户及部门信息
	 */
	MeetingPerson selectPersonAndDeptInfo(Long id);
	
	
	/**
	 * 查询与会者
	 */
	List<MeetingPerson> selectAttendees(Long reserveId);
}
