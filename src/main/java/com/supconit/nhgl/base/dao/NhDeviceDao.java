package com.supconit.nhgl.base.dao;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import com.supconit.common.daos.BaseDao;
import com.supconit.nhgl.base.entities.NhDevice;


public interface NhDeviceDao extends BaseDao<NhDevice, Long>{ 
	public Pageable<NhDevice> findByCondition(Pagination<NhDevice> pager, NhDevice condition);
}
