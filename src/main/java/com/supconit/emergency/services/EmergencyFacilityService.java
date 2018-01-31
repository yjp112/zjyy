package com.supconit.emergency.services;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.emergency.entities.EmergencyFacility;

public interface EmergencyFacilityService extends BaseBusinessService<EmergencyFacility, Long>{

	Pageable<EmergencyFacility> findByCondition(Pagination<EmergencyFacility> pager,
			EmergencyFacility emergencyFacility);

	void deleteByIds(Long[] ids);

}
