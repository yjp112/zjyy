package com.supconit.emergency.services;


import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.List;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.emergency.entities.EmergencyGroup;
import com.supconit.emergency.entities.EmergencyPerson;

public interface EmergencyPersonService extends BaseBusinessService<EmergencyPerson, Long> {

	List<EmergencyPerson> findTree(List<EmergencyGroup> emergencyGroup_tree);

	Pageable<EmergencyPerson> findByCondition(Pagination<EmergencyPerson> page,
			EmergencyPerson emergencyPerson, String treeId);

	boolean countByGroupIdAndPersonId(EmergencyPerson emergencyPerson);

	void deleteByIds(Long[] ids);

}
