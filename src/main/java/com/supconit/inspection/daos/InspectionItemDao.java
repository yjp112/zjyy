package com.supconit.inspection.daos;

import java.util.List;

import hc.base.domains.Pageable;

import com.supconit.common.daos.BaseDao;
import com.supconit.inspection.entities.InspectionItem;

public interface InspectionItemDao extends BaseDao<InspectionItem, Long>{
	/**
	 * 分页查询
	 */
	Pageable<InspectionItem> findByCondition(Pageable<InspectionItem> pager,InspectionItem condition);
	/**
	 * 删除设备下巡检内容
	 */
	void deleteByDeviceId(Long deviceId);
	/**
	 * 查询设备信息
	 */
	List<InspectionItem> getDeviceByDeviceId(Long deviceId);
	/**
	 * 查询设备下巡检内容
	 */
	List<InspectionItem> getItemByDeviceId(Long deviceId);
	/**
	 * 更新已存在的设备时间
	 */
	void updateStartTimeByDeviceId(InspectionItem condition);
	
}
