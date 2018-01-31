package com.supconit.nhgl.alarm.device.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.alarm.device.dao.MAlarmTypeDao;
import com.supconit.nhgl.alarm.device.entities.IAlarmType;

import hc.orm.AbstractBasicDaoImpl;


@Repository
public class IAlarmTypeDaoImpl extends AbstractBasicDaoImpl<IAlarmType, Long> implements MAlarmTypeDao{
	//private static final String	NAMESPACE	= "com.supconit.montrol.entity.MAlarmType";
	private static final String	NAMESPACE	= IAlarmType.class.getName();

	@Override
	protected String getNamespace() {
		return NAMESPACE;
		
	}

	@Override
	public List<IAlarmType> findAllTypes() {
		return selectList("findAllTypes");
	}

	
}
