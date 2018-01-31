package com.supconit.nhgl.basic.otherCategory.dao.impl;

import hc.base.domains.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.nhgl.basic.otherCategory.dao.OtherCategoryDao;
import com.supconit.nhgl.basic.otherCategory.entity.OtherCategory;

@Repository
public class OtherCategoryDaoImpl extends AbstractBaseDao<OtherCategory, Long> implements OtherCategoryDao{

	private static final String	NAMESPACE = OtherCategory.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	@Override
	public Pageable<OtherCategory> findByPage(Pageable<OtherCategory> pager,OtherCategory condition){
		return findByPager(pager, "findByConditions", "countByConditions", condition);
	}
	@Override
	public List<OtherCategory> findAll() {
		return selectList("findAll");
	}

	@Override
	public List<OtherCategory> findByRoot(Long id) {
		return selectList("findByRoot",id);
	}

	@Override
	public List<OtherCategory> findById(Long id) {
		return selectList("findById",id);
	}
	
	@Override
	public Long countRecordByCode(String areaCode) {
		OtherCategory condition=new OtherCategory();
        condition.setCode(areaCode);
        return getSqlSession().selectOne(OtherCategory.class.getName()+".countByConditions", condition);
	}
	
	@Override
	public List<OtherCategory> findBuildsGis3D(){
		return selectList("findBuildsGis3D");		
	}
	@Override
	public List<OtherCategory> findFloorById(Long id) {
		return selectList("findFloorByParentId",id);		
	}

	@Override
	public List<OtherCategory> findByCode(String code) {
		return selectList("findByCode",code);
	}

	@Override
	public List<OtherCategory> findByParentId(Long parentId) {
		return selectList("findByParentId",parentId);
	}

	@Override
	public OtherCategory getByCode(String code) {
		return selectOne("findByCode",code);
	}
	@Override
	public List<OtherCategory>findAllFullName() {
		return selectList("findAllFullName");
	}
	@Override
	public List<OtherCategory> findLouByCategory_g(String categoryCode) {
		Map map = new HashMap();
		map.put("categoryCode", categoryCode);
        return  selectList("findLouByCategory_g",map);
	}
	@Override
	public List<OtherCategory> findCengByCategory_g(String categoryCode) {
		Map map = new HashMap();
		map.put("categoryCode", categoryCode);
        return  selectList("findCengByCategory_g",map);
	}
	@Override
	public List<OtherCategory> findAlarmFloor(Long parentId,String tuCeng) {
		Map  map =new HashMap();
		map.put("parentId", parentId);
		map.put("tuCeng", ","+tuCeng+",");
		return selectList("findAlarmFloor_g",map);
	}

	@Override
	public List<OtherCategory> findByCon(OtherCategory area) {
		return selectList("findByCon", area);
	}
	@Override
	public List<Long> findChildIds(Long id) {
		List<Long> childIds = null;
        if(id!=null){//递归查询子节点
            childIds=selectList("findChildIds",id);
          }	
        return  childIds;
	}
	@Override
	public List<OtherCategory> findByCodes_g(List<String> lstCodes) {
		Map mapCodes=new HashMap();
		mapCodes.put("codes", lstCodes);
		return selectList("findByCodes_g",mapCodes);
	}

	@Override
	public List<OtherCategory> findByName(OtherCategory area) {
		// TODO Auto-generated method stub
		return selectList("findByName",area);
	}
	
	

}
