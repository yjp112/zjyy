package com.supconit.repair.daos;

import java.util.List;

import com.supconit.common.daos.BaseDao;
import com.supconit.repair.entities.RepairEvtCategory;

import hc.base.domains.Pageable;

public interface RepairEvtCategoryDao extends BaseDao<RepairEvtCategory, Long>{
	
	Pageable<RepairEvtCategory> findByCondition(Pageable<RepairEvtCategory> pager,RepairEvtCategory condition);

	int deleteByIds(Long[] ids);
    
    public  RepairEvtCategory findById(Long id); 
    public List<RepairEvtCategory> findAll();
	public List<Long> findChildIds(Long id);
	public List<RepairEvtCategory> findChildIdsEntity(Long id);
	public Long findChildrenCount(long id);
	public List<RepairEvtCategory> findByCodes_g(List<String> lstCodes) ;

	public RepairEvtCategory getByPidAndCategoryName(RepairEvtCategory re);

}
