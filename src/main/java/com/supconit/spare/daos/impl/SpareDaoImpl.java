
package com.supconit.spare.daos.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.base.entities.Device;
import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.spare.daos.SpareDao;
import com.supconit.spare.entities.Spare;

import hc.base.domains.Pageable;



@Repository
public class SpareDaoImpl extends AbstractBaseDao<Spare, Long> implements SpareDao {

    private static final String	NAMESPACE	= Spare.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
    
	@Override
	public Pageable<Spare> findByPage(Pageable<Spare> pager,
			Spare condition) {
		return findByPager(pager, "findByConditions", "countByConditions", condition);
	}
	@Override
	public int deleteByIds(Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return update("deleteByIds", map);
	}
    
    @Override
	public Spare findById(Long id) {
		return selectOne("findById",id);
	}

    @Override
    public Long countSpareIdUsed(Long spareId) {
        return getSqlSession().selectOne(Spare.class.getName()+".countSpareIdUsed", spareId);
    }

    public Long countRecordBySpareCode(String spareCode){
        Spare condition=new Spare();
        condition.setSpareCode(spareCode);
        return getSqlSession().selectOne(Spare.class.getName()+".countBySpareCode", condition);
    }

    @Override
    public Integer updateSelective(Spare spare) {
		return update("updateSelective", spare);
    }

    @Override
	public Long countByConditions(Device device) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("condition", device);
		return getSqlSession().selectOne(getNamespace() + ".countByConditions", param);
	}

	@Override
	public Pageable<Spare> findStockByCondition(Pageable<Spare> pager,
			Spare condition) {
		return findByPager(pager, "findStockByConditions", "countStockByConditions", condition);
	}

	/** 手机端使用 **/
	@Override
	public List<Spare> selectSpares(Spare spare) {
		return selectList("selectSpares",spare);
	}

	@Override
	public long countSpares(Spare spare) {
		return selectOne("countSpares",spare);
	}
}