package com.supconit.repair.services;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.repair.entities.RepairWorker;


import java.util.List;

public interface RepairWorkerService extends BaseBusinessService<RepairWorker, Long> {
    
	List<RepairWorker> findByOrderCode(String orderCode);
    void deleteByOrderCode(String orderCode);
    
    void batchAdd(List<RepairWorker> workerList,String orderCode);
}
