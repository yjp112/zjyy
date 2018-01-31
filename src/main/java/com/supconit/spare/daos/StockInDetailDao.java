
package com.supconit.spare.daos;

import java.util.List;

import com.supconit.common.daos.BaseDao;
import com.supconit.spare.entities.StockInDetail;

import hc.base.domains.Pageable;



public interface StockInDetailDao extends BaseDao<StockInDetail, Long>{
	
	public Pageable<StockInDetail> findByPage(Pageable<StockInDetail> pager,StockInDetail condition);
	
	public int deleteByIds(Long[] ids);
    
    public void insert(List<StockInDetail> list);
    
    public void deleteByStockInId(Long id);
}
