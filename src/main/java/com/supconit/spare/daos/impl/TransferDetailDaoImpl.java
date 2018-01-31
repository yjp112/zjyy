
package com.supconit.spare.daos.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.spare.daos.TransferDetailDao;
import com.supconit.spare.entities.TransferDetail;

import hc.base.domains.Pageable;


@Repository
public class TransferDetailDaoImpl extends AbstractBaseDao<TransferDetail, Long> implements TransferDetailDao {

    private static final String	NAMESPACE	= TransferDetail.class.getName();
	
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
    
	
	@Override
	public Pageable<TransferDetail> findByPage(Pageable<TransferDetail> pager,
			TransferDetail condition) {
		return findByPager(pager, "findByConditions", "countByConditions", condition);
	}
	@Override
	public int deleteByIds(Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return update("deleteByIds", map);
	}
    
    @Override
	public void insert(List<TransferDetail> list) {
		insert("insert",list);
	}
    
    @Override
	public void deleteByTransferId(Long transferId) {
		delete("deleteByTransferId",transferId);
	}

}