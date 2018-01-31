package com.supconit.meeting.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.meeting.daos.MeetingPersonDao;
import com.supconit.meeting.entities.MeetingPerson;
import com.supconit.meeting.services.MeetingPersonService;

@Service
public class MeetingPersonServiceImpl extends AbstractBaseBusinessService<MeetingPerson, Long> implements MeetingPersonService{

	@Autowired
	private MeetingPersonDao personDao;
	
	@Override
	public void deleteById(Long arg0) {
		
	}

	@Override
	public MeetingPerson getById(Long id) {
		return null;
	}

	@Override
	public void insert(MeetingPerson arg0) {
		
	}

	@Override
	public void update(MeetingPerson arg0) {
		
	}

	@Override
	public MeetingPerson selectPersonAndDeptInfo(Long id) {
		return personDao.selectPersonAndDeptInfo(id);
	}

	@Override
	public List<MeetingPerson> selectAttendees(Long reserveId) {
		return personDao.selectAttendees(reserveId);
	}

}
