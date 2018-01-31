package com.supconit.nhgl.basic.deviceMeter.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.basic.deviceMeter.dao.DeviceMeterDao;
import com.supconit.nhgl.basic.deviceMeter.entities.DeviceMeter;

import hc.base.domains.Pageable;
import hc.orm.AbstractBasicDaoImpl;

@Repository
public class DeviceMeterDaoImpl extends AbstractBasicDaoImpl<DeviceMeter,Long> implements DeviceMeterDao {
	private static final String NAMESPACE=DeviceMeter.class.getName();
	
	@Override
	public Pageable<DeviceMeter> findByCondition(
			Pageable<DeviceMeter> pager, DeviceMeter condition) {
		return findByPager(pager, "findByCondition", "countByCondition", condition);
	}
	
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public List<DeviceMeter> findDmByCondition(DeviceMeter dm) {
		return selectList("findByCondition", dm);
	}

	@Override
	public void deleteByDeviceId(DeviceMeter dm) {
		delete("deleteByDeviceId", dm);
	}

	@Override
	public void insertList(List<DeviceMeter> dmList) {
		insert("insertList", dmList);
	}

}