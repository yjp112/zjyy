package com.supconit.meeting.daos;

import java.util.List;

import com.supconit.common.daos.BaseDao;
import com.supconit.meeting.entities.RoomInfo;

import hc.base.domains.Pageable;


public interface RoomInfoDao extends BaseDao<RoomInfo, Long>{
	/**
	 * 分页查询
	 */
	Pageable<RoomInfo> findByPage(Pageable<RoomInfo> pager,RoomInfo condition);
	/**
	 * 查询部门下的子部门
	 */
	List<Long> selectDeptChildren(long useDeptId);
}
