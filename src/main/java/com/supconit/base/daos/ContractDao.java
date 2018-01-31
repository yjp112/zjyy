
package com.supconit.base.daos;

import com.supconit.base.entities.Contract;
import com.supconit.common.daos.BaseDao;

import hc.base.domains.Pageable;

public interface ContractDao extends BaseDao<Contract, Long>{
	
	Pageable<Contract> findByCondition(Pageable<Contract> pager, Contract condition);

	int deleteByIds(Long[] ids);
    
    public  Contract findById(Long id); 
}
