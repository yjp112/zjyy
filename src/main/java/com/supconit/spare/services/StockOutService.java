
package com.supconit.spare.services;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.spare.entities.StockOut;

import hc.base.domains.Pageable;



public interface StockOutService extends BaseBusinessService<StockOut, Long> {	
    public static final Integer OUTTYPE_PANKUI=2;// "盘亏出库"
	/**
	 * Query by condition
	 * @param pageNo  page number
	 * @param pageSize  page size
	 * @param condition Query condition
	 * @return
	 */
	Pageable<StockOut> findByCondition(Pageable<StockOut> pager,StockOut condition);
	
    /**
	 * findById
	 * @param object instance id
	 * @return object instance
	 */
    StockOut findById(Long id);

   /**
	 * delete objects 
	 * @param ids  object ID array	 
	 * @return 
	 */
	void deleteByIds(Long[] ids);
    /** 
     *@方法名称:takeEffect
     *@作    者:丁阳光
     *@创建日期:2013年7月17日
     *@方法描述: 出库单生效  
     * @param stockOutId void
     */
    void takeEffect(Long stockOutId);
}
