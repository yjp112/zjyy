package com.supconit.maintain.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.base.daos.AttachmentDao;
import com.supconit.base.daos.DeviceCategoryDao;
import com.supconit.base.daos.GeoAreaDao;
import com.supconit.base.entities.GeoArea;
import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.common.utils.Constant;
import com.supconit.maintain.daos.MaintainSpareDao;
import com.supconit.maintain.daos.MaintainTaskContentDao;
import com.supconit.maintain.daos.MaintainTaskDao;
import com.supconit.maintain.entities.MaintainSpare;
import com.supconit.maintain.entities.MaintainTask;
import com.supconit.maintain.services.MaintainTaskService;

import hc.base.domains.Pageable;
import hc.bpm.context.annotations.BpmSupport;

@Service
public class MaintainTaskServiceImpl extends AbstractBaseBusinessService<MaintainTask, Long> implements MaintainTaskService{

	@Autowired
	private MaintainTaskDao maintainTaskDao;
	@Autowired
	private DeviceCategoryDao deviceCategoryDao;
	@Autowired
	private GeoAreaDao geoAreaDao;
	@Autowired
	private MaintainTaskContentDao maintainTaskContentDao;
	@Autowired
	private MaintainSpareDao maintainSpareDao;
	@Autowired
	private AttachmentDao attachmentDao;
	
	@Override
	public MaintainTask getById(Long id) {
		return maintainTaskDao.getById(id);
	}

	@Override
	public void insert(MaintainTask entity) {
		
	}

	@Override
	public void update(MaintainTask task) {
		maintainTaskDao.update(task);
	}

	@Override
	public void deleteById(Long id) {
		
	}

	@Override
	@Transactional
	@BpmSupport(businessKey = "#task.id", businessDomain = "#task", variableKeys = "{'task'}", variables = "{#task}")
	public void startProcess(MaintainTask task) {
		//taskDao.readyForTask();
	}

	@Override
	public Pageable<MaintainTask> findByCondition(Pageable<MaintainTask> pager,
			MaintainTask condition) {
		setParameter(condition);
		pager = maintainTaskDao.findByCondition(pager, condition);
		List<String> maintainCodeList = new ArrayList<String>();
		for (MaintainTask maintainTask : pager) {
			maintainCodeList.add(maintainTask.getMaintainCode());
		}
		if(maintainCodeList.size()>0){
			condition.setMaintainCodeList(maintainCodeList);
			List<MaintainTask> list = maintainTaskDao.selectMaintainTaskList(condition);
			pager.clear();
			pager.addAll(list);
		}
		return pager;
	}
	
	/**
	 * 设置(类别\区域)
	 */
	public void setParameter(MaintainTask condition){
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
	public void update(MaintainTask task, String[] fileorignal,
			String[] filename, String[] delfiles,String fileLength) {
		update(task);
		String maintainCode = task.getMaintainCode();
		//maintainTaskContentDao.deleteByMaintainCode(maintainCode);
		maintainSpareDao.deleteByMaintainCode(maintainCode);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("maintainCode", maintainCode);
		//param.put("maintainTaskContentList", task.getMaintainTaskContentList());
		//maintainTaskContentDao.batchInsert(param);
		Iterator<MaintainSpare> it = task.getMaintainSpareList().iterator();
		while(it.hasNext()){
			MaintainSpare spare = it.next();
			if(null == spare.getSpareId()){
				it.remove();
			}
		}
		if(null != task.getMaintainSpareList() && task.getMaintainSpareList().size()>0){
			param.put("maintainSpareList", task.getMaintainSpareList());
			maintainSpareDao.batchInsert(param);
		}
		attachmentDao.saveAttachements(task.getId(),Constant.ATTACHEMENT_MAINTAIN,fileorignal, filename, delfiles,fileLength);
	}

	@Override
	@Transactional
	@BpmSupport(businessKey = "#task.id", businessDomain = "#task", variableKeys = "{'task'}", variables = "{#task}")
	public void submit(MaintainTask task, String[] fileorignal,
			String[] filename, String[] delfiles,String fileLength) {
		update(task, fileorignal, filename, delfiles,fileLength);
	}

	@Override
	@Transactional
	@BpmSupport(businessKey = "#task.id", businessDomain = "#task", variableKeys = "{'task'}", variables = "{#task}")
	public void submit(MaintainTask task) {
		task.setStatus(1);//完成
		maintainTaskDao.update(task);
	}

	@Override
	public MaintainTask getBydeviceId(Long deviceId) {
		return maintainTaskDao.getBydeviceId(deviceId);
	}

}
