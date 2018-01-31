package com.supconit.repair.services;

import java.util.List;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.repair.entities.RepairEvtCategoryPerson;

import hc.base.domains.Pageable;


public interface RepairEvtCategoryPersonService extends BaseBusinessService<RepairEvtCategoryPerson, Long> {	

	Pageable<RepairEvtCategoryPerson> findRunReportByCondition(Pageable<RepairEvtCategoryPerson> pager, RepairEvtCategoryPerson condition);
    /**
	 * findById
	 * @param object instance id
	 * @return object instance
	 */
	RepairEvtCategoryPerson findById(Long id);

   /**
	 * delete objects 
	 * @param ids  object ID array	 
	 * @return 
	 */
	void deleteByIds(Long[] ids);

    /**
	 * save
	 * @param device  object instance 
	 * @return
	 */
	void save(RepairEvtCategoryPerson repairEvtCategoryPerson);
	void insert(RepairEvtCategoryPerson repairEvtCategoryPerson,String fileorignal,String filename);
	void update(RepairEvtCategoryPerson repairEvtCategoryPerson,String fileorignal,String filename);
   
	List<RepairEvtCategoryPerson> findByIds(List<Long> ids);
    List<RepairEvtCategoryPerson> findByParam(RepairEvtCategoryPerson repairEvtCategoryPerson);
    public Long getByCategoryTotal(String categoryCode);
    public Pageable<RepairEvtCategoryPerson> findByCategoryArea(Pageable<RepairEvtCategoryPerson> pager, RepairEvtCategoryPerson condition) ;
	Pageable<RepairEvtCategoryPerson> findByCondition(Pageable<RepairEvtCategoryPerson> pager, RepairEvtCategoryPerson condition);
	public void deleteBypersonId(RepairEvtCategoryPerson repairEvtCategoryPerson);
	public List<RepairEvtCategoryPerson> findByPersonIdandCategoryId(RepairEvtCategoryPerson repairEvtCategoryPerson) ;
	public void deleteForDistinct();
	List<RepairEvtCategoryPerson> findByCategoryIdAndAreaId(Long categoryType,Long categoryId,Long areaId);
	RepairEvtCategoryPerson getByCategoryIdAndAreaId(Long categoryType,Long categoryId,Long areaId);

}
