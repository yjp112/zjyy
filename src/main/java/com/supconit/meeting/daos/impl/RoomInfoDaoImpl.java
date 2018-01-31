package com.supconit.meeting.daos.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.meeting.daos.RoomInfoDao;
import com.supconit.meeting.entities.RoomInfo;

import hc.base.domains.Pageable;


@Repository
public class RoomInfoDaoImpl extends AbstractBaseDao<RoomInfo, Long> implements RoomInfoDao {
	private static final String	NAMESPACE = RoomInfo.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	
	@Override
	public Pageable<RoomInfo> findByPage(Pageable<RoomInfo> pager,RoomInfo condition) {
		return findByPager(pager, "findByConditions", "countByConditions", condition);
	}

	@Override
	public List<Long> selectDeptChildren(long useDeptId) {
		Map param = new HashMap(1);
		param.put("useDeptId", useDeptId);
		return selectList("selectDeptChildren", param);
	}
	
	

}
