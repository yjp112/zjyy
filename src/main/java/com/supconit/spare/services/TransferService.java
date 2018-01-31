
package com.supconit.spare.services;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.spare.entities.Transfer;

import hc.base.domains.Pageable;



public interface TransferService extends BaseBusinessService<Transfer, Long> {	

	/**
	 * Query by condition
	 * @param pageNo  page number
	 * @param pageSize  page size
	 * @param condition Query condition
	 * @return
	 */
	Pageable<Transfer> findByCondition(Pageable<Transfer> pager,Transfer condition);
	
	
    /**
	 * findById 包含明细信息
	 * @param object instance id
	 * @return object instance
	 */
    Transfer findById(Long id);
    
    /** 
     *@方法名称:findByTransferCode
     *@作    者:丁阳光
     *@创建日期:2013年7月11日
     *@方法描述:  包含明细信息
     * @param transferCode
     * @return Transfer
     */
    Transfer findByTransferCode(String transferCode);

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
	 *@方法描述:   调拨单生效
	 * @param transferId void
	 */
	void takeEffect(Long transferId);
}
