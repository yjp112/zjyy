
package com.supconit.spare.daos.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.spare.daos.InventoryDao;
import com.supconit.spare.entities.Inventory;

import hc.base.domains.Pageable;


@Repository
public class InventoryDaoImpl extends AbstractBaseDao<Inventory, Long> implements InventoryDao {

    private static final String	NAMESPACE	= Inventory.class.getName();
	
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
    
	@Override
	public Pageable<Inventory> findByPage(Pageable<Inventory> pager,
			Inventory condition) {
		return findByPager(pager, "findByConditions", "countByConditions", condition);
	}
    @Override
    public List<Inventory> selectInventorys(Inventory condition) {
        
        return selectList("findByConditions", condition);
    }
	@Override
	public int deleteByIds(Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return update("deleteByIds", map);
	}
    
    @Override
	public Inventory findById(Long id) {
		return selectOne("findById",id);
	}

    @Override
    public Integer updateSelective(Inventory inventory) {
        return update("updateSelective", inventory);
    }


}