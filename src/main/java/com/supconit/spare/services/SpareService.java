
package com.supconit.spare.services;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.spare.entities.Spare;

import hc.base.domains.Pageable;

import java.util.List;


public interface SpareService extends BaseBusinessService<Spare, Long> {

	/**
	 * Query by condition
	 * @param condition Query condition
	 * @return
	 */

	Pageable<Spare> findByCondition(Pageable<Spare> pager,Spare condition);
	

   /**
	 * delete objects 
	 * @param ids  object ID array	 
	 * @return 
	 */
	void deleteByIds(Long[] ids);
	
	Pageable<Spare> findStockByCondition(Pageable<Spare> pager,Spare condition);

	/** 手机端使用 **/
	List<Spare> selectSpares(Spare spare);
	long countSpares(Spare spare);
}

