package com.supconit.meeting.services.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.meeting.daos.AccessRecordDao;
import com.supconit.meeting.entities.AccessRecord;
import com.supconit.meeting.entities.MeetingPerson;
import com.supconit.meeting.entities.RoomReservation;
import com.supconit.meeting.services.AccessRecordService;

@Service
public class AccessRecordServiceImpl extends AbstractBaseBusinessService<AccessRecord, Long> implements AccessRecordService{
	@Autowired
	private AccessRecordDao accessRecordDao;
	
	@Override
	public AccessRecord getById(Long arg0) {
		return null;
	}

	@Override
	public void insert(AccessRecord entity) {
		
	}

	@Override
	public void update(AccessRecord entity) {
		
	}

	@Override
	public void deleteById(Long id) {
		
	}

	@Override
	public List<String> findRecordByTime(AccessRecord condition,RoomReservation reserveMeeting) {
		List<String> statusList = new ArrayList<>();
		List<MeetingPerson> personDetailList = reserveMeeting.getPersonDetailList();
		if(null!=personDetailList && personDetailList.size()>0 && null==personDetailList.get(0).getId()){
			personDetailList.remove(0);
		}
		Calendar cal = Calendar.getInstance();
		for (MeetingPerson meetingPerson : personDetailList) {
			condition.setPersonId(meetingPerson.getId());
			List<AccessRecord> list = accessRecordDao.findRecordByTime(condition);
			Date startTime = reserveMeeting.getActualMeetingStart();
			Date endTime = reserveMeeting.getActualMeetingEnd();
			if(null != list){
				if(list.size()==0){//迟到或者早退
					statusList.add("缺席");
				}else if(list.size()==1){
					statusList.add("迟到/早退");
				}else{
					AccessRecord first = list.get(0);
					Date fTime = new Date(first.getEventTime());//第一次时间
					AccessRecord last = list.get(list.size()-1);
					Date lTime = new Date(last.getEventTime());//最后一次时间
					
					cal.setTime(startTime);
					cal.add(Calendar.MINUTE, 10);
					Date start_delay = cal.getTime();//取实际开始后10分钟
					
					cal.setTime(endTime);
					cal.add(Calendar.MINUTE, -10);
					Date end_early = cal.getTime();//取实际结束前10分钟
					
					if(fTime.before(start_delay) && lTime.after(end_early)){
						statusList.add("正常");
					}else{
						statusList.add("迟到/早退");
					}
				}
			}else{
				statusList.add("缺席");
			}
		}
		return statusList;
	}

	@Override
	public Map<String, String> findArriveCondition(AccessRecord condition,
			RoomReservation reserveMeeting) {
		Map<String,String> map = new HashMap<String, String>();
		StringBuilder sbAll = new StringBuilder();
		StringBuilder sbArrive = new StringBuilder();
		StringBuilder sbUnArrive = new StringBuilder();
		List<MeetingPerson> personDetailList = reserveMeeting.getPersonDetailList();
		if(null!=personDetailList && personDetailList.size()>0 && null==personDetailList.get(0).getId()){
			personDetailList.remove(0);
		}
		int countUnArrive = 0;
		int countArrive = 0;
		for (MeetingPerson meetingPerson : personDetailList) {
			condition.setPersonId(meetingPerson.getId());
			List<AccessRecord> list = accessRecordDao.findRecordByTime(condition);
			if(null == list || list.size()==0){
				sbUnArrive.append(meetingPerson.getName()).append(",");//未到
				countUnArrive++;
			}else{
				sbArrive.append(meetingPerson.getName()).append(",");//已到
				countArrive++;
			}
		}
		String sbArrives = "";
		if(countArrive>0) sbArrives = sbArrive.substring(0, sbArrive.length()-1).toString();
		String sbUnArrives = "";
		if(countUnArrive>0) sbUnArrives = sbUnArrive.substring(0, sbUnArrive.length()-1).toString();
		
		map.put("arrive", sbArrives);
		map.put("countArrive", String.valueOf(countArrive));
		map.put("unArrive", sbUnArrives);
		map.put("countUnArrive", String.valueOf(countUnArrive));
		if(sbArrives.length()>0){
			sbAll.append(sbArrives);
			if(sbUnArrives.length()>0){
				sbAll.append(",").append(sbUnArrives);
			}
		}else{
			sbAll.append(sbUnArrives);
		}
		map.put("all", sbAll.toString());
		map.put("countAll", String.valueOf(countUnArrive+countArrive));
		return map;
	}


}
