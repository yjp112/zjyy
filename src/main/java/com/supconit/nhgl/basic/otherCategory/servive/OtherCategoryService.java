package com.supconit.nhgl.basic.otherCategory.servive;

import hc.base.domains.Pageable;

import java.util.List;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.nhgl.basic.otherCategory.entity.OtherCategory;

public interface OtherCategoryService extends BaseBusinessService<OtherCategory, Long> {
	//分页查询
	Pageable<OtherCategory> findByPage(Pageable pager,OtherCategory OtherCategory);

	List<OtherCategory> findTree();
	List<OtherCategory> findByRoot(long parentId);
    List<OtherCategory> findBuildsGis3D();
    List<OtherCategory> findFloorById(Long buildingId);

	List<OtherCategory> findByCode(String code);

	//public String checkDelete(Long[] checkValue);

	public void removeOtherCategory(Long[] checkValue);

	public OtherCategory getByCode(String code);
	public List<OtherCategory> findBuildings();
	List<OtherCategory>findAllFullName();
	public List<OtherCategory> findLouByCategory_g(String categoryCode);
	public List<OtherCategory> findCengByCategory_g(String categoryCode);
	public List<OtherCategory> findAlarmFloor(Long parentId,String tuCeng) ;
	public List<OtherCategory> findAll();
	public void insertForImp(List<OtherCategory> list);
	
	
	List<OtherCategory> findByCon(OtherCategory area);
	List<OtherCategory> findById(Long id);
	
	public List<OtherCategory> findByCodes_g(List<String> lstCodes);
	
	public List<OtherCategory> findByParentId(Long parentId);
}
