package com.supconit.emergency.services;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.List;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.emergency.entities.EmergencyGroup;



public interface EmergencyGroupService extends BaseBusinessService<EmergencyGroup, Long> {

	List<EmergencyGroup> findTree(Long flag);

	Pageable<EmergencyGroup> findByCondition(Pagination<EmergencyGroup> page,
			EmergencyGroup dutyGroup);

	void deleteByIds(Long[] ids);


	List<EmergencyGroup> findSubById(Long parentId);

}
