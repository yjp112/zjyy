
package com.supconit.spare.daos;

import java.util.List;

import com.supconit.common.daos.BaseDao;
import com.supconit.spare.entities.Transfer;

import hc.base.domains.Pageable;



public interface TransferDao extends BaseDao<Transfer, Long>{
	
	public Pageable<Transfer> findByPage(Pageable<Transfer> pager,Transfer condition);
	
	public List<Transfer> selectTransfers(Transfer condition);

	public int deleteByIds(Long[] ids);
    
    public  Transfer findById(Long id); 
    public Integer updateSelective(Transfer transfer);
}
