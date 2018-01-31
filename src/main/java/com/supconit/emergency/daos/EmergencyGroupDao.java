package com.supconit.emergency.daos;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.List;

import com.supconit.common.daos.BaseDao;
import com.supconit.emergency.entities.EmergencyGroup;

public interface EmergencyGroupDao extends BaseDao<EmergencyGroup, Long>{

	List<EmergencyGroup> findAll();

	List<EmergencyGroup> findByCode(String groupCode);

	List<EmergencyGroup> findSubByRoot(Long id);

	EmergencyGroup getById(Long id);

	int insert(EmergencyGroup entity);

	int update(EmergencyGroup entity);

	List<EmergencyGroup> findTree(Long flag);

	Pageable<EmergencyGroup> findByCondition(Pagination<EmergencyGroup> page,
			EmergencyGroup dutyGroup);

	int deleteByIds(Long[] ids);

	List<EmergencyGroup> findSubById(Long parentId);

	List<EmergencyGroup> findByPid(Long parentId);

}
