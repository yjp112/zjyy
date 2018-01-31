
package com.supconit.base.daos.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.base.daos.DeviceStartStopRecordsDao;
import com.supconit.base.entities.DeviceStartStopRecords;
import com.supconit.common.daos.AbstractBaseDao;

import hc.base.domains.Pageable;


@Repository
public class DeviceStartStopRecordsDaoImpl extends AbstractBaseDao<DeviceStartStopRecords, Long> implements DeviceStartStopRecordsDao {

    private static final String	NAMESPACE	= DeviceStartStopRecords.class.getName();
	
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
    
	@Override
	public Pageable<DeviceStartStopRecords> findByCondition(Pageable<DeviceStartStopRecords> pager, DeviceStartStopRecords condition) {
		return findByPager(pager,"findByCondition", "countByCondition", condition);
	}

	@Override
	public int deleteByIds(Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return update("deleteByIds", map);
	}
    
    @Override
	public DeviceStartStopRecords findById(Long id) {
		return selectOne("findById",id);
	}
    @Override
	public List<DeviceStartStopRecords> findLastData() {
		return selectList("findLastData");
	}
}