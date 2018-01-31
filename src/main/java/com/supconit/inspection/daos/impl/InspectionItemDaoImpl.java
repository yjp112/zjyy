package com.supconit.inspection.daos.impl;

import hc.base.domains.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.inspection.daos.InspectionItemDao;
import com.supconit.inspection.entities.InspectionItem;

@Repository
public class InspectionItemDaoImpl extends AbstractBaseDao<InspectionItem, Long> implements InspectionItemDao{
	private static final String	NAMESPACE = InspectionItem.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	
	@Override
	public Pageable<InspectionItem> findByCondition(Pageable<InspectionItem> pager,
			InspectionItem condition) {
		return findByPager(pager, "findByConditions", "countByConditions", condition);
	}

	@Override
	public void deleteByDeviceId(Long deviceId) {
		Map<String,Long> param = new HashMap<String,Long>();
		param.put("deviceId", deviceId);
		delete("deleteByDeviceId", param);
	}
	
	@Override
	public List<InspectionItem> getDeviceByDeviceId(Long deviceId) {
		Map<String,Long> param = new HashMap<String,Long>();
		param.put("deviceId", deviceId);
		return selectList("getDeviceByDeviceId", param);
	}

	@Override
	public List<InspectionItem> getItemByDeviceId(Long deviceId) {
		Map<String,Long> param = new HashMap<String,Long>();
		param.put("deviceId", deviceId);
		return selectList("getItemByDeviceId", param);
	}

	@Override
	public void updateStartTimeByDeviceId(InspectionItem condition) {
		update("updateStartTimeByDeviceId", condition);
	}

}
