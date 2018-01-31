
package com.supconit.spare.daos;

import com.supconit.common.daos.BaseDao;
import com.supconit.spare.entities.PriceChange;

import hc.base.domains.Pageable;


public interface PriceChangeDao extends BaseDao<PriceChange, Long>{
	

	public Pageable<PriceChange> findByPage(Pageable<PriceChange> pager,PriceChange condition);

	int deleteByIds(Long[] ids);
    
    public  PriceChange findById(Long id); 
}
