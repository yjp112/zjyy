package com.supconit.montrol.dao.impl;

import com.supconit.montrol.dao.DeviceAlarmLevelDao;
import com.supconit.montrol.entity.DeviceAlarmLevel;
import hc.base.domains.Pageable;
import hc.orm.AbstractBasicDaoImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DeviceAlarmLevelDaoImpl extends AbstractBasicDaoImpl<DeviceAlarmLevel, Long> implements DeviceAlarmLevelDao {

	private static final String NAMESPACE	= DeviceAlarmLevel.class.getName();

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}


    @Override
    public Pageable<DeviceAlarmLevel> findByPager(Pageable<DeviceAlarmLevel> pager, DeviceAlarmLevel condition) {
        return findByPager(pager, "selectPager", "countPager", condition);
    }

    @Override
    public List<DeviceAlarmLevel> findList(DeviceAlarmLevel condition) {
        return selectList("findAll",condition);
    }

    @Override
    public void delByTagId(Long condition) {
        delete("deleteByTagId",condition);
    }
}
