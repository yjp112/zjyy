package com.supconit.dev.gis.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.dev.gis.daos.MapLevelDao;
import com.supconit.dev.gis.entities.MapLevel;
import com.supconit.dev.gis.services.MapLevelService;
import com.supconit.hl.base.services.GeoAreaService;
import com.supconit.hl.common.exception.BusinessDoneException;
import com.supconit.hl.common.services.impl.AbstractBaseBusinessService;
import com.supconit.hl.gis.controllers.GisConfigInfo;

import hc.base.domains.Pageable;

@Service
public class MapLevelServiceImpl extends
        AbstractBaseBusinessService<MapLevel, Long> implements MapLevelService {
	
	@Autowired
	private MapLevelDao mapLevelDao;
	@Autowired
	private GeoAreaService geoAreaService;

	@Override
	public MapLevel getById(Long id) {
		if(null == id || id <= 0)
			return null;
		MapLevel mapLevel = mapLevelDao.getById(id);
		return mapLevel;
	}

	@Override
	public void insert(MapLevel mapLevel){
		checkToSave(mapLevel);
		mapLevelDao.insert(mapLevel);
		GisConfigInfo.evict();
	}

	@Override
	public void update(MapLevel mapLevel){
		checkToSave(mapLevel);
		mapLevelDao.update(mapLevel);
		GisConfigInfo.evict();
	}

	@Override
	public Pageable<MapLevel> findByCondition(Pageable<MapLevel> pager,
			MapLevel condition) {
		return mapLevelDao.findByCondition(pager, condition);
	}

	@Override
	public void deleteById(Long id) {
		mapLevelDao.deleteById(id);
		GisConfigInfo.evict();
	}

	private void checkToSave(MapLevel entity) {
		List<MapLevel> list = new ArrayList<MapLevel>();
		List<MapLevel> list2 = new ArrayList<MapLevel>();
		list = mapLevelDao.findByFloor(entity.getInitFloor());
		//list2 = mapLevelDao.findByName(entity.getAreaName());
		if (null != list && list.size() >= 1) {
			String areaName = geoAreaService.getById(entity.getInitFloor()).getAreaName();
			if (list.size() > 1) {
				throw new BusinessDoneException("楼层[" + areaName+ "]已经被占用。");
			} else {
				if (entity.getId() != null) {
					MapLevel old = list.get(0);
					if (entity.getId().longValue() == old.getId().longValue()) {
					} else {
						throw new BusinessDoneException("楼层["+ areaName + "]已经被占用。");
					}
				} else {
					throw new BusinessDoneException("楼层["+ areaName + "]已经被占用。");
				}

			}
		}
//		if (null != list2 && list2.size() >= 1) {
//			if (list2.size() > 1) {
//				throw new BusinessDoneException("名称[" + entity.getAreaName()+ "]已经被占用。");
//			} else {
//				if (entity.getId() != null) {
//					MapLevel old = list2.get(0);
//					if (entity.getId().longValue() == old.getId().longValue()) {
//					} else {
//						throw new BusinessDoneException("名称["+ entity.getAreaName() + "]已经被占用。");
//					}
//				} else {
//					throw new BusinessDoneException("名称["+ entity.getAreaName() + "]已经被占用。");
//				}
//
//			}
//		}
	}

	@Override
	public List<MapLevel> findAll() {
		return mapLevelDao.findAll();
	}

	@Override
	public List<MapLevel> findAllSystemMapLevel() throws Exception {
		return mapLevelDao.findAllSystemMapLevel();
	}

	@Override
	public List<MapLevel> findAllBuild() throws Exception {
		return mapLevelDao.findAllBuild();
	}

	@Override
	public List<MapLevel> findAllBySystemCode(String systemCode) throws Exception {
		return mapLevelDao.findAllBySystemCode(systemCode);
	}

	@Override
	public void batchMerge() {
		mapLevelDao.batchMerge();
	}
}
