package com.supconit.montrol.service.impl;

import com.supconit.montrol.dao.DeviceAlarmLevelDao;
import com.supconit.montrol.entity.DeviceAlarmLevel;
import com.supconit.montrol.service.DeviceAlarmLevelService;
import hc.base.domains.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DeviceAlarmLevelServiceImpl extends hc.orm.AbstractBasicOrmService<DeviceAlarmLevel, Long> implements DeviceAlarmLevelService {
    @Autowired
    private DeviceAlarmLevelDao deviceAlarmLevelDao;


    @Override
    @Transactional(readOnly = true)
    public Pageable<DeviceAlarmLevel> find(Pageable<DeviceAlarmLevel> pager, DeviceAlarmLevel condition) {
        return deviceAlarmLevelDao.findByPager(pager, condition);
    }

    @Override
    public List<DeviceAlarmLevel> findList(DeviceAlarmLevel condition) {
        return deviceAlarmLevelDao.findList(condition);
    }

    @Override
    public void delByTagId(Long condition) {
        deviceAlarmLevelDao.delByTagId(condition);
    }


    @Override
    @Transactional(readOnly = true)
    public DeviceAlarmLevel getById(Long aLong) {
        return deviceAlarmLevelDao.getById(aLong);
    }

    @Override
    @Transactional
    public void insert(DeviceAlarmLevel entity) {
        deviceAlarmLevelDao.insert(entity);
    }

    @Override
    @Transactional
    public void update(DeviceAlarmLevel entity) {
        deviceAlarmLevelDao.update(entity);
    }

    @Override
    @Transactional
    public void delete(DeviceAlarmLevel entity) {
        deviceAlarmLevelDao.delete(entity);
    }
}
