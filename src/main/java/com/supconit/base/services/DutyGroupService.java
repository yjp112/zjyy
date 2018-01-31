
package com.supconit.base.services;

import java.util.List;

import com.supconit.base.entities.DutyGroup;
import com.supconit.common.services.BaseBusinessService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;


public interface DutyGroupService extends BaseBusinessService<DutyGroup, Long> {	

	/**
	 * Query by condition
	 * @param pageNo  page number
	 * @param pageSize  page size
	 * @param condition Query condition
	 * @return
	 */
	Pageable<DutyGroup> findByCondition(Pagination<DutyGroup> pager, DutyGroup condition);



   /**
	 * delete objects 
	 * @param ids  object ID array	 
	 * @return 
	 */
	void deleteByIds(Long[] ids);

    /**
	 * save
	 * @param dutyGroup  object instance 
	 * @return
	 */
	void save(DutyGroup dutyGroup);



	List<DutyGroup> findTree();



	List<DutyGroup> findGroupCategory(Long l);



	List<DutyGroup> findSubById(Long id);

    List<DutyGroup> findByGroup();

}

