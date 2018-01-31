
package com.supconit.base.daos;

import java.util.List;

import com.supconit.base.entities.EnumDetail;
import com.supconit.common.daos.BaseDao;

import hc.base.domains.Pageable;

public interface EnumDetailDao extends BaseDao<EnumDetail, Long>{
	
	Pageable<EnumDetail> findByCondition(Pageable pager, EnumDetail condition);

	int deleteByIds(Long[] ids);
    
    public  EnumDetail findById(Long id); 
    public  List<EnumDetail> selectByTypeId(Long typeId); 
}
