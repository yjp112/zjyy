package com.supconit.meeting.daos.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.meeting.daos.MeetingPersonDao;
import com.supconit.meeting.entities.MeetingPerson;


@Repository
public class MeetingPersonDaoImpl extends AbstractBaseDao<MeetingPerson, Long> implements MeetingPersonDao{
	
	private static final String	NAMESPACE = MeetingPerson.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	
	@Override
	public MeetingPerson selectPersonAndDeptInfo(Long id) {
		return selectOne("selectPersonAndDeptInfo", id);
	}

	@Override
	public List<MeetingPerson> selectAttendees(Long reserveId) {
		Map param = new HashMap();
		MeetingPerson condition = new MeetingPerson();
		condition.setReserveId(reserveId);
		param.put("condition", condition);
		return selectList("selectAttendees", param);
	}
	
	
}
