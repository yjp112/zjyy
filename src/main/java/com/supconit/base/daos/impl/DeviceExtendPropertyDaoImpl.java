
package com.supconit.base.daos.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.base.daos.DeviceExtendPropertyDao;
import com.supconit.base.entities.DeviceExtendProperty;
import com.supconit.common.daos.AbstractBaseDao;

import hc.base.domains.Pageable;


@Repository
public class DeviceExtendPropertyDaoImpl extends AbstractBaseDao<DeviceExtendProperty, Long> implements DeviceExtendPropertyDao {

    private static final String	NAMESPACE	= DeviceExtendProperty.class.getName();
	
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
    
	@Override
	public Pageable<DeviceExtendProperty> findByCondition(Pageable<DeviceExtendProperty> pager, DeviceExtendProperty condition) {
		return findByPager(pager,"findByCondition", "countByCondition", condition);
	}

	@Override
	public int deleteByIds(Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return update("deleteByIds", map);
	}
    
    @Override
	public DeviceExtendProperty findById(Long id) {
		return selectOne("findById",id);
	}
    
    @Override
	public void insert(List<DeviceExtendProperty> list) {
		insert("insertLst",list);
	}
    
    @Override
	public void deleteByDeviceId(Long deviceId) {
		delete("deleteByDeviceId",deviceId);
	}
}