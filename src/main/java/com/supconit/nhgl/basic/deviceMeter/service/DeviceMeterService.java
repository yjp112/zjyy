package com.supconit.nhgl.basic.deviceMeter.service;

import java.util.List;

import com.supconit.nhgl.basic.deviceMeter.entities.DeviceMeter;

import hc.base.domains.Pageable;
import hc.orm.BasicOrmService;




public interface DeviceMeterService extends BasicOrmService<DeviceMeter,Long>{
	Pageable<DeviceMeter> findByCondition(Pageable<DeviceMeter> pager,DeviceMeter condition);
//	String buildTree(List<SubSystemInfo> slist);

	List<DeviceMeter> findDmByCondition(DeviceMeter dm);
	void deleteByDeviceId(DeviceMeter dm);

	void insertList(List<DeviceMeter> dmList);
}
