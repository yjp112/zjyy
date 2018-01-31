package com.supconit.meeting.daos;

import java.util.List;
import java.util.Map;

import com.supconit.common.daos.BaseDao;
import com.supconit.meeting.entities.MeetingPerson;
import com.supconit.meeting.entities.RoomReservation;

import hc.base.domains.Pageable;


public interface RoomReservationDao extends BaseDao<RoomReservation, Long>{
	/**
	 * 通过条件查询
	 */
	List<RoomReservation> findByConditions(RoomReservation roomReservation);
	/**
	 * 查询该段时间是否可预约
	 */
	Integer queryRoomIsReverse(RoomReservation roomReservation);
	/**
	 * 批量插入与会者
	 */
	void BatchInsertAttendee(List<MeetingPerson> list,Long reserveId);
	/**
	 * 删除与会者
	 */
	void deleteAttendee(Long reserveId);
	/**
	 * 分页查询我预定的会议
	 */
	Pageable<RoomReservation> queryMyReserveMeeting(Pageable<RoomReservation> pager,Map param);
	/**
	 * 分页查询我参加的会议
	 */
	Pageable<RoomReservation> queryMyAttendMeeting(Pageable<RoomReservation> pager,Map param);
	/**
	 * 查询该会议室是否使用过
	 */
	Long countReservationByRoomId(Long roomId);
	/**
	 * 修改时间会议时间
	 */
	void updateActualMeetingTime(RoomReservation roomReservation);
}
