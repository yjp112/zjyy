package com.supconit.meeting.daos.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.meeting.daos.RoomReservationDao;
import com.supconit.meeting.entities.MeetingPerson;
import com.supconit.meeting.entities.RoomReservation;

import hc.base.domains.Pageable;


@Repository
public class RoomReservationDaoImpl extends AbstractBaseDao<RoomReservation, Long> implements RoomReservationDao {

	private static final String	NAMESPACE = RoomReservation.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	
	@Override
	public List<RoomReservation> findByConditions(RoomReservation roomReservation) {
		Map param = new HashMap(1);
		param.put("condition", roomReservation);
		return selectList("findByConditions", param);
	}
	
	@Override
	public Integer queryRoomIsReverse(RoomReservation roomReservation) {
		Map param = new HashMap(1);
		param.put("condition", roomReservation);
		return getSqlSession().selectOne(NAMESPACE + ".queryRoomIsReverse", param);
	}
	
	@Override
	public void BatchInsertAttendee(List<MeetingPerson> list, Long reserveId) {
		Map param = new HashMap(2);
		param.put("list",list);
		param.put("roomReservationId",reserveId);
		insert("batch_insert",param);
	}

	@Override
	public void deleteAttendee(Long reserveId) {
		Map param = new HashMap(1);
		param.put("roomReservationId",reserveId);
		delete("deleteAttendee", param);
	}
	
	@Override
	public Pageable<RoomReservation> queryMyReserveMeeting(Pageable<RoomReservation> pager,Map param) {
		return findByPager(pager,"queryMyReserveMeeting", "countMyReserveMeeting",null, param);
	}

	@Override
	public Pageable<RoomReservation> queryMyAttendMeeting(Pageable<RoomReservation> pager,Map param) {
		return findByPager(pager,"queryMyAttendMeeting", "countMyAttendMeeting",null, param);
	}

	@Override
	public Long countReservationByRoomId(Long roomId) {
		Map param = new HashMap(1);
		param.put("roomId", roomId);
		return getSqlSession().selectOne(NAMESPACE + ".countReservationByRoomId", param);
	}

	@Override
	public void updateActualMeetingTime(RoomReservation roomReservation) {
		update("updateActualMeetingTime", roomReservation);
	}

	

}
