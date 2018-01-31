package com.supconit.inspection.services.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import hc.base.domains.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.base.daos.DeviceCategoryDao;
import com.supconit.base.daos.GeoAreaDao;
import com.supconit.base.entities.GeoArea;
import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.inspection.daos.InspectionPlanDao;
import com.supconit.inspection.entities.InspectionPlan;
import com.supconit.inspection.services.InspectionPlanService;

@Service
public class InspectionPlanServiceImpl extends AbstractBaseBusinessService<InspectionPlan, Long> implements InspectionPlanService {
	@Autowired
	private InspectionPlanDao inspectionPlanDao;
	@Autowired
	private DeviceCategoryDao deviceCategoryDao;
	@Autowired
	private GeoAreaDao geoAreaDao;
	
	
	@Override
	public InspectionPlan getById(Long arg0) {
		return null;
	}

	@Override
	public void insert(InspectionPlan entity) {
		
	}

	@Override
	public void update(InspectionPlan entity) {
		
	}

	@Override
	public void deleteById(Long id) {
		
	}

	@Override
	public Pageable<InspectionPlan> findByCondition(Pageable<InspectionPlan> pager,
			InspectionPlan condition) {
		setParameter(condition);
		return inspectionPlanDao.findByCondition(pager, condition);
	}
	
	/**
	 * 设置(类别\区域)
	 */
	public void setParameter(InspectionPlan condition){
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
	public void findByConditionMonth(
			Pageable<InspectionPlan> pager, InspectionPlan condition) {
		setParameter(condition);
		inspectionPlanDao.findByConditionMonth(pager, condition);
		Iterator<InspectionPlan> it = pager.iterator();
		while(it.hasNext()){
			InspectionPlan plan = it.next();
			List<String> monthReportList = new ArrayList<String>();
			if(plan.getFirstWeek().intValue()>0){
				monthReportList.add("√");
			}else{
				monthReportList.add("");
			}
			if(plan.getSecondWeek().intValue()>0){
				monthReportList.add("√");
			}else{
				monthReportList.add("");
			}
			if(plan.getThirdWeek().intValue()>0){
				monthReportList.add("√");
			}else{
				monthReportList.add("");
			}
			if(plan.getFourWeek().intValue()>0){
				monthReportList.add("√");
			}else{
				monthReportList.add("");
			}
			plan.setMonthReportList(monthReportList);
		}
	}

	@Override
	public void generatePlan() {
		inspectionPlanDao.generatePlan();
	}
	
	
	
}
