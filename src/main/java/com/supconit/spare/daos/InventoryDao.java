
package com.supconit.spare.daos;

import java.util.List;

import com.supconit.common.daos.BaseDao;
import com.supconit.spare.entities.Inventory;

import hc.base.domains.Pageable;




public interface InventoryDao extends BaseDao<Inventory, Long>{
	
    public	Pageable<Inventory> findByPage(Pageable<Inventory> pager,Inventory condition);
	public List<Inventory> selectInventorys(Inventory condition);
	public int deleteByIds(Long[] ids);
    
    public  Inventory findById(Long id); 
    public Integer updateSelective(Inventory inventory);
}
