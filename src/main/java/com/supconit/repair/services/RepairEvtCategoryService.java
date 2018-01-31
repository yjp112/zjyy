package com.supconit.repair.services;

import java.util.List;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.repair.entities.RepairEvtCategory;

import hc.base.domains.Pageable;


public interface RepairEvtCategoryService extends BaseBusinessService<RepairEvtCategory, Long> {	

	/**
	 * Query by condition
	 * @param pageNo  page number
	 * @param pageSize  page size
	 * @param repairEvtCategory Query condition
	 * @return
	 */
	Pageable<RepairEvtCategory> findByCondition(Pageable<RepairEvtCategory> pager,RepairEvtCategory condition);



   /**
	 * delete objects 
	 * @param ids  object ID array	 
	 * @return 
	 */
	void deleteByIds(Long[] ids);

    /**
	 * save
	 * @param repairEvtCategory  object instance 
	 * @return
	 */
	void save(RepairEvtCategory repairEvtCategory);
	public List<RepairEvtCategory> findAll();
 	public void insert(RepairEvtCategory repairEvtCategory);
 	public void update(RepairEvtCategory repairEvtCategory);
	public void deleteById(Long id);    
	
}

