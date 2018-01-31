package com.supconit.emergency.daos.impl;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.emergency.daos.EmergencyFacilityDao;
import com.supconit.emergency.entities.EmergencyFacility;
@Repository
public class EmergencyFacilityDaoImpl extends AbstractBaseDao<EmergencyFacility, Long> implements EmergencyFacilityDao{
	private static final String	NAMESPACE	= EmergencyFacility.class.getName();
	
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public List<EmergencyFacility> findByFacilityCode(String facilityCode) {
		return selectList("findByCode",facilityCode);
	}

	@Override
	public Pageable<EmergencyFacility> findByCondition(
			Pagination<EmergencyFacility> pager,
			EmergencyFacility condition) {
		return findByPager(pager, "findByConditions", "countByConditions", condition);
		
	}

	@Override
	public int deleteByIds(Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return update("deleteByIds", map);
	}

}
