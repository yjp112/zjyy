package com.supconit.base.daos;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.List;

import com.supconit.base.entities.DeviceSpareInDetail;
import com.supconit.common.daos.BaseDao;

public interface DeviceSpareInDetailDao extends BaseDao<DeviceSpareInDetail, Long>{
	Pageable<DeviceSpareInDetail> findByCondition(Pagination<DeviceSpareInDetail> pager,DeviceSpareInDetail condition);
	public DeviceSpareInDetail findById(Long id);
	public DeviceSpareInDetail findTopOne();
	public List<DeviceSpareInDetail> findListBySpareId(Long spareId);
	int deleteByIds(Long[] ids);
}
