
package com.supconit.base.daos;

import com.supconit.base.entities.DeviceCategoryProperty;

import hc.base.domains.Pageable;
import hc.orm.BasicDao;


public interface DeviceCategoryPropertyDao extends BasicDao<DeviceCategoryProperty, Long>{
	
	Pageable<DeviceCategoryProperty> findByCondition(Pageable<DeviceCategoryProperty> pager, DeviceCategoryProperty condition);

	int deleteByIds(Long[] ids);
    
    public  DeviceCategoryProperty findById(Long id); 
	public int deleteByCategoryId(Long categoryId);
}
