package com.supconit.inspection.daos.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.inspection.daos.InspectionSpareDao;
import com.supconit.inspection.entities.InspectionSpare;

@Repository
public class InspectionSpareDaoImpl extends AbstractBaseDao<InspectionSpare, Long> implements InspectionSpareDao{
	private static final String	NAMESPACE = InspectionSpare.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	
	@Override
	public List<InspectionSpare> selectInspectionSpareList(
			String inspectionCode) {
		Map<String, Object> params = new HashMap();
		params.put("inspectionCode", inspectionCode);
		return selectList("selectInspectionSpareList", params);
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
	
	
}
