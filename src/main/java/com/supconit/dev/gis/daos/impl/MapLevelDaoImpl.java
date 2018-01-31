package com.supconit.dev.gis.daos.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.dev.gis.daos.MapLevelDao;
import com.supconit.dev.gis.entities.MapLevel;
import com.supconit.hl.common.daos.impl.AbstractBaseDao;

import hc.base.domains.Pageable;

@Repository
public class MapLevelDaoImpl extends AbstractBaseDao<MapLevel, Long>
		implements MapLevelDao {
	private static final String	NAMESPACE	= MapLevel.class.getName();

	@Override
	public Pageable<MapLevel> findByCondition(Pageable<MapLevel> page, MapLevel condition) {
		return findByPager(page,"findByCondition", "countByCondition", condition);
	}

	@Override
	public Long countRecordByBuild(Long buildId) {
		MapLevel condition = new MapLevel();
        condition.setInitBuild(buildId);
        Map param = new HashMap<String, Object>();
		param.put("condition", condition);
        return getSqlSession().selectOne(MapLevel.class.getName()+".countByCondition", param);
	}


	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	
	@Override
	public List<MapLevel> findAll() {
		return selectList("findAll");
	}

	@Override
	public List<MapLevel> findByBuild(Long buildId) {
		return selectList("findByBuild", buildId);
	}

	@Override
	public List<MapLevel> findByFloor(Long floorId) {
		return selectList("findByFloor", floorId);
	}

	@Override
	public List<MapLevel> findAllSystemMapLevel() {
		// TODO Auto-generated method stub
		return selectList("findAllSystemMapLevel");
	}

	@Override
	public List<MapLevel> findAllBuild() {
		return selectList("findAllBuild"); 
	}

	@Override
	public List<MapLevel> findAllBySystemCode(String systemCode) {
		return selectList("findAllSystemCode",systemCode);
	}

	@Override
	public int batchMerge() {
		return update("batchMerge");
	}
}
