package com.supconit.repair.daos;

import java.util.List;

import com.supconit.base.entities.Device;
import com.supconit.common.daos.BaseDao;
import com.supconit.repair.entities.RepairEvtCategoryPerson;

import hc.base.domains.Pageable;

public interface RepairEvtCategoryPersonDao extends BaseDao<RepairEvtCategoryPerson, Long> {

	public Pageable<RepairEvtCategoryPerson> findByCategoryArea(Pageable<RepairEvtCategoryPerson> pager, RepairEvtCategoryPerson condition);

	//public Pager<RepairEvtCategoryPerson> findRunReportByCondition(Integer pageNo, Integer pageSize, RepairEvtCategoryPerson condition);

	public int deleteByIds(Long[] ids);

	public RepairEvtCategoryPerson findById(Long id);

	public List<RepairEvtCategoryPerson> findByCodeName_g(RepairEvtCategoryPerson condition);

	public Pageable<RepairEvtCategoryPerson> findByCodeName_g(Pageable<RepairEvtCategoryPerson> pager, RepairEvtCategoryPerson condition);


	public Long findCountByCategoryId(long id);

	public List findMjZnzm(String geoareaCode ,List<Long> lstHaveOpenStatus ,List<Long> lstHaveAlarmStatus, boolean bol);

	public List<RepairEvtCategoryPerson> findStatusByCodes(RepairEvtCategoryPerson repairEvtCategoryPerson);

	public List<RepairEvtCategoryPerson> findGroupByCategoryId(List<Long> ids);

	public List<RepairEvtCategoryPerson> findGroupByLocationId(List<Long> ids);

    List<RepairEvtCategoryPerson> findByIds(List<Long> ids);

    List<RepairEvtCategoryPerson> findByParam(RepairEvtCategoryPerson repairEvtCategoryPerson);
    public Long getByCategoryTotal(String categoryCode);
	public Pageable<RepairEvtCategoryPerson> findByCondition(Pageable<RepairEvtCategoryPerson> pager,
			RepairEvtCategoryPerson condition);
	public void updateLocationName();
	public List<RepairEvtCategoryPerson> findCate(List<Long> lstSystemRuleId) ;
	public int 	deleteBypersonId(RepairEvtCategoryPerson repairEvtCategoryPerson) ;
	public void insert(List<Long> list,RepairEvtCategoryPerson person) ;
	public List<RepairEvtCategoryPerson> findByPersonIdandCategoryId(RepairEvtCategoryPerson repairEvtCategoryPerson) ;
	public int deleteForDistinct();
	public List<RepairEvtCategoryPerson> findByCategoryIdAndAreaId(Long categoryType,Long categoryId, Long areaId);
	Long countByCategoryId(Long categoryId);
}
