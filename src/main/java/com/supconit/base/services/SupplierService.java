
package com.supconit.base.services;

import com.supconit.base.entities.Supplier;
import com.supconit.common.services.BaseBusinessService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;


public interface SupplierService extends BaseBusinessService<Supplier, Long> {	

	/**
	 * Query by condition
	 * @param pageNo  page number
	 * @param pageSize  page size
	 * @param condition Query condition
	 * @return
	 */
	Pageable<Supplier> findByCondition(Pagination<Supplier> pager, Supplier condition);



   /**
	 * delete objects 
	 * @param ids  object ID array	 
	 * @return 
	 */
	void deleteByIds(Long[] ids);

    /**
	 * save
	 * @param supplier  object instance 
	 * @return
	 */
	void save(Supplier supplier);

}

