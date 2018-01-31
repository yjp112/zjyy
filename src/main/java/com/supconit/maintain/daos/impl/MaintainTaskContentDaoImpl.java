package com.supconit.maintain.daos.impl;

import hc.base.domains.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.maintain.daos.MaintainTaskContentDao;
import com.supconit.maintain.entities.MaintainTaskContent;

@Repository
public class MaintainTaskContentDaoImpl extends AbstractBaseDao<MaintainTaskContent, Long> implements MaintainTaskContentDao{
	private static final String	NAMESPACE = MaintainTaskContent.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	
	@Override
	public List<MaintainTaskContent> selectMaintainTaskContentList(
			String maintainCode) {
		Map<String, Object> params = new HashMap();
		params.put("maintainCode", maintainCode);
		return selectList("selectMaintainTaskContentList", params);
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

	@Override
	public Pageable<MaintainTaskContent> findByCondition(
			Pageable<MaintainTaskContent> pager, MaintainTaskContent condition) {
		return findByPager(pager, "findByConditions", "countByConditions", condition);
	}
	
	
}
