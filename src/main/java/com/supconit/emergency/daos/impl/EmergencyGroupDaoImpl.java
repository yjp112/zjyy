package com.supconit.emergency.daos.impl;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.emergency.daos.EmergencyGroupDao;
import com.supconit.emergency.entities.EmergencyGroup;
@Repository
public class EmergencyGroupDaoImpl extends AbstractBaseDao<EmergencyGroup, Long> implements EmergencyGroupDao {

    private static final String	NAMESPACE	= EmergencyGroup.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public List<EmergencyGroup> findAll() {
		return selectList("findByCondition");
	}

	@Override
	public List<EmergencyGroup> findByCode(String groupCode) {
		return selectList("findByCode",groupCode);
	}

	@Override
	public List<EmergencyGroup> findSubByRoot(Long parentId) {
		return selectList("findSubByRoot",parentId);
	}

	@Override
	public List<EmergencyGroup> findTree(Long flag) {
		return selectList("findByConditions",flag);
	}

	@Override
	public Pageable<EmergencyGroup> findByCondition(
			Pagination<EmergencyGroup> page, EmergencyGroup condition) {
		return findByPager(page, "findByCondition", "countByCondition", condition);
	}

	@Override
	public int deleteByIds(Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return delete("deleteByIds", map);
	}

	@Override
	public List<EmergencyGroup> findSubById(Long parentId) {
		return selectList("findSubById",parentId);
	}

	@Override
	public List<EmergencyGroup> findByPid(Long parentId) {
		return selectList("findByPid",parentId);
	}


}
