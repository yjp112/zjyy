
package com.supconit.spare.daos.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.spare.daos.StockInDao;
import com.supconit.spare.entities.StockIn;

import hc.base.domains.Pageable;



@Repository
public class StockInDaoImpl extends AbstractBaseDao<StockIn, Long> implements StockInDao {

    private static final String	NAMESPACE	= StockIn.class.getName();
	
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
    
	@Override
	public Pageable<StockIn> findByPage(Pageable<StockIn> pager,
			StockIn condition) {
		return findByPager(pager, "findByConditions", "countByConditions", condition);
	}
	@Override
	public int deleteByIds(Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return update("deleteByIds", map);
	}
    
    @Override
	public StockIn findById(Long id) {
		return selectOne("findById",id);
	}

    @Override
    public Integer updateSelective(StockIn stockIn) {
        return update("updateSelective", stockIn);
    }

}