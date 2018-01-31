package com.supconit.maintain.daos.impl;

import hc.base.domains.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.maintain.daos.MaintainTaskDao;
import com.supconit.maintain.entities.MaintainTask;

@Repository
public class MaintainTaskDaoImpl extends AbstractBaseDao<MaintainTask, Long> implements MaintainTaskDao{
	private static final String	NAMESPACE = MaintainTask.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	@Override
	public void readyForTask() {
		update("readyForTask");
	}
	@Override
	public List<MaintainTask> findReadyTask() {
		return selectList("findReadyTask");
	}
	@Override
	public Pageable<MaintainTask> findByCondition(Pageable<MaintainTask> pager,
			MaintainTask condition) {
		return findByPager(pager, "findByConditions", "countByConditions", condition);
	}
	@Override
	public List<MaintainTask> selectMaintainTaskList(MaintainTask condition) {
		Map<String, Object> params = new HashMap();
		params.put("condition", condition);
		return selectList("selectMaintainTaskList", params);
	}
	@Override
	public List<Long> findProcessIds(List<Long> ids) {
		Map<String, Object> params = new HashMap();
		params.put("ids", ids);
		return selectList("findProcessIds", params);
	}
	@Override
	public MaintainTask getBydeviceId(Long deviceId) {
		Map param = new HashMap<String, Long>();
		param.put("deviceId", deviceId);
		param.put("status", 1l);//保养完成
		return selectOne("getByDeviceId", param);
	}
	
}
