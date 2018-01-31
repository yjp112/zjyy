package com.supconit.repair.daos.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.repair.daos.RepairEvtCategoryDao;
import com.supconit.repair.entities.RepairEvtCategory;

import hc.base.domains.Pageable;


@Repository
public class RepairEvtCategoryDaoImpl extends AbstractBaseDao<RepairEvtCategory, Long> implements RepairEvtCategoryDao {

    private static final String	NAMESPACE	= RepairEvtCategory.class.getName();
	
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
    
	@Override
	public Pageable<RepairEvtCategory> findByCondition(Pageable<RepairEvtCategory> pager, RepairEvtCategory condition) {	
		return findByPager(pager, "findByCondition", "countByCondition", condition);
	}
	@Override
	public List<Long> findChildIds(Long id) {
		List<Long> childIds = null;
        if(id!=null){//递归查询子节点
        	childIds=getSqlSession().selectList(NAMESPACE+".findChildIds",id);
          }	
        return  childIds;
	}
	@Override
	public int deleteByIds(Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return update("deleteByIds", map);
	}
    
    @Override
	public RepairEvtCategory findById(Long id) {
		return selectOne("findById",id);
	}
    @Override
	public RepairEvtCategory getByPidAndCategoryName(RepairEvtCategory re) {
		return selectOne("getByPidAndCategoryName",re);
	}
    @Override
	public List<RepairEvtCategory> findAll() {
		return selectList("findAll");
	}
    @Override
	public List<RepairEvtCategory> findByCodes_g(List<String> lstCodes) {
		Map mapCodes = new HashMap();
		mapCodes.put("codes", lstCodes);
		return selectList("findByCodes_g",mapCodes);
	}
    
	//查询某设备类别的子节点数量-删除设备类别验证用
	public Long findChildrenCount(long id) {
		return getSqlSession().selectOne(getNamespace()+".findChildrenCount", id);
	}

	@Override
	public List<RepairEvtCategory> findChildIdsEntity(Long id) {
		List<RepairEvtCategory> childIds = null;
        if(id!=null){//递归查询子节点
        	childIds=getSqlSession().selectList(NAMESPACE+".findChildIdsEntity",id);
          }	
        return childIds;
	}
}