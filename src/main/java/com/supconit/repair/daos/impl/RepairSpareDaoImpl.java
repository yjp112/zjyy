package com.supconit.repair.daos.impl;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.repair.daos.RepairSpareDao;
import com.supconit.repair.entities.RepairSpare;
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
public class RepairSpareDaoImpl extends AbstractBaseDao<RepairSpare, Long> implements RepairSpareDao {

    private static final String NAMESPACE = RepairSpare.class.getName();
    @Override
    protected String getNamespace() {
        return NAMESPACE;
    }

    @Override
    public List<RepairSpare> findListByOrderCode(String orderCode) {
        return selectList("findListByOrderCode",orderCode);
    }

    @Override
    public void batchInsert(List<RepairSpare> itemList,String orderCode) {
    	Iterator<RepairSpare> sList = itemList.iterator();  
		while(sList.hasNext()){  
			RepairSpare spare = sList.next();  
		    if(spare.getSpareType()==null){  
		    	sList.remove();  
		    }  
		}
		if(itemList!=null && itemList.size()>0){
			Assert.notNull(itemList);
	        Map map = new HashMap();
	        map.put("spareList",itemList);
	        map.put("orderCode",orderCode);
			insert("batch_insert", map);
		}
    }

    @Override
    public void deleteByOrderCode(String orderCode) {
        delete("deleteByOrderCode",orderCode);
    }

}
