
package com.supconit.base.daos;

import java.util.List;

import com.supconit.base.entities.SubDevice;
import com.supconit.common.daos.BaseDao;

import hc.base.domains.Pageable;

public interface SubDeviceDao extends BaseDao<SubDevice, Long>{
	
	public Pageable<SubDevice> findByCondition(Pageable<SubDevice> pager, SubDevice condition);

	public int deleteByIds(Long[] ids);
    
    public void insert(List<SubDevice> list);
    
    public void deleteByDeviceId(Long id);
}
