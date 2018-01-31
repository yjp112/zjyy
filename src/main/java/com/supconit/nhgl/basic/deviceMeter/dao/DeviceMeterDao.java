package com.supconit.nhgl.basic.deviceMeter.dao;

import java.util.List;

import com.supconit.nhgl.basic.deviceMeter.entities.DeviceMeter;

import hc.base.domains.Pageable;
import hc.orm.BasicDao;



public interface DeviceMeterDao extends BasicDao<DeviceMeter,Long>{
	Pageable<DeviceMeter> findByCondition(Pageable<DeviceMeter> pager, DeviceMeter condition);

	List<DeviceMeter> findDmByCondition(DeviceMeter dm);

	void insertList(List<DeviceMeter> dmList);

	void deleteByDeviceId(DeviceMeter dm);
}
