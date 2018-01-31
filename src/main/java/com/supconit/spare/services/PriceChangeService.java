
package com.supconit.spare.services;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.spare.entities.PriceChange;

import hc.base.domains.Pageable;



public interface PriceChangeService extends BaseBusinessService<PriceChange, Long> {	

    public static final Integer CHANGE_TYPE_FIXTIME=1;//定时调价
    public static final Integer CHANGE_TYPE_IMMEDIATELY=2;//即时调价
	/**
	 * Query by condition
	 * @param pageNo  page number
	 * @param pageSize  page size
	 * @param condition Query condition
	 * @return
	 */

	Pageable<PriceChange> findByCondition(Pageable<PriceChange> pager,PriceChange condition);
	

   /**
	 * delete objects 
	 * @param ids  object ID array	 
	 * @return 
	 */
	void deleteByIds(Long[] ids);
    /** 
     *@方法名称:takeEffect
     *@作    者:丁阳光
     *@创建日期:2013年7月19日
     *@方法描述:  调价单生效
     * @param Id void
     */
    void takeEffect(Long Id);
}

