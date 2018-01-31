
package com.supconit.spare.daos;

import java.util.List;

import com.supconit.common.daos.BaseDao;
import com.supconit.spare.entities.InventoryDetail;

import hc.base.domains.Pageable;

public interface InventoryDetailDao extends BaseDao<InventoryDetail, Long>{
	
	public Pageable<InventoryDetail> findByPage(Pageable<InventoryDetail> pager,InventoryDetail condition);
	public int deleteByIds(Long[] ids);
    
    public void insert(List<InventoryDetail> list);
    
    public void deleteByInventoryId(Long id);
}
