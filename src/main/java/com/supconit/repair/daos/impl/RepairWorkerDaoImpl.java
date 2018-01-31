package com.supconit.repair.daos.impl;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.repair.daos.RepairWorkerDao;
import com.supconit.repair.entities.RepairWorker;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @文件名: RepairItemDaoImpl
 * @创建日期: 13-7-30
 * @版权: Copyright(c)2013
 * @开发人员:
 * @版本:
 * @描述:
 */
@Repository
public class RepairWorkerDaoImpl extends AbstractBaseDao<RepairWorker, Long> implements RepairWorkerDao {

	private static final String	NAMESPACE	= RepairWorker.class.getName();

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}

    @Override
    public List<RepairWorker> findListByOrderCode(String orderCode) {
        return selectList("findListByOrderCode",orderCode);
    }

    @Override
    public void batchInsert(List<RepairWorker> workerList,String orderCode) {
    	Iterator<RepairWorker> wList = workerList.iterator();  
		while(wList.hasNext()){  
			RepairWorker worker = wList.next();  
		    if(worker.getWorkerMode()==null){  
		    	wList.remove();  
		    }  
		}
		if(workerList!=null && workerList.size()>0){
			Assert.notNull(workerList);
	        Map map = new HashMap();
	        map.put("workerList",workerList);
	        map.put("orderCode",orderCode);
			insert("batch_insert", map);
		}
    }

    @Override
    public void deleteByOrderCode(String orderCode) {
        delete("deleteByOrderCode",orderCode);
    }

}
