
package com.supconit.base.daos.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.base.daos.ContractDao;
import com.supconit.base.entities.Contract;
import com.supconit.common.daos.AbstractBaseDao;

import hc.base.domains.Pageable;


@Repository
public class ContractDaoImpl extends AbstractBaseDao<Contract, Long> implements ContractDao {

    private static final String	NAMESPACE	= Contract.class.getName();
	
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
    
	@Override
	public Pageable<Contract> findByCondition(Pageable<Contract> pager, Contract condition) {
		return findByPager(pager,"findByCondition", "countByCondition", condition);
	}

	@Override
	public int deleteByIds(Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return update("deleteByIds", map);
	}
    
    @Override
	public Contract findById(Long id) {
		return selectOne("findById",id);
	}
}