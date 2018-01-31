
package com.supconit.spare.daos;

import java.util.List;

import com.supconit.common.daos.BaseDao;
import com.supconit.spare.entities.Warehouse;

import hc.base.domains.Pageable;



public interface WarehouseDao extends BaseDao<Warehouse, Long>{
	

	public Pageable<Warehouse> findByPage(Pageable<Warehouse> pager,Warehouse condition);
	
	List<Warehouse> findAll();
	int deleteByIds(Long[] ids);
    
    public  Warehouse findById(Long id);

	Long countRecordByCode(String code);

	List<Warehouse> findByCode(String warehouseCode, String warehouseName);

}
