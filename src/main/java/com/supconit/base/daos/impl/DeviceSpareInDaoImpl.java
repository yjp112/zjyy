package com.supconit.base.daos.impl;

import java.util.HashMap;
import java.util.Map;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.base.daos.DeviceSpareInDao;
import com.supconit.base.entities.DeviceSpareIn;
import com.supconit.common.daos.AbstractBaseDao;
@Repository
public class DeviceSpareInDaoImpl extends AbstractBaseDao<DeviceSpareIn, Long> implements
	DeviceSpareInDao{
	private static final String NAMESPACE=DeviceSpareIn.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	@Override
	public Pageable<DeviceSpareIn> findByCondition(Pagination<DeviceSpareIn> pager,
			DeviceSpareIn condition) {
		return findByPager(pager, "findByCondition", "countByCondition", condition);
	}
	@Override
	public DeviceSpareIn findById(Long id,String modelType) {
		Map map=new HashMap();
		map.put("id", id);
		map.put("modelType", modelType);
		return selectOne("findById",map);
	}
	
	@Override
	public DeviceSpareIn findByDeviceId(Long deviceId) {
		return selectOne("findByDeviceId",deviceId);
	}
	
	
	@Override
	public DeviceSpareIn findTopOne() {
		return selectOne("findTopOne");
	}
	@Override
	public DeviceSpareIn findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
