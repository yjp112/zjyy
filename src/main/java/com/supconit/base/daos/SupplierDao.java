
package com.supconit.base.daos;

import java.util.List;

import com.supconit.base.entities.Supplier;
import com.supconit.common.daos.BaseDao;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

public interface SupplierDao extends BaseDao<Supplier, Long>{
	
	Pageable<Supplier> findByCondition(Pagination<Supplier> pager, Supplier condition);

	int deleteByIds(Long[] ids);
    
    public  Supplier findById(Long id); 
    
    public Long countRecordByCode(String code);

	List<Supplier> findByCode(String supplierCode);
}
