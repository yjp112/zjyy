package com.supconit.inspection.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.base.daos.AttachmentDao;
import com.supconit.base.daos.DeviceCategoryDao;
import com.supconit.base.daos.GeoAreaDao;
import com.supconit.base.entities.GeoArea;
import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.common.utils.Constant;
import com.supconit.inspection.daos.InspectionSpareDao;
import com.supconit.inspection.daos.InspectionTaskContentDao;
import com.supconit.inspection.daos.InspectionTaskDao;
import com.supconit.inspection.entities.InspectionSpare;
import com.supconit.inspection.entities.InspectionTask;
import com.supconit.inspection.services.InspectionTaskService;
import com.supconit.inspection.daos.InspectionSpareDao;

import hc.base.domains.Pageable;
import hc.bpm.context.annotations.BpmSupport;

@Service
public class InspectionTaskServiceImpl extends AbstractBaseBusinessService<InspectionTask, Long> implements InspectionTaskService{

	@Resource
	private InspectionTaskDao inspectionTaskDao;
	@Autowired
	private DeviceCategoryDao deviceCategoryDao;
	@Autowired
	private GeoAreaDao geoAreaDao;
	@Autowired
	private InspectionTaskContentDao inspectionTaskContentDao;
	@Autowired
	private InspectionSpareDao inspectionSpareDao;
	@Autowired
	private AttachmentDao attachmentDao;
	
	@Override
	public InspectionTask getById(Long id) {
		return inspectionTaskDao.getById(id);
	}

	@Override
	public void insert(InspectionTask entity) {
		
	}

	@Override
	public void update(InspectionTask task) {
		inspectionTaskDao.update(task);
	}

	@Override
	public void deleteById(Long id) {
		
	}

	@Override
	@Transactional
	@BpmSupport(businessKey = "#task.id", businessDomain = "#task", variableKeys = "{'task'}", variables = "{#task}")
	public void startProcess(InspectionTask task) {
		
	}

	@Override
	public Pageable<InspectionTask> findByCondition(Pageable<InspectionTask> pager,
			InspectionTask condition) {
		setParameter(condition);
		pager = inspectionTaskDao.findByCondition(pager, condition);
		List<String> inspectionCodeList = new ArrayList<String>();
		for (InspectionTask inspectionTask : pager) {
			inspectionCodeList.add(inspectionTask.getInspectionCode());
		}
		if(inspectionCodeList.size()>0){
			condition.setInspectionCodeList(inspectionCodeList);
			List<InspectionTask> list = inspectionTaskDao.selectInspectionTaskList(condition);
			pager.clear();
			pager.addAll(list);
		}
		return pager;
	}
	
	/**
	 * 设置(类别\区域)
	 */
	public void setParameter(InspectionTask condition){
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
	public void update(InspectionTask task, String[] fileorignal,
			String[] filename, String[] delfiles,String fileLength) {
		update(task);
		String inspectionCode = task.getInspectionCode();
		//inspectionTaskContentDao.deleteByInspectionCode(inspectionCode);
		inspectionSpareDao.deleteByInspectionCode(inspectionCode);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("inspectionCode", inspectionCode);
		//param.put("inspectionTaskContentList", task.getInspectionTaskContentList());
		//inspectionTaskContentDao.batchInsert(param);
		Iterator<InspectionSpare> it = task.getInspectionSpareList().iterator();
		while(it.hasNext()){
			InspectionSpare spare = it.next();
			if(null == spare.getSpareId()){
				it.remove();
			}
		}
		if(null != task.getInspectionSpareList() && task.getInspectionSpareList().size()>0){
			param.put("inspectionSpareList", task.getInspectionSpareList());
			inspectionSpareDao.batchInsert(param);
		}
		attachmentDao.saveAttachements(task.getId(),Constant.ATTACHEMENT_INSPECTION,fileorignal, filename, delfiles,fileLength);
	}

	@Override
	@Transactional
	@BpmSupport(businessKey = "#task.id", businessDomain = "#task", variableKeys = "{'task'}", variables = "{#task}")
	public void submit(InspectionTask task, String[] fileorignal,
			String[] filename, String[] delfiles,String fileLength) {
		update(task, fileorignal, filename, delfiles,fileLength);
	}

	@Override
	@Transactional
	@BpmSupport(businessKey = "#task.id", businessDomain = "#task", variableKeys = "{'task'}", variables = "{#task}")
	public void submit(InspectionTask task) {
		task.setStatus(1);//完成
		inspectionTaskDao.update(task);
	}

	@Override
	public InspectionTask getBydeviceId(Long deviceId) {
		return inspectionTaskDao.getBydeviceId(deviceId);
	}

}
