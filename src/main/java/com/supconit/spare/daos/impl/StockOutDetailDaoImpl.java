
package com.supconit.spare.daos.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.spare.daos.StockOutDetailDao;
import com.supconit.spare.entities.StockOutDetail;

import hc.base.domains.Pageable;


@Repository
public class StockOutDetailDaoImpl extends AbstractBaseDao<StockOutDetail, Long> implements StockOutDetailDao {

    private static final String	NAMESPACE	= StockOutDetail.class.getName();
	
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
    
	@Override
	public Pageable<StockOutDetail> findByPage(Pageable<StockOutDetail> pager,
			StockOutDetail condition) {
		return findByPager(pager, "findByConditions", "countByConditions", condition);
	}
	@Override
	public int deleteByIds(Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return update("deleteByIds", map);
	}
    
    @Override
	public void insert(List<StockOutDetail> list) {
		insert("insert",list);
	}
    
    @Override
	public void deleteByStockOutId(Long stockOutId) {
		delete("deleteByStockOutId",stockOutId);
	}

	@Override
	public void updateBackQty(StockOutDetail condition) {
		update("updateBackQty", condition);
	}

}