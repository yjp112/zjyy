package com.supconit.montrol.service.impl;

import com.supconit.montrol.dao.IRealAlarmDao;
import com.supconit.montrol.service.IRealAlarmService;
import hc.base.domains.Pageable;
import hc.safety.manager.SafetyManager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.honeycomb.business.authorization.entities.User;
import com.supconit.montrol.entity.MRealAlarm;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RealAlarmServiceImpl extends hc.orm.AbstractBasicOrmService<MRealAlarm, Long> implements IRealAlarmService {
    @Autowired
    private IRealAlarmDao realAlarmDao;
    @Autowired
    private SafetyManager safetyManager;


    @Override
    @Transactional(readOnly = true)
    public Pageable<MRealAlarm> find(Pageable<MRealAlarm> pager, MRealAlarm condition) {
        return this.realAlarmDao.findByPager(pager, condition);
    }

    private User getCurrentUser() {
        return (User) this.safetyManager.getCurrentUser();
    }

    public MRealAlarm findNewAlarm() {
        return realAlarmDao.findNewAlarm();
    }


    @Override
    @Transactional(readOnly = true)
    public long countRealAlarm() {
        return realAlarmDao.countRealAlarm();

    }

    @Override
    @Transactional(readOnly = true)
    public List<MRealAlarm> findFirstFifthAlarm() {
        return realAlarmDao.findFirstFifthAlarm();
    }

    @Override
    @Transactional
    public void displayAlarm() {
        this.realAlarmDao.displayAlarm();
    }


    @Override
    @Transactional(readOnly = true)
    public MRealAlarm getById(Long aLong) {
        return this.realAlarmDao.getById(aLong);
    }

    @Override
    @Transactional
    public void insert(MRealAlarm entity) {
        this.realAlarmDao.insert(entity);
    }

    @Override
    @Transactional
    public void update(MRealAlarm entity) {
        this.realAlarmDao.update(entity);
    }

    @Override
    @Transactional
    public void delete(MRealAlarm entity) {
        this.realAlarmDao.delete(entity);
    }
}
