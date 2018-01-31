package com.supconit.repair.daos;

import com.supconit.common.daos.BaseDao;
import com.supconit.repair.entities.RepairSpare;


import java.util.List;


public interface RepairSpareDao extends BaseDao<RepairSpare, Long>{

    List<RepairSpare> findListByOrderCode(String orderCode);

    void batchInsert(List<RepairSpare> spareList,String orderCode);

    void deleteByOrderCode(String orderCode);

}
