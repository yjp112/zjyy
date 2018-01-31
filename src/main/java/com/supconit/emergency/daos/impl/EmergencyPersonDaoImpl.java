package com.supconit.emergency.daos.impl;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.emergency.daos.EmergencyPersonDao;
import com.supconit.emergency.entities.EmergencyPerson;

@Repository
public class EmergencyPersonDaoImpl extends AbstractBaseDao<EmergencyPerson, Long> implements EmergencyPersonDao {
	private static final String	NAMESPACE	= EmergencyPerson.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public List<EmergencyPerson> findGroupPersons(Long groupId) {
		return selectList("findGroupPersons",groupId);
	}

	@Override
	public int deleteByIds(Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return delete("deleteByIds", map);
	}

	@Override
	public List<EmergencyPerson> findTree(List<Long> groupIds) {
		return selectList("findByGroupIds",groupIds);
	}

	@Override
	public Pageable<EmergencyPerson> findByCondition(
			Pagination<EmergencyPerson> page, EmergencyPerson condition) {
		return findByPager(page, "findByCondition", "countByCondition", condition);
	}

	@Override
	public Long countByGroupIdAndPersonId(EmergencyPerson emergencyPerson) {
		return getSqlSession().selectOne(EmergencyPerson.class.getName()+".countByGroupIdAndPersonId", emergencyPerson);
	}

}
