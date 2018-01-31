
package com.supconit.base.daos.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.base.daos.SubDeviceDao;
import com.supconit.base.entities.SubDevice;
import com.supconit.common.daos.AbstractBaseDao;

import hc.base.domains.Pageable;

@Repository
public class SubDeviceDaoImpl extends AbstractBaseDao<SubDevice, Long> implements SubDeviceDao {

    private static final String	NAMESPACE	= SubDevice.class.getName();
	
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
    
	@Override
	public Pageable<SubDevice> findByCondition(Pageable<SubDevice> pager, SubDevice condition) {
		return findByPager(pager,"findByCondition", "countByCondition", condition);
	}

	@Override
	public int deleteByIds(Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return update("deleteByIds", map);
	}
    
    @Override
	public void insert(List<SubDevice> list) {
		insert("insert",list);
	}
    
    @Override
	public void deleteByDeviceId(Long deviceId) {
		delete("deleteByDeviceId",deviceId);
	}

}