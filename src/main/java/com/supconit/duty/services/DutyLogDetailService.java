package com.supconit.duty.services;


import java.util.List;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.duty.entities.DutyLogDetail;

public interface DutyLogDetailService extends BaseBusinessService<DutyLogDetail, Long>{
	

	   /**
		 * delete objects 
		 * @param ids  object ID array	 
		 * @return 
		 */
		void deleteByIds(Long[] ids);

	List<DutyLogDetail> findAll(Long id); 
	
}
