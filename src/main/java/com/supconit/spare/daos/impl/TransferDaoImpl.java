
package com.supconit.spare.daos.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.spare.daos.TransferDao;
import com.supconit.spare.entities.Transfer;

import hc.base.domains.Pageable;



@Repository
public class TransferDaoImpl extends AbstractBaseDao<Transfer, Long> implements TransferDao {

    private static final String	NAMESPACE	= Transfer.class.getName();
	
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
    
	@Override
	public Pageable<Transfer> findByPage(Pageable<Transfer> pager,
			Transfer condition) {
		return findByPager(pager, "findByConditions", "countByConditions", condition);
	}
    @Override
    public List<Transfer> selectTransfers(Transfer condition) {
        return selectList("findByConditions",condition);
    }
	@Override
	public int deleteByIds(Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return update("deleteByIds", map);
	}
    
    @Override
	public Transfer findById(Long id) {
		return selectOne("findById",id);
	}

    @Override
    public Integer updateSelective(Transfer transfer) {
        return update("updateSelective", transfer);
    }


}