package com.supconit.emergency.daos;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.List;

import com.supconit.common.daos.BaseDao;
import com.supconit.emergency.entities.EmergencyFacility;

public interface EmergencyFacilityDao extends BaseDao<EmergencyFacility, Long>{

	List<EmergencyFacility> findByFacilityCode(String facilityCode);

	Pageable<EmergencyFacility> findByCondition(
			Pagination<EmergencyFacility> pager,
			EmergencyFacility emergencyFacility);

	int deleteByIds(Long[] ids);

}
