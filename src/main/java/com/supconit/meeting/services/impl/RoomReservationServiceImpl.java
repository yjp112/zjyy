package com.supconit.meeting.services.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.common.exceptions.BusinessDoneException;
import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.meeting.daos.RoomReservationDao;
import com.supconit.meeting.entities.MeetingPerson;
import com.supconit.meeting.entities.RoomReservation;
import com.supconit.meeting.services.RoomReservationService;

import hc.base.domains.Pageable;


@Service
public class RoomReservationServiceImpl extends AbstractBaseBusinessService<RoomReservation, Long> implements RoomReservationService{
	@Autowired
	private RoomReservationDao roomReservationDao;
	
	@Override
	@Transactional
	public void deleteById(Long id) {
		roomReservationDao.deleteById(id);//删除预定的会议
		roomReservationDao.deleteAttendee(id);//删除与会者
	}

	@Override
	public RoomReservation getById(Long id) {
		return roomReservationDao.getById(id);
	}

	@Override
	@Transactional
	public synchronized void insert(RoomReservation roomReservation) {
		boolean flag = queryRoomIsReverse(roomReservation);
		if(!flag){
			throw new BusinessDoneException("该时间段已有预约,请调整时间范围");  
		}
		roomReservationDao.insert(roomReservation);
		List<MeetingPerson> list = roomReservation.getPersonDetailList();
		if(null != list && list.size() > 0){//批量插入与会人员
			list.remove(0);
			MeetingPerson mp = new MeetingPerson();
			mp.setId(roomReservation.getMeetingCompere());
			list.add(mp);
			roomReservationDao.BatchInsertAttendee(list, roomReservation.getId());
		}
	}

	@Override
	@Transactional
	public synchronized void update(RoomReservation roomReservation) {
		boolean flag = queryRoomIsReverse(roomReservation);
		if(!flag){
			throw new BusinessDoneException("该时间段已有预约,请调整时间范围");  
		}
		roomReservationDao.update(roomReservation);
		roomReservationDao.deleteAttendee(roomReservation.getId());//删除旧的与会者
		List<MeetingPerson> list = roomReservation.getPersonDetailList();
		if(null != list && list.size() > 0){//批量插入与会人员
			list.remove(0);
			long meetingCompere = roomReservation.getMeetingCompere();
			boolean b = false;
			for (MeetingPerson meetingPerson : list) {
				if(null != meetingPerson.getId() && meetingCompere == meetingPerson.getId().longValue()){
					b = true;
					break;
				}
			}
			if(!b){
				MeetingPerson mp = new MeetingPerson();
				mp.setId(meetingCompere);
				list.add(mp);
			}
			roomReservationDao.BatchInsertAttendee(list, roomReservation.getId());
		}
	}

	@Override
	public boolean queryRoomIsReverse(RoomReservation roomReservation) {
		int count = roomReservationDao.queryRoomIsReverse(roomReservation);
		if(count > 0 ){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public Pageable<RoomReservation> queryMyReserveMeeting(Pageable<RoomReservation> pager, Map map) {
		return roomReservationDao.queryMyReserveMeeting(pager, map);
	}
	
	@Override
	public Pageable<RoomReservation> queryMyAttendMeeting(Pageable<RoomReservation> pager, Map map) {
		return roomReservationDao.queryMyAttendMeeting(pager, map);
	}

	@Override
	public void updateMeetingEndTime(RoomReservation roomReservation) {
		roomReservationDao.update(roomReservation);
	}

	@Override
	public boolean countReservationByRoomId(Long roomId) {
		return roomReservationDao.countReservationByRoomId(roomId)==0? false:true;
	}

	@Override
	public void updateActualMeetingTime(RoomReservation roomReservation) {
		roomReservationDao.updateActualMeetingTime(roomReservation);
	}

}
