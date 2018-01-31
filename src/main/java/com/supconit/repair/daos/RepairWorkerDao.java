package com.supconit.repair.daos;

import com.supconit.common.daos.BaseDao;
import com.supconit.repair.entities.RepairWorker;
import java.util.List;


public interface RepairWorkerDao extends BaseDao<RepairWorker, Long> {
	
	List<RepairWorker> findListByOrderCode(String orderCode);

    void batchInsert(List<RepairWorker> workerList,String orderCode);

    void deleteByOrderCode(String orderCode);

}
