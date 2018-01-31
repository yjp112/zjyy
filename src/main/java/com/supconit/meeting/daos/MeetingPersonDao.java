package com.supconit.meeting.daos;

import java.util.List;

import com.supconit.common.daos.BaseDao;
import com.supconit.meeting.entities.MeetingPerson;


public interface MeetingPersonDao extends BaseDao<MeetingPerson, Long>{
	/**
	 * 查询用户及部门信息
	 */
	MeetingPerson selectPersonAndDeptInfo(Long id);
	
	/**
	 * 查询与会者
	 */
	List<MeetingPerson> selectAttendees(Long reserveId);
}
