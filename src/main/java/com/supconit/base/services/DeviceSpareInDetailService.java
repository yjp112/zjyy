package com.supconit.base.services;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.List;

import com.supconit.base.entities.DeviceSpareInDetail;
import com.supconit.common.services.BaseBusinessService;

public interface DeviceSpareInDetailService extends BaseBusinessService<DeviceSpareInDetail, Long>{
	Pageable<DeviceSpareInDetail> findByCondition(Pagination<DeviceSpareInDetail> pager,DeviceSpareInDetail condition);
	public DeviceSpareInDetail findById(Long id);
	public DeviceSpareInDetail findTopOne();
	public void deleteByIds(Long[] ids);
	public void updatelst(List<DeviceSpareInDetail> ddlst);
	public List<DeviceSpareInDetail> findListBySpareId(Long spareId);
}
