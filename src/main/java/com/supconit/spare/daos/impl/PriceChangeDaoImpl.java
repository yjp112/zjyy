
package com.supconit.spare.daos.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.spare.daos.PriceChangeDao;
import com.supconit.spare.entities.PriceChange;

import hc.base.domains.Pageable;


@Repository
public class PriceChangeDaoImpl extends AbstractBaseDao<PriceChange, Long> implements PriceChangeDao {

    private static final String	NAMESPACE	= PriceChange.class.getName();
	
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
    
	@Override
	public Pageable<PriceChange> findByPage(Pageable<PriceChange> pager,
			PriceChange condition) {
		return findByPager(pager, "findByConditions", "countByConditions", condition);
	}
	@Override
	public int deleteByIds(Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return update("deleteByIds", map);
	}
    
    @Override
	public PriceChange findById(Long id) {
		return selectOne("findById",id);
	}
}