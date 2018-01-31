package com.supconit.duty.daos;


import java.util.List;

import com.supconit.common.daos.BaseDao;
import com.supconit.duty.entities.DutyLogDetail;


public interface DutyLogDetailDao extends BaseDao<DutyLogDetail, Long>{
	
		

		void deleteByIds(Long[] ids);

		List<DutyLogDetail> findAll(Long id);
}
