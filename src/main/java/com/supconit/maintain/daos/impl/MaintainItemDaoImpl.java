package com.supconit.maintain.daos.impl;

import hc.base.domains.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.maintain.daos.MaintainItemDao;
import com.supconit.maintain.entities.MaintainItem;

@Repository
public class MaintainItemDaoImpl extends AbstractBaseDao<MaintainItem, Long> implements MaintainItemDao{
	private static final String	NAMESPACE = MaintainItem.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	
	@Override
	public Pageable<MaintainItem> findByCondition(Pageable<MaintainItem> pager,
			MaintainItem condition) {
		return findByPager(pager, "findByConditions", "countByConditions", condition);
	}

	@Override
	public void deleteByDeviceId(Long deviceId) {
		Map<String,Long> param = new HashMap<String,Long>();
		param.put("deviceId", deviceId);
		delete("deleteByDeviceId", param);
	}
	
	@Override
	public List<MaintainItem> getDeviceByDeviceId(Long deviceId) {
		Map<String,Long> param = new HashMap<String,Long>();
		param.put("deviceId", deviceId);
		return selectList("getDeviceByDeviceId", param);
	}

	@Override
	public List<MaintainItem> getItemByDeviceId(Long deviceId) {
		Map<String,Long> param = new HashMap<String,Long>();
		param.put("deviceId", deviceId);
		return selectList("getItemByDeviceId", param);
	}

	@Override
	public void updateStartTimeByDeviceId(MaintainItem condition) {
		update("updateStartTimeByDeviceId", condition);
	}

}
