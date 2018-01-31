package com.supconit.maintain.daos.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.maintain.daos.MaintainSpareDao;
import com.supconit.maintain.entities.MaintainSpare;

@Repository
public class MaintainSpareDaoImpl extends AbstractBaseDao<MaintainSpare, Long> implements MaintainSpareDao{
	private static final String	NAMESPACE = MaintainSpare.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	
	@Override
	public List<MaintainSpare> selectMaintainSpareList(
			String maintainCode) {
		Map<String, Object> params = new HashMap();
		params.put("maintainCode", maintainCode);
		return selectList("selectMaintainSpareList", params);
	}

	@Override
	public void deleteByMaintainCode(String maintainCode) {
		Map<String, Object> params = new HashMap();
		params.put("maintainCode", maintainCode);
		delete("deleteByMaintainCode", params);
	}

	@Override
	public void batchInsert(Map<String, Object> param) {
		insert("batchInsert", param);
	}
	
	
}
