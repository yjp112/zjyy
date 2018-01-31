
package com.supconit.base.daos;

import java.util.List;

import com.supconit.base.entities.DeviceCategory;

import hc.base.domains.Pageable;
import hc.orm.BasicDao;

public interface DeviceCategoryDao extends BasicDao<DeviceCategory, Long>{
	
	Pageable<DeviceCategory> findByCondition(Pageable<DeviceCategory> pager, DeviceCategory condition);

	int deleteByIds(Long[] ids);
    
    public  DeviceCategory findById(Long id); 
    public List<DeviceCategory> findAll();
	public List<Long> findChildIds(Long id);
	public List<DeviceCategory> findChildIdsEntity(Long id);
	public DeviceCategory getByCode(String code);
	public List<DeviceCategory> findByCategoryCode(String categoryCode);
	public Long findChildrenCount(long id);
	public List<DeviceCategory> findByCodes_g(List<String> lstCodes) ;
	List<DeviceCategory> findByParentId(long parentId);
}
