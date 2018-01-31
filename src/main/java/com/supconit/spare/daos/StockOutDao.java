
package com.supconit.spare.daos;

import com.supconit.common.daos.BaseDao;
import com.supconit.spare.entities.StockOut;

import hc.base.domains.Pageable;


public interface StockOutDao extends BaseDao<StockOut, Long>{
	
	public Pageable<StockOut> findByPage(Pageable<StockOut> pager,StockOut condition);
	
	public int deleteByIds(Long[] ids);
    
    public  StockOut findById(Long id); 
    public Integer updateSelective(StockOut stockOut);
}
