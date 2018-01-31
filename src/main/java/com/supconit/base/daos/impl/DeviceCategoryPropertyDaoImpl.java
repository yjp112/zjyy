
package com.supconit.base.daos.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.base.daos.DeviceCategoryPropertyDao;
import com.supconit.base.entities.DeviceCategoryProperty;
import com.supconit.common.daos.AbstractBaseDao;

import hc.base.domains.Pageable;


@Repository
public class DeviceCategoryPropertyDaoImpl extends AbstractBaseDao<DeviceCategoryProperty, Long> implements DeviceCategoryPropertyDao {

    private static final String	NAMESPACE	= DeviceCategoryProperty.class.getName();
	
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
    
	@Override
	public Pageable<DeviceCategoryProperty> findByCondition(Pageable<DeviceCategoryProperty> pager, DeviceCategoryProperty condition) {
		return findByPager(pager,"findByCondition", "countByCondition", condition);
	}

	@Override
	public int deleteByIds(Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return update("deleteByIds", map);
	}
    
    @Override
	public DeviceCategoryProperty findById(Long id) {
		return selectOne("findById",id);
	}
    @Override
	public int deleteByCategoryId(Long categoryId) {
		return update("deleteByCategoryId",categoryId);
	}
    
}