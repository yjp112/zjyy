
package com.supconit.spare.daos.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.spare.daos.StockOutDao;
import com.supconit.spare.entities.StockOut;

import hc.base.domains.Pageable;



@Repository
public class StockOutDaoImpl extends AbstractBaseDao<StockOut, Long> implements StockOutDao {

    private static final String	NAMESPACE	= StockOut.class.getName();
	
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
    
	@Override
	public Pageable<StockOut> findByPage(Pageable<StockOut> pager,
			StockOut condition) {
		return findByPager(pager, "findByConditions", "countByConditions", condition);
	}
	@Override
	public int deleteByIds(Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return update("deleteByIds", map);
	}
    
    @Override
	public StockOut findById(Long id) {
		return selectOne("findById",id);
	}

    @Override
    public Integer updateSelective(StockOut stockOut) {
        return update("updateSelective", stockOut);
    }

}