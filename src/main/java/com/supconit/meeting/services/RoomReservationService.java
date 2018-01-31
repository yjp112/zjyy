package com.supconit.meeting.services;

import java.util.Map;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.meeting.entities.RoomReservation;

import hc.base.domains.Pageable;


public interface RoomReservationService extends BaseBusinessService<RoomReservation, Long> {
	/**
	 * 查询该段时间是否可预约
	 */
	boolean queryRoomIsReverse(RoomReservation roomReservation);
	/**
	 * 分页查询我预定的会议
	 */
	Pageable<RoomReservation> queryMyReserveMeeting(Pageable<RoomReservation> pager, Map map);
	/**
	 * 分页查询我参加的会议
	 */
	Pageable<RoomReservation> queryMyAttendMeeting(Pageable<RoomReservation> pager, Map map);
	/**
	 * 更新会议结束时间
	 */
	void updateMeetingEndTime(RoomReservation roomReservation);
	/**
	 * 查询该会议室是否使用过
	 */
	boolean countReservationByRoomId(Long roomId);
	/**
	 * 修改时间会议时间
	 */
	void updateActualMeetingTime(RoomReservation roomReservation);
}
