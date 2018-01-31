package com.supconit.inspection.services;

import hc.base.domains.Pageable;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.inspection.entities.InspectionItem;

public interface InspectionItemService extends BaseBusinessService<InspectionItem, Long>{
	/**
	 * 分页查询
	 */
	Pageable<InspectionItem> findByCondition(Pageable<InspectionItem> pager,InspectionItem condition);
	/**
	 * 查看设备及设备下巡检内容
	 */
	InspectionItem getItem(Long deviceId);
	/**
	 * 删除设备下巡检内容
	 */
	void deleteByDeviceId(Long deviceId);
}
