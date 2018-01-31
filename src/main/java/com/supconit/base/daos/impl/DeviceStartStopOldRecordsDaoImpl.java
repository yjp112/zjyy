
package com.supconit.base.daos.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.base.daos.DeviceStartStopOldRecordsDao;
import com.supconit.base.entities.DeviceStartStopOldRecords;
import com.supconit.common.daos.AbstractBaseDao;

import hc.base.domains.Pageable;


@Repository
public class DeviceStartStopOldRecordsDaoImpl extends AbstractBaseDao<DeviceStartStopOldRecords, Long> implements DeviceStartStopOldRecordsDao {

    private static final String	NAMESPACE	= DeviceStartStopOldRecords.class.getName();
	
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
    
	@Override
	public Pageable<DeviceStartStopOldRecords> findByCondition(Pageable<DeviceStartStopOldRecords> pager, DeviceStartStopOldRecords condition) {
		return findByPager(pager,"findByCondition", "countByCondition", condition);
	}

	@Override
	public int deleteByIds(Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return update("deleteByIds", map);
	}
    
    @Override
	public DeviceStartStopOldRecords findById(Long id) {
		return selectOne("findById",id);
	}
    @Override
	public List<DeviceStartStopOldRecords> findNewData() {
		return selectList("findNewData");
	}
}