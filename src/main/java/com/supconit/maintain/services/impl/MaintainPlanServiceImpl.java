package com.supconit.maintain.services.impl;

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
import com.supconit.maintain.daos.MaintainPlanDao;
import com.supconit.maintain.entities.MaintainPlan;
import com.supconit.maintain.services.MaintainPlanService;

@Service
public class MaintainPlanServiceImpl extends AbstractBaseBusinessService<MaintainPlan, Long> implements MaintainPlanService {
	@Autowired
	private MaintainPlanDao maintainPlanDao;
	@Autowired
	private DeviceCategoryDao deviceCategoryDao;
	@Autowired
	private GeoAreaDao geoAreaDao;
	
	@Override
	public MaintainPlan getById(Long arg0) {
		return null;
	}

	@Override
	public void insert(MaintainPlan entity) {
		
	}

	@Override
	public void update(MaintainPlan entity) {
		
	}

	@Override
	public void deleteById(Long id) {
		
	}

	@Override
	public Pageable<MaintainPlan> findByCondition(Pageable<MaintainPlan> pager,
			MaintainPlan condition) {
		setParameter(condition);
		return maintainPlanDao.findByCondition(pager, condition);
	}
	
	/**
	 * 设置(类别\区域)
	 */
	public void setParameter(MaintainPlan condition){
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
	public void findByConditionYear(
			Pageable<MaintainPlan> pager, MaintainPlan condition) {
		setParameter(condition);
		maintainPlanDao.findByConditionYear(pager, condition);
		Iterator<MaintainPlan> it = pager.iterator();
		while(it.hasNext()){
			MaintainPlan plan = it.next();
			List<String> yearReportList = new ArrayList<String>();
			if(plan.getJan().intValue()>0){
				yearReportList.add("√");
			}else{
				yearReportList.add("");
			}
			if(plan.getFeb().intValue()>0){
				yearReportList.add("√");
			}else{
				yearReportList.add("");
			}
			if(plan.getMar().intValue()>0){
				yearReportList.add("√");
			}else{
				yearReportList.add("");
			}
			if(plan.getApr().intValue()>0){
				yearReportList.add("√");
			}else{
				yearReportList.add("");
			}
			if(plan.getMay().intValue()>0){
				yearReportList.add("√");
			}else{
				yearReportList.add("");
			}
			if(plan.getJun().intValue()>0){
				yearReportList.add("√");
			}else{
				yearReportList.add("");
			}
			if(plan.getJul().intValue()>0){
				yearReportList.add("√");
			}else{
				yearReportList.add("");
			}
			if(plan.getAug().intValue()>0){
				yearReportList.add("√");
			}else{
				yearReportList.add("");
			}
			if(plan.getSep().intValue()>0){
				yearReportList.add("√");
			}else{
				yearReportList.add("");
			}
			if(plan.getOct().intValue()>0){
				yearReportList.add("√");
			}else{
				yearReportList.add("");
			}
			if(plan.getNov().intValue()>0){
				yearReportList.add("√");
			}else{
				yearReportList.add("");
			}
			if(plan.getDec().intValue()>0){
				yearReportList.add("√");
			}else{
				yearReportList.add("");
			}
			plan.setYearReportList(yearReportList);
		}
	}

	@Override
	public void generatePlan() {
		maintainPlanDao.generatePlan();
	}
	
	
	
}
