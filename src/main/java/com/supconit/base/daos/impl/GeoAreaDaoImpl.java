
package com.supconit.base.daos.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.base.daos.GeoAreaDao;
import com.supconit.base.entities.GeoArea;
import com.supconit.common.daos.AbstractBaseDao;

import hc.base.domains.Pageable;


@Repository
public class GeoAreaDaoImpl extends AbstractBaseDao<GeoArea, Long> implements GeoAreaDao {

	@Override
	protected String getNamespace() {
		return GeoArea.class.getName();
	}
	
	@Override
	public Pageable<GeoArea> findByPage(Pageable<GeoArea> pager,GeoArea condition){
		return findByPager(pager, "findByConditions", "countByConditions", condition);
	}
	@Override
	public List<GeoArea> findAll() {
		return selectList("findAll");
	}

	@Override
	public List<GeoArea> findByRoot(Long id) {
		return selectList("findByRoot",id);
	}

	@Override
	public List<GeoArea> findById(Long id) {
		return selectList("findById",id);
	}
	
	@Override
	public Long countRecordByCode(String areaCode) {
		GeoArea condition=new GeoArea();
        condition.setAreaCode(areaCode);
        return getSqlSession().selectOne(GeoArea.class.getName()+".countByConditions", condition);
	}
	
	@Override
	public List<GeoArea> findBuildsGis3D(){
		return selectList("findBuildsGis3D");		
	}
	@Override
	public List<GeoArea> findFloorById(Long id) {
		return selectList("findFloorByParentId",id);		
	}

	@Override
	public List<GeoArea> findByCode(String code) {
		return selectList("findByCode",code);
	}

	@Override
	public List<GeoArea> findByParentId(Long parentId) {
		return selectList("findByParentId",parentId);
	}

	@Override
	public GeoArea getByCode(String code) {
		return selectOne("findByCode",code);
	}
	@Override
	public List<GeoArea>findAllFullName() {
		return selectList("findAllFullName");
	}
	@Override
	public List<GeoArea> findLouByCategory_g(String categoryCode) {
		Map map = new HashMap();
		map.put("categoryCode", categoryCode);
        return  selectList("findLouByCategory_g",map);
	}
	@Override
	public List<GeoArea> findCengByCategory_g(String categoryCode) {
		Map map = new HashMap();
		map.put("categoryCode", categoryCode);
        return  selectList("findCengByCategory_g",map);
	}
	@Override
	public List<GeoArea> findAlarmFloor(Long parentId,String tuCeng) {
		Map  map =new HashMap();
		map.put("parentId", parentId);
		map.put("tuCeng", ","+tuCeng+",");
		return selectList("findAlarmFloor_g",map);
	}

	@Override
	public List<GeoArea> findByCon(GeoArea area) {
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
	public List<GeoArea> findByCodes_g(List<String> lstCodes) {
		Map mapCodes=new HashMap();
		mapCodes.put("codes", lstCodes);
		return selectList("findByCodes_g",mapCodes);
	}

	@Override
	public List<GeoArea> findFirstLevel() {
		return selectList("findFirstLevel");
	}

	@Override
	public List<Long> findAreaChildIdsByName(String name) {
		return selectList("findAreaChildIdsByName",name);
	}

}