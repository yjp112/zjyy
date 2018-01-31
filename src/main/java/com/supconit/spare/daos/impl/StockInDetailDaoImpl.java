
package com.supconit.spare.daos.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.spare.daos.StockInDetailDao;
import com.supconit.spare.entities.StockInDetail;

import hc.base.domains.Pageable;


@Repository
public class StockInDetailDaoImpl extends AbstractBaseDao<StockInDetail, Long> implements StockInDetailDao {

    private static final String	NAMESPACE	= StockInDetail.class.getName();
	
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
    
	@Override
	public Pageable<StockInDetail> findByPage(Pageable<StockInDetail> pager,
			StockInDetail condition) {
		return findByPager(pager, "findByConditions", "countByConditions", condition);
	}
	@Override
	public int deleteByIds(Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return update("deleteByIds", map);
	}
    
    @Override
	public void insert(List<StockInDetail> list) {
    	try{
		insert("insert",list);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
	}
    
    @Override
	public void deleteByStockInId(Long stockInId) {
		delete("deleteByStockInId",stockInId);
	}

}