package com.supconit.repair.daos.impl;

import hc.base.domains.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.repair.daos.RepairDao;
import com.supconit.repair.entities.Repair;


@Repository
public class RepairDaoImpl extends AbstractBaseDao<Repair, Long> implements RepairDao {

    private static final String	NAMESPACE	= Repair.class.getName();
	
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
    
	@Override
	public Pageable<Repair> findByPage(Pageable<Repair> pager, Repair condition) {	
		return findByPager(pager, "findByCondition", "countByCondition", condition);
	}
	
	@Override
	public Pageable<Repair> findByDevicePage(Pageable<Repair> pager, Repair condition) {	
		return findByPager(pager, "findByDeviceId", "countByDeviceId", condition);
	}

    @Override
	public List<Repair> findByCauseType(Repair repair) {
		return selectList("findByCauseType",repair);
	}

	@Override
	public List<Repair> findByRepairMode(Repair repair) {
		return selectList("findByRepairMode",repair);
	}

	@Override
	public List<Repair> findByGrade(Repair repair) {
		return selectList("findByGrade",repair);
	}
	@Override
	public List<Repair> findGradeByGroups(Repair repair) {
		return selectList("findGradeByGroups",repair);
	}

	@Override
	public List<Repair> findByDelay(Repair repair) {
		return selectList("findByDelay",repair);
	}

	@Override
	public Pageable<Repair> findRepairMonitorPages(Pageable<Repair> pager,
			Repair condition) {
		return findByPager(pager, "findRepairMonitorPages", "countRepairMonitorPages", condition);
	}

	@Override
	public List<Repair> queryByGrade(Repair repair) {
		return selectList("queryByGrade",repair);
	}
    
	@Override
	public int deleteByIds(Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return update("deleteByIds", map);
	}

	@Override
	public List<Repair> findByRepairType(Repair repair) {
		return selectList("findByRepairType",repair);
	}

	@Override
	public Repair getByDeviceId(Long deviceId) {
		Map param = new HashMap<String, Long>();
		param.put("deviceId", deviceId);
		param.put("status", 2l);//维修完成
		return selectOne("getByDeviceId", param);
	}

	@Override
	public List<Repair> findByCostSaving(Repair repair) {
		return selectList("findByCostSaving",repair);
	}

	@Override
	public List<Repair> findByRepairTrend(Repair repair) {
		return selectList("findByRepairTrend",repair);
	}

	@Override
	public List<Repair> findByRepairArea(Repair repair) {
		return selectList("findByRepairArea",repair);
	}

}