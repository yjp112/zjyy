package com.supconit.emergency.daos;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.List;

import com.supconit.common.daos.BaseDao;
import com.supconit.emergency.entities.EmergencyPerson;

public interface EmergencyPersonDao extends BaseDao<EmergencyPerson, Long>{

	List<EmergencyPerson> findGroupPersons(Long id);

	int deleteByIds(Long[] ids);

	List<EmergencyPerson> findTree(List<Long> groupIds);

	Pageable<EmergencyPerson> findByCondition(Pagination<EmergencyPerson> page,
			EmergencyPerson condition);

	Long countByGroupIdAndPersonId(EmergencyPerson emergencyPerson);

}
