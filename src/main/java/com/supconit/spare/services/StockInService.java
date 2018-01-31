
package com.supconit.spare.services;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.spare.entities.StockIn;

import hc.base.domains.Pageable;



public interface StockInService extends BaseBusinessService<StockIn, Long> {	
    public static final Integer INTYPE_PANYING=3;//"盘盈入库"
	/**
	 * Query by condition
	 * @param pageNo  page number
	 * @param pageSize  page size
	 * @param condition Query condition
	 * @return
	 */
	Pageable<StockIn> findByCondition(Pageable<StockIn> pager,StockIn condition);
	
	
    /**
	 * findById
	 * @param object instance id
	 * @return object instance
	 */
    StockIn findById(Long id);

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
     *@方法描述:  入库单生效 
     * @param stockInId void
     */
    void takeEffect(Long stockInId);
    
}
