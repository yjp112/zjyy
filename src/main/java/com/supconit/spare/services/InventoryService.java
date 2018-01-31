
package com.supconit.spare.services;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.spare.entities.Inventory;

import hc.base.domains.Pageable;



public interface InventoryService extends BaseBusinessService<Inventory, Long> {	

	/**
	 * Query by condition
	 * @param pageNo  page number
	 * @param pageSize  page size
	 * @param condition Query condition
	 * @return
	 */
	Pageable<Inventory> findByCondition(Pageable<Inventory> pager,Inventory condition);
	
	
    /**包含明细信息
	 * findById
	 * @param object instance id
	 * @return object instance
	 */
    Inventory findById(Long id);
    /** 
     *@方法名称:findByInventoryCode
     *@作    者:丁阳光
     *@创建日期:2013年7月11日
     *@方法描述: 包含明细信息 
     * @param inventoryCode
     * @return Inventory
     */
    Inventory findByInventoryCode(String inventoryCode);
   /**
	 * delete objects 
	 * @param ids  object ID array	 
	 * @return 
	 */
	void deleteByIds(Long[] ids);

	/** 
	 *@方法名称:takeEffect
	 *@作    者:丁阳光
	 *@创建日期:2013年7月11日
	 *@方法描述: 盘点单生效，盘盈入库，盘亏出库 
	 * @param inventoryId void
	 */
	void takeEffect(Long inventoryId);
}
