
package com.supconit.base.services;

import com.supconit.base.entities.EnumDetail;
import com.supconit.common.services.BaseBusinessService;

import hc.base.domains.Pageable;


public interface EnumDetailService extends BaseBusinessService<EnumDetail, Long> {	

	/**
	 * Query by condition
	 * @param pageNo  page number
	 * @param pageSize  page size
	 * @param condition Query condition
	 * @return
	 */
	Pageable<EnumDetail> findByCondition(Pageable pager, EnumDetail condition);



   /**
	 * delete objects 
	 * @param ids  object ID array	 
	 * @return 
	 */
	void deleteByIds(Long[] ids);

    /**
	 * save
	 * @param enumDetail  object instance 
	 * @return
	 */
	void save(EnumDetail enumDetail);

}

