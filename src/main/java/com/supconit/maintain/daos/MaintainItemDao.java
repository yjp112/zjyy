package com.supconit.maintain.daos;

import java.util.List;

import hc.base.domains.Pageable;

import com.supconit.common.daos.BaseDao;
import com.supconit.maintain.entities.MaintainItem;

public interface MaintainItemDao extends BaseDao<MaintainItem, Long>{
	/**
	 * 分页查询
	 */
	Pageable<MaintainItem> findByCondition(Pageable<MaintainItem> pager,MaintainItem condition);
	/**
	 * 删除设备下保养内容
	 */
	void deleteByDeviceId(Long deviceId);
	/**
	 * 查询设备信息
	 */
	List<MaintainItem> getDeviceByDeviceId(Long deviceId);
	/**
	 * 查询设备下保养内容
	 */
	List<MaintainItem> getItemByDeviceId(Long deviceId);
	/**
	 * 更新已存在的设备时间
	 */
	void updateStartTimeByDeviceId(MaintainItem condition);
	
}
