
package com.supconit.spare.daos;

import java.util.List;

import com.supconit.common.daos.BaseDao;
import com.supconit.spare.entities.TransferDetail;

import hc.base.domains.Pageable;



public interface TransferDetailDao extends BaseDao<TransferDetail, Long>{
	
	public Pageable<TransferDetail> findByPage(Pageable<TransferDetail> pager,TransferDetail condition);
	
	public int deleteByIds(Long[] ids);
    
    public void insert(List<TransferDetail> list);
    
    public void deleteByTransferId(Long id);
}
