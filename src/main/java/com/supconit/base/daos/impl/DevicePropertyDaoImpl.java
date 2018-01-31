
package com.supconit.base.daos.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.base.daos.DevicePropertyDao;
import com.supconit.base.entities.DeviceProperty;
import com.supconit.common.daos.AbstractBaseDao;

import hc.base.domains.Pageable;


@Repository
public class DevicePropertyDaoImpl extends AbstractBaseDao<DeviceProperty, Long> implements DevicePropertyDao {

    private static final String	NAMESPACE	= DeviceProperty.class.getName();
	
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
    
	@Override
	public Pageable<DeviceProperty> findByCondition(Pageable<DeviceProperty> pager, DeviceProperty condition) {
		return findByPager(pager,"findByCondition", "countByCondition", condition);
	}

	@Override
	public int deleteByIds(Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return update("deleteByIds", map);
	}
    
    @Override
	public DeviceProperty findById(Long id) {
		return selectOne("findById",id);
	}
    /*@Override
	public List<DeviceProperty> findAll() {
		return selectList("findAll");
	}
    @Override
	public List<DeviceProperty> findByCategoryId(Long id) {
		return selectList("findByCategoryId",id);
	} */
    @Override
	public List<DeviceProperty> findList(String sql,Map map) {
    	return selectList(sql,map);
    	/*if(map == null){
    		return selectList(sql);
    	}else{
    		return selectList(sql,map);
    	}*/
	} 
    
	//查询属性是否在设备类别中已被使用-删除设备属性验证用
	public Long findUseCount(long id) {
		return getSqlSession().selectOne(getNamespace()+".findUseCount", id);
	}

	@Override
	public Long findByCode1(DeviceProperty condition) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne(getNamespace()+".findByCode1", condition);
	}   
	@Override
	public Long findByName1(DeviceProperty condition) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne(getNamespace()+".findByName1", condition);
	}   
}