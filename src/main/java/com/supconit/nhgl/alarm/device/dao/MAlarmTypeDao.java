package com.supconit.nhgl.alarm.device.dao;

import java.util.List;

import com.supconit.nhgl.alarm.device.entities.IAlarmType;

import hc.orm.BasicDao;


public interface MAlarmTypeDao extends BasicDao<IAlarmType, Long>{
	public List<IAlarmType> findAllTypes();
}
