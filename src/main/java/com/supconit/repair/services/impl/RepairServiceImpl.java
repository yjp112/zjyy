package com.supconit.repair.services.impl;

import hc.base.domains.Pageable;
import hc.bpm.context.annotations.BpmSupport;
import hc.bpm.services.ProcessService;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.base.daos.AttachmentDao;
import com.supconit.base.daos.DeviceCategoryDao;
import com.supconit.base.daos.GeoAreaDao;
import com.supconit.base.entities.GeoArea;
import com.supconit.common.exceptions.BusinessDoneException;
import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.common.utils.Constant;
import com.supconit.common.utils.DateUtils;
import com.supconit.common.utils.UtilTool;
import com.supconit.honeycomb.business.organization.entities.Department;
import com.supconit.honeycomb.business.organization.services.DepartmentService;
import com.supconit.repair.daos.RepairDao;
import com.supconit.repair.daos.RepairSpareDao;
import com.supconit.repair.daos.RepairWorkerDao;
import com.supconit.repair.entities.Repair;
import com.supconit.repair.services.RepairService;

@Service
public class RepairServiceImpl extends AbstractBaseBusinessService<Repair, Long> implements RepairService {

	@Autowired
	private RepairDao repairDao;
	@Autowired
	private DepartmentService deptService;
	@Autowired
	private DeviceCategoryDao deviceCategoryDao;
	@Autowired
	private GeoAreaDao geoAreaDao;
	@Autowired
	private AttachmentDao attachmentDao;
	@Autowired
	private RepairWorkerDao repairWorkerDao;
	@Autowired
	private RepairSpareDao repairSpareDao;
    @Autowired
    private ProcessService processService;
	
	@Override
	@Transactional(readOnly = true)
	public Pageable<Repair> findByPage(Pageable<Repair> pager,Repair condition) {
		return repairDao.findByPage(pager, condition);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Pageable<Repair> findByDevicePage(Pageable<Repair> pager,Repair condition) {
		return repairDao.findByDevicePage(pager, condition);
	}

	@Override
	public List<Repair> findByCauseType(Repair repair) {
		return repairDao.findByCauseType(repair);
	}

	@Override
	public List<Repair> findByRepairMode(Repair repair) {
		return repairDao.findByRepairMode(repair);
	}

	@Override
	public List<Repair> findByGrade(Repair repair) {
		return repairDao.findByGrade(repair);
	}
	@Override
	public List<Repair> findByDelay(Repair repair) {
		return repairDao.findByDelay(repair);
	}
	
	@Override
	public void deleteById(Long id) {
		repairDao.deleteById(id);
	}
	
	@Override
	@Transactional
	public void deleteByIds(Long[] ids) {
		repairDao.deleteByIds(ids);
	}

	@Override
	public Repair getById(Long id) {
		return repairDao.getById(id);
	}

	@Override
	public void insert(Repair repair) {
		repairDao.insert(repair);
	}

	@Override
	public void update(Repair repair) {
		repairDao.update(repair);
	}

	@Override
	public Pageable<Repair> findRepairMonitorPages(Pageable<Repair> pager,
			Repair condition) {
		setParameter(condition);
		pager = repairDao.findRepairMonitorPages(pager, condition);
		for (Repair repair : pager) {
			String time = repair.getDelayTime();
			if(StringUtils.isEmpty(time)) continue;
			long ms = Long.parseLong(time);
			repair.setDelayTime(DateUtils.milliSecondformatTime(ms));
		}
		return pager;
	}

	@Override
	public List<Repair> queryByGrade(Repair repair) {
		return repairDao.queryByGrade(repair);
	}
	
	/**
	 * 设置(部门\类别\区域)
	*/
	private void setParameter(Repair condition){
		//部门
		if(null != condition.getRepairDeptId()){
			List<Department> deptList = deptService.findByPid(condition.getRepairDeptId());
			List<Long> deptIds=new ArrayList<Long>();
			if(!UtilTool.isEmptyList(deptList));{
				for(Department dept:deptList){
					deptIds.add(dept.getId());
				}
			}
			deptIds.add(condition.getRepairDeptId());
			condition.setDeptChildIds(deptIds);
		}
		//类别
		if(null != condition.getCategoryId()){
			List<Long> categoryIds=deviceCategoryDao.findChildIds(condition.getCategoryId());
			condition.setDeviceCategoryChildIds(categoryIds);
		}
		//区域
		if(null != condition.getAreaId()){
			//查出每个节点对应的子节点（包括自己）
			List<GeoArea> listGeoAreas = new ArrayList<GeoArea>();
			if(condition.getAreaId().longValue()==0l){//如果是根节点
				listGeoAreas = geoAreaDao.findByRoot(condition.getAreaId());
			}else{
				listGeoAreas = geoAreaDao.findById(condition.getAreaId());
			}
			List<Long> areaIds = new ArrayList<Long>();
			for (int i=0;i<listGeoAreas.size();i++){
				areaIds.add(listGeoAreas.get(i).getId());
			}
			condition.setGeoAreaChildIds(areaIds);
		}	
	}
	
    @Override
	@Transactional
	public void update(Repair repair,String[] fileorignal,String[] filename,String[] delfiles,String fileLength,boolean flag){     
		try {
			repairDao.update(repair);
			if(flag){
				repairWorkerDao.deleteByOrderCode(repair.getRepairCode());
				repairSpareDao.deleteByOrderCode(repair.getRepairCode());
				if(null != repair.getWorkerList() && repair.getWorkerList().size()>0){
					repairWorkerDao.batchInsert(repair.getWorkerList(), repair.getRepairCode());
				}
				if(null != repair.getSpareList() && repair.getSpareList().size()>0){
					repairSpareDao.batchInsert(repair.getSpareList(), repair.getRepairCode());
				}
				attachmentDao.saveAttachements(repair.getId(),Constant.ATTACHEMENT_REPAIR,fileorignal, filename, delfiles,fileLength);
			}
	    	
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessDoneException("保存失败");  
		}
	}

    @Override
	@Transactional
	public void insert(Repair repair,String[] fileorignal,String[] filename,String[] delfiles,String fileLength){     
		try {
			repairDao.insert(repair);
			repairWorkerDao.deleteByOrderCode(repair.getRepairCode());
			repairSpareDao.deleteByOrderCode(repair.getRepairCode());
			if(null != repair.getWorkerList() && repair.getWorkerList().size()>0){
				repairWorkerDao.batchInsert(repair.getWorkerList(), repair.getRepairCode());
			}
			if(null != repair.getSpareList() && repair.getSpareList().size()>0){
				repairSpareDao.batchInsert(repair.getSpareList(), repair.getRepairCode());
			}
	    	attachmentDao.saveAttachements(repair.getId(),Constant.ATTACHEMENT_REPAIR,fileorignal, filename, delfiles,fileLength);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessDoneException("保存失败");  
		}
	}
    
    @Override
	@Transactional
	@BpmSupport(businessKey = "#repair.id", businessDomain = "#repair", variableKeys = "{'task'}", variables = "{#repair}")
	public void startProcess(Repair repair,String[] fileorignal,String[] filename,String[] delfiles,String fileLength){     
    	insert(repair,fileorignal,filename,delfiles,fileLength);
	}
    
    @Override
	@Transactional
	@BpmSupport(businessKey = "#repair.id", businessDomain = "#repair", variableKeys = "{'task'}", variables = "{#repair}")
	public void submitProcess(Repair repair,String[] fileorignal,String[] filename,String[] delfiles,String fileLength,boolean flag){     
    	if (null == repair.getId()) {
            this.insert(repair,fileorignal,filename,delfiles,fileLength);
        } else {
            this.update(repair,fileorignal,filename,delfiles,fileLength,flag);
        }
	}   
    
    @Override
    @Transactional
    public void deleteProcessInstance(Long id) {
    	Repair repair=repairDao.getById(id);
        deleteById(id);
        processService.deleteProcessInstance(repair.getProcessInstanceId());
    }

	/** 手机端 **/
	@Override
	@Transactional
	public void updateRepair(Repair repair){
		try {
			repairDao.update(repair);
			if(repair.getStatus()==3)
			{
				repairWorkerDao.deleteByOrderCode(repair.getRepairCode());
				repairSpareDao.deleteByOrderCode(repair.getRepairCode());
				if(null != repair.getWorkerList() && repair.getWorkerList().size()>0){
					repairWorkerDao.batchInsert(repair.getWorkerList(), repair.getRepairCode());
				}
				if(null != repair.getSpareList() && repair.getSpareList().size()>0){
					repairSpareDao.batchInsert(repair.getSpareList(), repair.getRepairCode());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessDoneException("保存失败");
		}
	}


	@Override
	@Transactional
	public void insertRepair(Repair repair){
		try {
			repairDao.insert(repair);
			repairWorkerDao.deleteByOrderCode(repair.getRepairCode());
			repairSpareDao.deleteByOrderCode(repair.getRepairCode());
			if(null != repair.getWorkerList() && repair.getWorkerList().size()>0){
				repairWorkerDao.batchInsert(repair.getWorkerList(), repair.getRepairCode());
			}
			if(null != repair.getSpareList() && repair.getSpareList().size()>0){
				repairSpareDao.batchInsert(repair.getSpareList(), repair.getRepairCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessDoneException("保存失败");
		}
	}

	@Override
	@Transactional
	@BpmSupport(businessKey = "#repair.id", businessDomain = "#repair", variableKeys = "{'task'}", variables = "{#repair}")
	public void submitProcess(Repair repair){
		if (null == repair.getId()) {
			this.insertRepair(repair);
		} else {
			this.updateRepair(repair);
		}
	}

	@Override
	public List<Repair> findByRepairType(Repair repair) {
		return repairDao.findByRepairType(repair);
	}

	@Override
	public List<Repair> findGradeByGroups(Repair repair) {
		return repairDao.findGradeByGroups(repair);
	}

	@Override
	public Repair getByDeviceId(Long deviceId) {
		return repairDao.getByDeviceId(deviceId);
	}

	@Override
	public List<Repair> findByCostSaving(Repair repair) {
		return repairDao.findByCostSaving(repair);
	}

	@Override
	public List<Repair> findByRepairTrend(Repair repair) {
		return repairDao.findByRepairTrend(repair);
	}

	@Override
	public List<Repair> findByRepairArea(Repair repair) {
		return repairDao.findByRepairArea(repair);
	}

}
