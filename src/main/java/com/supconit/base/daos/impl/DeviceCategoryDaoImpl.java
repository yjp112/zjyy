
package com.supconit.base.daos.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.base.daos.DeviceCategoryDao;
import com.supconit.base.entities.DeviceCategory;
import com.supconit.common.daos.AbstractBaseDao;

import hc.base.domains.Pageable;


@Repository
public class DeviceCategoryDaoImpl extends AbstractBaseDao<DeviceCategory, Long> implements DeviceCategoryDao {

    private static final String	NAMESPACE	= DeviceCategory.class.getName();
	
    
	@Override
	public Pageable<DeviceCategory> findByCondition(Pageable<DeviceCategory> pager, DeviceCategory condition) {
		return findByPager(pager,"findByCondition", "countByCondition", condition);
	}
	@Override
	public List<Long> findChildIds(Long id) {
		List<Long> childIds = null;
        if(id!=null){//递归查询子节点
            childIds=selectList("findChildIds",id);
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
	public DeviceCategory findById(Long id) {
		return selectOne("findById",id);
	}
    @Override
	public DeviceCategory getByCode(String code) {
    	DeviceCategory dc = new DeviceCategory();
    	dc.setCategoryCode(code);
		return selectOne("getByCode",dc);
	}
    @Override
	public List<DeviceCategory> findAll() {
		return selectList("findAll");
	}
    @Override
	public List<DeviceCategory> findByCodes_g(List<String> lstCodes) {
		Map mapCodes = new HashMap();
		mapCodes.put("codes", lstCodes);
		return selectList("findByCodes_g",mapCodes);
	}

	@Override
	public List<DeviceCategory> findByParentId(long parentId) {
		return selectList("findByParentId",parentId);
	}

	//查询某设备类别的子节点数量-删除设备类别验证用
	public Long findChildrenCount(long id) {
		return getSqlSession().selectOne(getNamespace()+".findChildrenCount", id);
	}
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	@Override
	public List<DeviceCategory> findChildIdsEntity(Long id) {
		List<DeviceCategory> childIds = null;
        if(id!=null){//递归查询子节点
            childIds=selectList("findChildIdsEntity",id);
          }	
        return  childIds;
	}
	@Override
	public List<DeviceCategory> findByCategoryCode(String categoryCode) {
		return selectList("findByCategoryCode",categoryCode);
	}
}