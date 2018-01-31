package com.supconit.repair.services.impl;

import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.repair.daos.RepairWorkerDao;
import com.supconit.repair.entities.RepairWorker;
import com.supconit.repair.services.RepairWorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
@Transactional
public class RepairWorkerServiceImpl extends AbstractBaseBusinessService<RepairWorker, Long> implements RepairWorkerService {
    @Autowired
    private RepairWorkerDao itemDao;
    
    @Override
    public List<RepairWorker> findByOrderCode(String orderCode) {
        Assert.notNull(orderCode);
        return itemDao.findListByOrderCode(orderCode);
    }

    @Override
    public void deleteByOrderCode(String orderCode) {
        itemDao.deleteByOrderCode(orderCode);
    }
    
    @Override
    public void batchAdd(List<RepairWorker> workerList,String orderCode) {
        Assert.notNull(workerList);
        itemDao.batchInsert(workerList,orderCode);
    }
    
    @Override
    public RepairWorker getById(Long aLong) {
        return itemDao.getById(aLong);
    }

    @Override
    public void deleteById(Long aLong) {
        itemDao.deleteById(aLong);
    }

    @Override
    public void insert(RepairWorker repairItem) {
        itemDao.insert(repairItem);
    }

    @Override
    public void update(RepairWorker repairItem) {
       itemDao.update(repairItem);
    }
}
