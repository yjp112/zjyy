
package com.supconit.spare.daos;

import com.supconit.common.daos.BaseDao;
import com.supconit.spare.entities.StockIn;

import hc.base.domains.Pageable;



public interface StockInDao extends BaseDao<StockIn, Long>{
	
	 public Pageable<StockIn> findByPage(Pageable<StockIn> pager,StockIn condition);
	   
	public int deleteByIds(Long[] ids);
    
    public  StockIn findById(Long id); 
    public Integer updateSelective(StockIn stockIn);
}
