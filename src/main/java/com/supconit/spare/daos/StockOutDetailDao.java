
package com.supconit.spare.daos;

import java.util.List;

import com.supconit.common.daos.BaseDao;
import com.supconit.spare.entities.StockOutDetail;

import hc.base.domains.Pageable;



public interface StockOutDetailDao extends BaseDao<StockOutDetail, Long>{
	
	public Pageable<StockOutDetail> findByPage(Pageable<StockOutDetail> pager,StockOutDetail condition);
	
	public int deleteByIds(Long[] ids);
    
    public void insert(List<StockOutDetail> list);
    
    public void deleteByStockOutId(Long id);
    
    public void updateBackQty(StockOutDetail condition);
}
