
package com.supconit.base.daos.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.base.daos.DevicePiontsDao;
import com.supconit.base.entities.DevicePionts;
import com.supconit.common.daos.AbstractBaseDao;

import hc.base.domains.Pageable;

@Repository
public class DevicePiontsDaoImpl extends AbstractBaseDao<DevicePionts, Long> implements DevicePiontsDao {

    private static final String	NAMESPACE	= DevicePionts.class.getName();
	
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
    
	@Override
	public Pageable<DevicePionts> findByCondition(Pageable<DevicePionts> pager, DevicePionts condition) {
		return findByPager(pager,"findByCondition", "countByCondition", condition);
	}

	@Override
	public int deleteByIds(Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return update("deleteByIds", map);
	}
    
    @Override
	public void insert(List<DevicePionts> list) {
		insert("insert",list);
	}
    
    @Override
	public void deleteByDeviceId(Long deviceId) {
		delete("deleteByDeviceId",deviceId);
	}

}