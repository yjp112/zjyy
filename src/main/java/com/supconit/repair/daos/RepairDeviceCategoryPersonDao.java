package com.supconit.repair.daos;

import java.util.List;

import com.supconit.common.daos.BaseDao;
import com.supconit.repair.entities.RepairDeviceCategoryPerson;

import hc.base.domains.Pageable;

public interface RepairDeviceCategoryPersonDao extends BaseDao<RepairDeviceCategoryPerson, Long> {

	public Pageable<RepairDeviceCategoryPerson> findByCategoryArea(Pageable<RepairDeviceCategoryPerson> pager, RepairDeviceCategoryPerson condition);

	//public Pager<RepairDeviceCategoryPerson> findRunReportByCondition(Integer pageNo, Integer pageSize, RepairDeviceCategoryPerson condition);

	public int deleteByIds(Long[] ids);

	public RepairDeviceCategoryPerson findById(Long id);

	public List<RepairDeviceCategoryPerson> findByCodeName_g(RepairDeviceCategoryPerson condition);

	public Pageable<RepairDeviceCategoryPerson> findByCodeName_g(Pageable<RepairDeviceCategoryPerson> pager, RepairDeviceCategoryPerson condition);


	public Long findCountByCategoryId(long id);

	public List findMjZnzm(String geoareaCode ,List<Long> lstHaveOpenStatus ,List<Long> lstHaveAlarmStatus, boolean bol);

	public List<RepairDeviceCategoryPerson> findStatusByCodes(RepairDeviceCategoryPerson repairDeviceCategoryPerson);

	public List<RepairDeviceCategoryPerson> findGroupByCategoryId(List<Long> ids);

	public List<RepairDeviceCategoryPerson> findGroupByLocationId(List<Long> ids);

    List<RepairDeviceCategoryPerson> findByIds(List<Long> ids);

    List<RepairDeviceCategoryPerson> findByParam(RepairDeviceCategoryPerson repairDeviceCategoryPerson);
    public Long getByCategoryTotal(String categoryCode);
	public Pageable<RepairDeviceCategoryPerson> findByCondition(Pageable<RepairDeviceCategoryPerson> pager,
			RepairDeviceCategoryPerson condition);
	public void updateLocationName();
	public List<RepairDeviceCategoryPerson> findCate(List<Long> lstSystemRuleId) ;
	public int 	deleteBypersonId(RepairDeviceCategoryPerson repairDeviceCategoryPerson) ;
	public void insert(List<Long> list,RepairDeviceCategoryPerson person) ;
	public List<RepairDeviceCategoryPerson> findByPersonIdandCategoryId(RepairDeviceCategoryPerson repairDeviceCategoryPerson) ;
	public int deleteForDistinct();
	public List<RepairDeviceCategoryPerson> findByCategoryIdAndAreaId(Long categoryType,Long categoryId, Long areaId);

}
