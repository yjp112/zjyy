package com.supconit.nhgl.alarm.device.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.nhgl.alarm.device.dao.MAlarmTypeDao;
import com.supconit.nhgl.alarm.device.entities.IAlarmType;
import com.supconit.nhgl.alarm.device.service.MAlarmTypeService;

@Service
public class MAlarmTypeServiceImpl implements MAlarmTypeService{
	@Autowired
	private MAlarmTypeDao alarmTypeDao;
	
	@Override
	public List<IAlarmType> findAllTypes() {
		List<IAlarmType> types = alarmTypeDao.findAllTypes();
		return types;
	}
	

}
