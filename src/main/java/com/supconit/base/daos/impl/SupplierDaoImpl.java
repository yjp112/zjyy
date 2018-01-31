
package com.supconit.base.daos.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.base.daos.SupplierDao;
import com.supconit.base.entities.Supplier;
import com.supconit.common.daos.AbstractBaseDao;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;


@Repository
public class SupplierDaoImpl extends AbstractBaseDao<Supplier, Long> implements SupplierDao {

    private static final String	NAMESPACE	= Supplier.class.getName();
	
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
    
	@Override
	public Pageable<Supplier> findByCondition(Pagination<Supplier> pager, Supplier condition) {
		return findByPager(pager,"findByCondition", "countByCondition", condition);
	}

	@Override
	public int deleteByIds(Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return delete("deleteByIds", map);
	}
    
    @Override
	public Supplier findById(Long id) {
		return selectOne("findById",id);
	}
    
    @Override
	public Long countRecordByCode(String code) {
    	Supplier condition=new Supplier();
        condition.setSupplierCode(code);
        return getSqlSession().selectOne(Supplier.class.getName()+".countByCondition", condition);
	}

	@Override
	public List<Supplier> findByCode(String supplierCode) {
		return selectList("findByCode",supplierCode);
	}
}