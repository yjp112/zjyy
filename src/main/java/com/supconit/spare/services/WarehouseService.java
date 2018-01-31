package com.supconit.spare.services;

import java.util.List;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.spare.entities.Warehouse;

import hc.base.domains.Pageable;



public interface WarehouseService extends BaseBusinessService<Warehouse, Long> {

    /**
     * Query by condition
     * 
     * @param pageNo
     *            page number
     * @param pageSize
     *            page size
     * @param condition
     *            Query condition
     * @return
     */
    Pageable<Warehouse> findByCondition(Pageable<Warehouse> pager,Warehouse condition);
	
    List<Warehouse> finaAll();

    /**
     * delete objects
     * 
     * @param ids
     *            object ID array
     * @return
     */
    void deleteByIds(Long[] ids);

	List<Warehouse> findByCode(String warehouseCode, String warehouseName);

}
