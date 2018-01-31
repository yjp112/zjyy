
package com.supconit.base.daos.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.base.daos.DeviceChangeDao;
import com.supconit.base.entities.DeviceChange;
import com.supconit.common.daos.AbstractBaseDao;

import hc.base.domains.Pageable;


@Repository
public class DeviceChangeDaoImpl extends AbstractBaseDao<DeviceChange, Long> implements DeviceChangeDao {

    private static final String	NAMESPACE	= DeviceChange.class.getName();
	
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
    
	@Override
	public Pageable<DeviceChange> findByCondition(Pageable<DeviceChange> pager, DeviceChange condition) {
		return findByPager(pager,"findByCondition", "countByCondition", condition);
	}

	@Override
	public int deleteByIds(Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return update("deleteByIds", map);
	}
    
    @Override
	public DeviceChange findById(Long id) {
		return selectOne("findById",id);
	}
	@Override
	public int deleteByDeviceId(Long deviceId) {
		return update("deleteByDeviceId", deviceId);
	}
    
}