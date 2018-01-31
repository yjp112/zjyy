package com.supconit.inspection.daos.impl;

import hc.base.domains.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.inspection.daos.InspectionTaskDao;
import com.supconit.inspection.entities.InspectionTask;

@Repository
public class InspectionTaskDaoImpl extends AbstractBaseDao<InspectionTask, Long> implements InspectionTaskDao{
	private static final String	NAMESPACE = InspectionTask.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	@Override
	public void readyForTask() {
		update("readyForTask");
	}
	@Override
	public List<InspectionTask> findReadyTask() {
		return selectList("findReadyTask");
	}
	
	@Override
	public Pageable<InspectionTask> findByCondition(Pageable<InspectionTask> pager,
			InspectionTask condition) {
		return findByPager(pager, "findByConditions", "countByConditions", condition);
	}
	@Override
	public List<InspectionTask> selectInspectionTaskList(InspectionTask condition) {
		Map<String, Object> params = new HashMap();
		params.put("condition", condition);
		return selectList("selectInspectionTaskList", params);
	}
	@Override
	public List<Long> findProcessIds(List<Long> ids) {
		Map<String, Object> params = new HashMap();
		params.put("ids", ids);
		return selectList("findProcessIds", params);
	}
	@Override
	public InspectionTask getBydeviceId(Long deviceId) {
		Map param = new HashMap<String, Long>();
		param.put("deviceId", deviceId);
		param.put("status", 1l);//巡检完成
		return selectOne("getByDeviceId", param);
	}
}
