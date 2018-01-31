package com.supconit.repair.daos;

import hc.base.domains.Pageable;

import java.util.List;

import com.supconit.common.daos.BaseDao;
import com.supconit.repair.entities.Repair;

public interface RepairDao extends BaseDao<Repair, Long>{
	
	Pageable<Repair> findByPage(Pageable<Repair> pager,Repair condition);
	
	Pageable<Repair> findByDevicePage(Pageable<Repair> pager,Repair condition);
	
	public List<Repair> findByCauseType(Repair repair) ;
	public List<Repair> findByRepairType(Repair repair) ;
	public List<Repair> findByRepairMode(Repair repair) ;
	public List<Repair> findGradeByGroups(Repair repair) ;
	public List<Repair> findByGrade(Repair repair) ;
	public List<Repair> queryByGrade(Repair repair) ;
	public List<Repair> findByDelay(Repair repair) ;
	public List<Repair> findByCostSaving(Repair repair) ;
	public List<Repair> findByRepairTrend(Repair repair) ;
	public List<Repair> findByRepairArea(Repair repair) ;
	
	/**
     * 监控中心分页查询
     */
    Pageable<Repair> findRepairMonitorPages(Pageable<Repair> pager,Repair condition);
    
	int deleteByIds(Long[] ids);
	/**
     * 查询设备第一次维修
     */
 	Repair getByDeviceId(Long deviceId);
}
