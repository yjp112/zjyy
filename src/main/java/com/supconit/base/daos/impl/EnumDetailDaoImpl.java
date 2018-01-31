
package com.supconit.base.daos.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.base.daos.EnumDetailDao;
import com.supconit.base.entities.EnumDetail;
import com.supconit.common.daos.AbstractBaseDao;

import hc.base.domains.Pageable;


@Repository
public class EnumDetailDaoImpl extends AbstractBaseDao<EnumDetail, Long> implements EnumDetailDao {

    private static final String	NAMESPACE	= EnumDetail.class.getName();
	
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
    
	@Override
	public Pageable<EnumDetail> findByCondition(Pageable pager, EnumDetail condition) {
		return findByPager(pager, "findByCondition", "countByCondition", condition);
	}

	@Override
	public int deleteByIds(Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return update("deleteByIds", map);
	}
    
    @Override
	public EnumDetail findById(Long id) {
		return selectOne("findById",id);
	}

    @Override
    public List<EnumDetail> selectByTypeId(Long typeId) {
        return selectList("selectByTypeId", typeId);
    }

}