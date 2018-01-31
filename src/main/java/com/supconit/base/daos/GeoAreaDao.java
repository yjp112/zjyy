
package com.supconit.base.daos;

import java.util.List;

import com.supconit.base.entities.GeoArea;
import com.supconit.common.daos.BaseDao;

import hc.base.domains.Pageable;

public interface GeoAreaDao extends BaseDao<GeoArea, Long>{
	
	//查询分页
		Pageable<GeoArea> findByPage(Pageable<GeoArea> pager,GeoArea condition);
		
		List<GeoArea> findAll();

		List<GeoArea> findByRoot(Long id);

		List<GeoArea> findById(Long id);
	 
		Long countRecordByCode(String areaCode);
		
		List<GeoArea> findBuildsGis3D();
		
		List<GeoArea> findFloorById(Long id);

		List<GeoArea> findByCode(String code);

		List<GeoArea> findByParentId(Long parentId);

		GeoArea getByCode(String code);
		
		List<GeoArea>findAllFullName();
	    public List<GeoArea> findLouByCategory_g(String categoryCode);
	    public List<GeoArea> findCengByCategory_g(String categoryCode);
		public List<GeoArea> findAlarmFloor(Long parentId,String tuCeng);
		
		List<GeoArea> findByCon(GeoArea area);
		public List<GeoArea> findByCodes_g(List<String> lstCodes);

		List<Long> findChildIds(Long id);
		
		List<GeoArea> findFirstLevel();
		/**
		 * 查询区域子类别
		 */
		List<Long> findAreaChildIdsByName(String name);
}
