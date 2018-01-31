
package com.supconit.spare.daos.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.spare.daos.InventoryDetailDao;
import com.supconit.spare.entities.InventoryDetail;

import hc.base.domains.Pageable;


@Repository
public class InventoryDetailDaoImpl extends AbstractBaseDao<InventoryDetail, Long> implements InventoryDetailDao {

    private static final String	NAMESPACE	= InventoryDetail.class.getName();
	
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	@Override
	public Pageable<InventoryDetail> findByPage(Pageable<InventoryDetail> pager,
			InventoryDetail condition) {
		return findByPager(pager, "findByConditions", "countByConditions", condition);
	}

	@Override
	public int deleteByIds(Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return update("deleteByIds", map);
	}
    
    @Override
	public void insert(List<InventoryDetail> list) {
		insert("insert",list);
	}
    
    @Override
	public void deleteByInventoryId(Long inventoryId) {
		delete("deleteByInventoryId",inventoryId);
	}

}