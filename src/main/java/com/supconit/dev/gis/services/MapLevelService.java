package com.supconit.dev.gis.services;

import java.util.List;

import com.supconit.dev.gis.entities.MapLevel;

import hc.base.domains.Pageable;
import hc.orm.BasicOrmService;

public interface MapLevelService extends BasicOrmService<MapLevel, Long> {

	Pageable<MapLevel> findByCondition(Pageable<MapLevel> pager, MapLevel condition) throws Exception;
	
	void deleteById(Long id) throws Exception;
	
	List<MapLevel> findAll() throws Exception;
	
	List<MapLevel> findAllBuild() throws Exception;
	
	List<MapLevel> findAllSystemMapLevel() throws Exception;
	
	List<MapLevel> findAllBySystemCode(String systemCode) throws Exception;
	
	void batchMerge();
}
