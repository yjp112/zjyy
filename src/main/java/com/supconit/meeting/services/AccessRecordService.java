package com.supconit.meeting.services;

import java.util.List;
import java.util.Map;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.meeting.entities.AccessRecord;
import com.supconit.meeting.entities.RoomReservation;

public interface AccessRecordService extends BaseBusinessService<AccessRecord, Long> {
	/**
	 * 查询到会人员的签到状态
	 */
	List<String> findRecordByTime(AccessRecord condition,RoomReservation reserveMeeting);
	/**
	 * 查询会议人员是否到会
	 */
	Map<String,String> findArriveCondition(AccessRecord condition,RoomReservation reserveMeeting);
}
