package com.supconit.nhgl.basic.otherCategory.dao;

import hc.base.domains.Pageable;

import java.util.List;

import com.supconit.common.daos.BaseDao;
import com.supconit.nhgl.basic.otherCategory.entity.OtherCategory;

public interface OtherCategoryDao extends BaseDao<OtherCategory, Long>{
	//查询分页
	Pageable<OtherCategory> findByPage(Pageable<OtherCategory> pager,OtherCategory condition);
	
	List<OtherCategory> findAll();

	List<OtherCategory> findByRoot(Long id);

	List<OtherCategory> findById(Long id);
 
	Long countRecordByCode(String areaCode);
	
	List<OtherCategory> findBuildsGis3D();
	
	List<OtherCategory> findFloorById(Long id);

	List<OtherCategory> findByCode(String code);
	
	List<OtherCategory> findByName(OtherCategory area); 

	List<OtherCategory> findByParentId(Long parentId);

	OtherCategory getByCode(String code);
	
	List<OtherCategory>findAllFullName();
    public List<OtherCategory> findLouByCategory_g(String categoryCode);
    public List<OtherCategory> findCengByCategory_g(String categoryCode);
	public List<OtherCategory> findAlarmFloor(Long parentId,String tuCeng);
	
	List<OtherCategory> findByCon(OtherCategory area);
	public List<OtherCategory> findByCodes_g(List<String> lstCodes);

	List<Long> findChildIds(Long id);

}
