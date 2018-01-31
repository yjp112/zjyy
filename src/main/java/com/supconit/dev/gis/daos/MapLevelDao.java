
package com.supconit.dev.gis.daos;

import java.util.List;

import com.supconit.dev.gis.entities.MapLevel;
import com.supconit.hl.platform.daos.CommonDao;

import hc.base.domains.Pageable;

public interface MapLevelDao extends CommonDao<MapLevel, Long>{
	
	Pageable<MapLevel> findByCondition(Pageable<MapLevel> page, MapLevel condition);

	int deleteById(Long id);

	public Long countRecordByBuild(Long buildId);

	List<MapLevel> findByBuild(Long buildId);
	
	List<MapLevel> findByFloor(Long floorId);
	
	List<MapLevel> findAll();
	
	List<MapLevel> findAllBuild();

	List<MapLevel> findAllSystemMapLevel();

	List<MapLevel> findAllBySystemCode(String systemCode);
	
	int batchMerge();
}
