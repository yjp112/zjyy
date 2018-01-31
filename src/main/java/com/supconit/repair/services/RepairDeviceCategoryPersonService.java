package com.supconit.repair.services;

import java.util.List;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.repair.entities.RepairDeviceCategoryPerson;

import hc.base.domains.Pageable;


public interface RepairDeviceCategoryPersonService extends BaseBusinessService<RepairDeviceCategoryPerson, Long> {	

	Pageable<RepairDeviceCategoryPerson> findRunReportByCondition(Pageable<RepairDeviceCategoryPerson> pager, RepairDeviceCategoryPerson condition);
    /**
	 * findById
	 * @param object instance id
	 * @return object instance
	 */
	RepairDeviceCategoryPerson findById(Long id);

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
	void save(RepairDeviceCategoryPerson repairDeviceCategoryPerson);
	void insert(RepairDeviceCategoryPerson repairDeviceCategoryPerson,String fileorignal,String filename);
	void update(RepairDeviceCategoryPerson repairDeviceCategoryPerson,String fileorignal,String filename);
   
	List<RepairDeviceCategoryPerson> findByIds(List<Long> ids);
    List<RepairDeviceCategoryPerson> findByParam(RepairDeviceCategoryPerson repairDeviceCategoryPerson);
    public Long getByCategoryTotal(String categoryCode);
    public Pageable<RepairDeviceCategoryPerson> findByCategoryArea(Pageable<RepairDeviceCategoryPerson> pager, RepairDeviceCategoryPerson condition) ;
	Pageable<RepairDeviceCategoryPerson> findByCondition(Pageable<RepairDeviceCategoryPerson> pager, RepairDeviceCategoryPerson condition);
	public void deleteBypersonId(RepairDeviceCategoryPerson repairDeviceCategoryPerson);
	public List<RepairDeviceCategoryPerson> findByPersonIdandCategoryId(RepairDeviceCategoryPerson repairDeviceCategoryPerson) ;
	public void deleteForDistinct();
	List<RepairDeviceCategoryPerson> findByCategoryIdAndAreaId(Long categoryType,Long categoryId,Long areaId);
	RepairDeviceCategoryPerson getByCategoryIdAndAreaId(Long categoryType,Long categoryId,Long areaId);

}
