
package com.supconit.base.services;

import com.supconit.base.entities.Contract;
import com.supconit.common.services.BaseBusinessService;

import hc.base.domains.Pageable;


public interface ContractService extends BaseBusinessService<Contract, Long> {	

	/**
	 * Query by condition
	 * @param pageNo  page number
	 * @param pageSize  page size
	 * @param condition Query condition
	 * @return
	 */
	Pageable<Contract> findByCondition(Pageable<Contract> pager, Contract condition);



   /**
	 * delete objects 
	 * @param ids  object ID array	 
	 * @return 
	 */
	void deleteByIds(Long[] ids);

    /**
	 * save
	 * @param contract  object instance 
	 * @return
	 */
	void save(Contract contract);
	void insert(Contract contract,String[] fileorignal,String[] filename,String[] delfiles,String fileLength);
	void update(Contract contract,String[] fileorignal,String[] filename,String[] delfiles,String fileLength);
}

