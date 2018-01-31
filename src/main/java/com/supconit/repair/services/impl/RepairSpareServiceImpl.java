package com.supconit.repair.services.impl;

import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.repair.daos.RepairSpareDao;
import com.supconit.repair.entities.RepairSpare;
import com.supconit.repair.services.RepairSpareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;


@Service
@Transactional
public class RepairSpareServiceImpl extends AbstractBaseBusinessService<RepairSpare, Long> implements RepairSpareService {
    @Autowired
    private RepairSpareDao spareDao;

    @Override
    public List<RepairSpare> findByOrderCode(String orderCode) {
        Assert.notNull(orderCode);
        return spareDao.findListByOrderCode(orderCode);
    }

    @Override
    public void batchAdd(List<RepairSpare> spareList,String orderCode) {
        Assert.notNull(spareList);
        spareDao.batchInsert(spareList,orderCode);
    }

    @Override
    public void deleteByOrderCode(String orderCode) {
        Assert.notNull(orderCode);
        spareDao.deleteByOrderCode(orderCode);
    }

    @Override
    public RepairSpare getById(Long aLong) {
        Assert.notNull(aLong);
        return spareDao.getById(aLong);
    }

    @Override
    public void deleteById(Long aLong) {
        spareDao.deleteById(aLong);
    }

    @Override
    public void insert(RepairSpare repairSpare) {
       spareDao.insert(repairSpare);
    }

    @Override
    public void update(RepairSpare repairSpare) {
        spareDao.update(repairSpare);
    }
}
