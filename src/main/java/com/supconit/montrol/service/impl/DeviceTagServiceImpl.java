package com.supconit.montrol.service.impl;

import com.supconit.montrol.dao.DeviceTagDao;
import com.supconit.montrol.entity.DeviceTag;
import com.supconit.montrol.service.DeviceTagService;
import hc.base.domains.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeviceTagServiceImpl extends hc.orm.AbstractBasicOrmService<DeviceTag, Long> implements DeviceTagService {
    @Autowired
    private DeviceTagDao deviceTagDao;


    @Override
    @Transactional(readOnly = true)
    public Pageable<DeviceTag> find(Pageable<DeviceTag> pager, DeviceTag condition) {
        return deviceTagDao.findByPager(pager, condition);
    }

    @Override
    public DeviceTag findByName(DeviceTag condition) {
        return deviceTagDao.findByName(condition);
    }


    @Override
    @Transactional(readOnly = true)
    public DeviceTag getById(Long aLong) {
        return deviceTagDao.getById(aLong);
    }

    @Override
    @Transactional
    public void insert(DeviceTag entity) {
        deviceTagDao.insert(entity);
    }

    @Override
    @Transactional
    public void update(DeviceTag entity) {
        deviceTagDao.update(entity);
    }

    @Override
    @Transactional
    public void delete(DeviceTag entity) {
        deviceTagDao.delete(entity);
    }
}
