package com.supconit.montrol.service.impl;

import hc.base.domains.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.montrol.dao.IAlarmLevelDao;
import com.supconit.montrol.entity.MAlarmLevel;
import com.supconit.montrol.service.IAlarmLevelService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlarmLevelServiceImpl extends hc.orm.AbstractBasicOrmService<MAlarmLevel, Long> implements IAlarmLevelService {
    @Autowired
    private IAlarmLevelDao levelDao;


    @Override
    @Transactional(readOnly = true)
    public Pageable<MAlarmLevel> find(Pageable<MAlarmLevel> pager, MAlarmLevel condition) {
        return levelDao.findByPager(pager, condition);
    }

    @Override
    public List<MAlarmLevel> findList() {
        return levelDao.findList();
    }

    @Override
    public Long getCount(MAlarmLevel condition) {
        return levelDao.getCount(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public MAlarmLevel getById(Long aLong) {
        return levelDao.getById(aLong);
    }

    @Override
    @Transactional
    public void insert(MAlarmLevel entity) {
        levelDao.insert(entity);
    }

    @Override
    @Transactional
    public void update(MAlarmLevel entity) {
        levelDao.update(entity);
    }

    @Override
    @Transactional
    public void delete(MAlarmLevel entity) {
        levelDao.delete(entity);
    }
}
