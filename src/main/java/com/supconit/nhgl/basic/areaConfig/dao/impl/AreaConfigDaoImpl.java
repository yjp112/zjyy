package com.supconit.nhgl.basic.areaConfig.dao.impl;

import hc.base.domains.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.nhgl.basic.areaConfig.dao.AreaConfigDao;
import com.supconit.nhgl.basic.areaConfig.entities.AreaConfig;

@Repository
public class AreaConfigDaoImpl extends AbstractBaseDao<AreaConfig,Long> implements AreaConfigDao{
	private static final String NAME_SPACE=AreaConfig.class.getName();
	
	@Override
	public Pageable<AreaConfig> findByCondition(Pageable<AreaConfig> pager,
			AreaConfig condition) {
		return findByPager(pager, "findByCondition", "countByCondition", condition);
	}
	@Override
	protected String getNamespace() {
		return NAME_SPACE;
	}


	@Override
	public List<AreaConfig> findByAreaIdAndRule(AreaConfig areaConfig) {
		return selectList("findByAreaIdAndRule",areaConfig);
	}
	@Override
	public int deleteByIds(Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return update("deleteByIds", map);
	}
	@Override
	public int deleteByAreaId(Long areaId,String nhtype) {
		Map map=new HashMap();
		map.put("areaId", areaId);
		map.put("nhType", nhtype);
		return update("deleteByAreaId",map);
	}
	@Override
	public int countFindAll(AreaConfig areaConfig) {
		// TODO Auto-generated method stub
		return selectOne("countFindAll", areaConfig); 
	}
	@Override
	public AreaConfig getByCondition(AreaConfig areaConfig) {
		// TODO Auto-generated method stub
		return selectOne("getByCondition",areaConfig);
	}
	@Override
	public Long getByAreaId(Long id) {
		// TODO Auto-generated method stub
		return selectOne("getByAreaId",id);
	}
	@Override
	public List<AreaConfig> findAllDevicesWithOutRoot() {
		return selectList("findAllDevicesWithOutRoot");
	}
	@Override
	public List<AreaConfig> findAllArea() {
		return selectList("findAllArea");
	}
	@Override
	public Pageable<AreaConfig> findUnusedDevice(Pageable<AreaConfig> pager,
			AreaConfig condition) {
		return findByPager(pager,"findUnusedDevice","countUnusedDevice",condition);
	}
	@Override
	public Pageable<AreaConfig> findRepeatDevice(Pageable<AreaConfig> pager,
			AreaConfig condition) {
		return findByPager(pager,"findRepeatDevice","countRepeatDevice",condition);
	}
	@Override
	public Pageable<AreaConfig> findErrorFtxs(Pageable<AreaConfig> pager,
			AreaConfig condition) {
		return findByPager(pager,"findErrorFtxs","countErrorFtxs",condition);
	}
	@Override
	public List<AreaConfig> findErrorIsSum(String nhType) {
		return selectList("findErrorIsSum",nhType);
	}
	@Override
	public void deleteByAreaIdAndNhtype(Set<Long> ids, String nhType) {
		Map map=new HashMap();
		map.put("ids", ids);
		map.put("nhType", nhType);
		delete("deleteByAreaIdAndNhtype", map);
	}
	@Override
	public List<AreaConfig> findAreaConfigByNhType(AreaConfig condition) {
		return selectList("findAreaConfigByNhType",condition);
	}
	
	@Override
	public void deleteAll() {
		delete("deleteAll");
	}
	@Override
	public List<AreaConfig> findDevicesMapWithOutRoot() {
		return selectList("findDevicesMapWithOutRoot");
	}
}
