
package com.supconit.base.services;

import java.util.List;

import com.supconit.base.entities.GeoArea;
import com.supconit.common.services.BaseBusinessService;

import hc.base.domains.Pageable;


public interface GeoAreaService extends BaseBusinessService<GeoArea, Long> {	

	//分页查询
		Pageable<GeoArea> findByPage(Pageable pager,GeoArea geoArea);

		List<GeoArea> findTree();
		List<GeoArea> findByRoot(long parentId);
	    List<GeoArea> findBuildsGis3D();
	    List<GeoArea> findFloorById(Long buildingId);

		List<GeoArea> findByCode(String code);

		//public String checkDelete(Long[] checkValue);

		public void removeGeoArea(Long[] checkValue);

		public GeoArea getByCode(String code);
		public List<GeoArea> findBuildings();
		List<GeoArea>findAllFullName();
		public List<GeoArea> findLouByCategory_g(String categoryCode);
		public List<GeoArea> findCengByCategory_g(String categoryCode);
		public List<GeoArea> findAlarmFloor(Long parentId,String tuCeng) ;
		public List<GeoArea> findAll();
		public void insertForImp(List<GeoArea> list);
		
		
		List<GeoArea> findByCon(GeoArea area);
		List<GeoArea> findById(Long id);
		
		public List<GeoArea> findByCodes_g(List<String> lstCodes);
		
		public List<GeoArea> findByParentId(Long parentId);
}

