package com.supconit.inspection.daos.impl;

import hc.base.domains.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.inspection.daos.InspectionTaskContentDao;
import com.supconit.inspection.entities.InspectionTaskContent;

@Repository
public class InspectionTaskContentDaoImpl extends AbstractBaseDao<InspectionTaskContent, Long> implements InspectionTaskContentDao{
	private static final String	NAMESPACE = InspectionTaskContent.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	
	@Override
	public List<InspectionTaskContent> selectInspectionTaskContentList(
			String inspectionCode) {
		Map<String, Object> params = new HashMap();
		params.put("inspectionCode", inspectionCode);
		return selectList("selectInspectionTaskContentList", params);
	}

	@Override
	public void deleteByInspectionCode(String inspectionCode) {
		Map<String, Object> params = new HashMap();
		params.put("inspectionCode", inspectionCode);
		delete("deleteByInspectionCode", params);
	}

	@Override
	public void batchInsert(Map<String, Object> param) {
		insert("batchInsert", param);
	}

	@Override
	public Pageable<InspectionTaskContent> findByCondition(Pageable<InspectionTaskContent> pager,
			InspectionTaskContent condition) {
		return findByPager(pager, "findByConditions", "countByConditions", condition);
	}
	
	
}
