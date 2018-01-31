package com.supconit.maintain.services;

import hc.base.domains.Pageable;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.maintain.entities.MaintainItem;

public interface MaintainItemService extends BaseBusinessService<MaintainItem, Long>{
	/**
	 * 分页查询
	 */
	Pageable<MaintainItem> findByCondition(Pageable<MaintainItem> pager,MaintainItem condition);
	/**
	 * 查看设备及设备下保养内容
	 */
	MaintainItem getItem(Long deviceId);
	/**
	 * 删除设备下保养内容
	 */
	void deleteByDeviceId(Long deviceId);
}
