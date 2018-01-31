
package com.supconit.base.daos;

import java.util.List;
import java.util.Map;

import com.supconit.base.entities.DeviceProperty;
import com.supconit.common.daos.BaseDao;

import hc.base.domains.Pageable;

public interface DevicePropertyDao extends BaseDao<DeviceProperty, Long>{
	
	Pageable<DeviceProperty> findByCondition(Pageable<DeviceProperty> pager, DeviceProperty condition);
	Long findByCode1(DeviceProperty condition);
    Long findByName1(DeviceProperty condition);
    int deleteByIds(Long[] ids);
    
    public  DeviceProperty findById(Long id); 
	/*public List<DeviceProperty> findAll();
	public List<DeviceProperty> findByCategoryId(Long id);*/
	public List<DeviceProperty> findList(String sql,Map map);
	public Long findUseCount(long id) ;
}
