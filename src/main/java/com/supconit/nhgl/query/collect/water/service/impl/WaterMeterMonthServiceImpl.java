package com.supconit.nhgl.query.collect.water.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.supconit.base.daos.GeoAreaDao;
import com.supconit.base.entities.GeoArea;
import com.supconit.common.utils.UtilTool;
import com.supconit.nhgl.analyse.electric.dept.dao.DepartmentDao;
import com.supconit.nhgl.analyse.electric.dept.entities.Department;
import com.supconit.nhgl.query.collect.water.dao.WaterMeterMonthDao;
import com.supconit.nhgl.query.collect.water.entities.WaterMeterMonth;
import com.supconit.nhgl.query.collect.water.service.WaterMeterMonthService;

import hc.base.domains.Pageable;

@Repository
public class WaterMeterMonthServiceImpl implements WaterMeterMonthService {
	@Autowired
	private WaterMeterMonthDao	dao;
	@Autowired
	private GeoAreaDao geoAreaDao;
	@Autowired
	private DepartmentDao departmentDao;
	@Value("${water_category}")
	private String waterCatagory;

	@Override
	public WaterMeterMonth findByConc(WaterMeterMonth em) {
		if(em.getDeptId()!=null){
			em.setDeptIdlst(departmentDao.findAllChildrenIds(Long.valueOf(em.getDeptId())));
		}
		return dao.findByConc(em);
	}

	@Override
	public WaterMeterMonth findByConp(WaterMeterMonth em) {
		return dao.findByConp(em);
	}
	//获取部门的耗电量
	@Override
	public List<WaterMeterMonth> findByDept(WaterMeterMonth em) {
		if(em.getDeptId()!=null){
			em.setDeptIdlst(departmentDao.findAllChildrenIds(Long.valueOf(em.getDeptId())));
		}
		return dao.findByDept(em);
	}

	@Override
	public List<WaterMeterMonth> findByDeptAndFx(WaterMeterMonth em) {
		return dao.findByDeptAndFx(em);
	}

	@Override
	public List<WaterMeterMonth> findDept(WaterMeterMonth em) {
		return dao.findDept(em);
	}

	@Override
	public List<WaterMeterMonth> findArea(WaterMeterMonth em) {
		return dao.findArea(em);
	}

	@Override
	public List<WaterMeterMonth> getMonthWater(WaterMeterMonth em) {
		return dao.getMonthWater(em);
	}

	@Override
	public List<WaterMeterMonth> findMaxTime(WaterMeterMonth em) {
		return dao.findMaxTime(em);
	}

	@Override
	public int save(List<WaterMeterMonth> em) {
		return dao.save(em);
	}

	@Override
	public List<WaterMeterMonth> findByArea(WaterMeterMonth em) {
		return dao.findByArea(em);
	}

	@Override
	public Pageable<WaterMeterMonth> findByCondition(
			Pageable<WaterMeterMonth> pager, WaterMeterMonth condition) {
		setParameter(condition);
		return dao.findByCondition(pager, condition);
	}

	@Override
	public Pageable<WaterMeterMonth> findByDeptCondition(
			Pageable<WaterMeterMonth> pager, WaterMeterMonth condition) {
		setParameter(condition);
		return dao.findByDeptCondition(pager, condition);
	}
	public void setParameter(WaterMeterMonth condition){
		if(condition.getDpid()!=null){
			List<Department> listDepartment=departmentDao.findById(condition.getDpid());
			List<Long> lstDpId=new ArrayList<Long>();
			if(!UtilTool.isEmptyList(listDepartment)){
				for(Department d:listDepartment){
					lstDpId.add(d.getId());
				}
			}
			condition.setLstDpId(lstDpId);
		}
		if(condition.getLocationId()!=null){
            List<GeoArea> listGeoAreas = geoAreaDao.findById(condition.getLocationId());
            List<Long> lstLocationId = new ArrayList<Long>();
            if(!UtilTool.isEmptyList(listGeoAreas)){
            	for(GeoArea g:listGeoAreas){
            		lstLocationId.add(g.getId());
            	}
            }
            condition.setLstLocationId(lstLocationId);
          }	
	}

	@Override
	public List<WaterMeterMonth> getParentAreaMonthWater(
			WaterMeterMonth em) {
		em.setCategoryCode(waterCatagory);
		return dao.getParentAreaMonthWater(em);
	}

	@Override
	public List<WaterMeterMonth> getChildrenAreaMonthWater(
			WaterMeterMonth em) {
		em.setCategoryCode(waterCatagory);
		return dao.getChildrenAreaMonthWater(em);
	}

	@Override
	public List<WaterMeterMonth> getChildrenDeptMonthWater(
			WaterMeterMonth em) {
		return dao.getChildrenDeptMonthWater(em);
	}

	@Override
	public List<WaterMeterMonth> getSubWaterMonthChildren(WaterMeterMonth emm) {
		return dao.getSubWaterMonthChildren(emm);
	}

	@Override
	public List<WaterMeterMonth> getSubWaterMonthParent(WaterMeterMonth emm) {
		return dao.getSubWaterMonthParent(emm);
	}

	@Override
	public List<WaterMeterMonth> getMonthTotal(WaterMeterMonth emm) {
		return dao.getMonthTotal(emm);
	}

	@Override
	public List<WaterMeterMonth> getParentDeptMonthWater(WaterMeterMonth em) {
		return dao.getParentDeptMonthWater(em);
	}

	@Override
	public List<WaterMeterMonth> getChildDeptMonthWater(WaterMeterMonth em) {
		return dao.getChildDeptMonthWater(em);
	}

}
