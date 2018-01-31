package com.supconit.meeting.daos.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.meeting.daos.AccessRecordDao;
import com.supconit.meeting.entities.AccessRecord;

@Repository
public class AccessRecordDaoImpl extends AbstractBaseDao<AccessRecord, Long> implements AccessRecordDao{
	private static final String	NAMESPACE = AccessRecord.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	@Override
	public List<AccessRecord> findRecordByTime(AccessRecord condition) {
		Map param = new HashMap(1);
		param.put("condition", condition);
		return selectList("findRecordByTime", param);
	}
	
	
}
