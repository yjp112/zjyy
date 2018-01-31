package com.supconit.repair.services;

import hc.base.domains.Pageable;

import java.util.List;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.repair.entities.Repair;


public interface RepairService extends BaseBusinessService<Repair, Long> {	

    Pageable<Repair> findByPage(Pageable<Repair> pager, Repair condition);
    
    Pageable<Repair> findByDevicePage(Pageable<Repair> pager, Repair condition);
    public List<Repair> findByRepairTrend(Repair repair) ;
	void insert(Repair repair,String[] fileorignal,String[] filename,String[] delfiles,String fileLength);
	void update(Repair repair,String[] fileorignal,String[] filename,String[] delfiles,String fileLength,boolean flag);
	void submitProcess(Repair repair,String[] fileorignal,String[] filename,String[] delfiles,String fileLength,boolean flag);
	void startProcess(Repair repair,String[] fileorignal,String[] filename,String[] delfiles,String fileLength);
    void deleteProcessInstance(Long id);
	/** 手机端 **/
	void insertRepair(Repair repair);
	void updateRepair(Repair repair);
	void submitProcess(Repair repair);
	public List<Repair> findByCostSaving(Repair repair) ;
    public List<Repair> findByCauseType(Repair repair) ;
	public List<Repair> findByRepairType(Repair repair) ;
	public List<Repair> findByRepairMode(Repair repair) ;
	public List<Repair> findByGrade(Repair repair) ;
	public List<Repair> findGradeByGroups(Repair repair) ;
	public List<Repair> queryByGrade(Repair repair) ;
	public List<Repair> findByDelay(Repair repair) ;
	public List<Repair> findByRepairArea(Repair repair) ;
	/**
     * 监控中心分页查询
     */
    Pageable<Repair> findRepairMonitorPages(Pageable<Repair> pager,Repair condition);
    
 	void deleteByIds(Long[] ids);
 	/**
     * 查询设备第一次维修
     */
 	Repair getByDeviceId(Long deviceId);

}
