package com.supconit.nhgl.query.collect.service.impl;

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
import com.supconit.nhgl.query.collect.dao.ElectricMeterMonthDao;
import com.supconit.nhgl.query.collect.entities.ElectricMeterMonth;
import com.supconit.nhgl.query.collect.service.ElectricMeterMonthService;

import hc.base.domains.Pageable;

@Repository
public class ElectricMeterMonthServiceImpl implements ElectricMeterMonthService {
	@Autowired
	private ElectricMeterMonthDao	dao;
	@Autowired
	private GeoAreaDao geoAreaDao;
	@Autowired
	private DepartmentDao departmentDao;
	@Value("${electric_category}")
	private String electricCatagory;
	@Override
	public ElectricMeterMonth findByConc(ElectricMeterMonth em) {
		if(em.getDeptId()!=null){
			em.setDeptIdlst(departmentDao.findAllChildrenIds(Long.valueOf(em.getDeptId())));
		}
		return dao.findByConc(em);
	}

	@Override
	public ElectricMeterMonth findByConp(ElectricMeterMonth em) {
		return dao.findByConp(em);
	}
	//获取部门的耗电量
	@Override
	public List<ElectricMeterMonth> findByDept(ElectricMeterMonth em) {
		if(em.getDeptId()!=null){
			em.setDeptIdlst(departmentDao.findAllChildrenIds(Long.valueOf(em.getDeptId())));
		}
		return dao.findByDept(em);
	}

	@Override
	public List<ElectricMeterMonth> findByDeptAndFx(ElectricMeterMonth em) {
		return dao.findByDeptAndFx(em);
	}

	@Override
	public List<ElectricMeterMonth> findDept(ElectricMeterMonth em) {
		return dao.findDept(em);
	}

	@Override
	public List<ElectricMeterMonth> findArea(ElectricMeterMonth em) {
		return dao.findArea(em);
	}

	@Override
	public List<ElectricMeterMonth> getMonthElectric(ElectricMeterMonth em) {
		return dao.getMonthElectric(em);
	}

	@Override
	public List<ElectricMeterMonth> findMaxTime(ElectricMeterMonth em) {
		return dao.findMaxTime(em);
	}

	@Override
	public int save(List<ElectricMeterMonth> em) {
		return dao.save(em);
	}

	@Override
	public List<ElectricMeterMonth> findByArea(ElectricMeterMonth em) {
		return dao.findByArea(em);
	}

	@Override
	public Pageable<ElectricMeterMonth> findByCondition(
			Pageable<ElectricMeterMonth> pager, ElectricMeterMonth condition) {
		setParameter(condition);
		return dao.findByCondition(pager, condition);
	}

	@Override
	public Pageable<ElectricMeterMonth> findByDeptCondition(
			Pageable<ElectricMeterMonth> pager, ElectricMeterMonth condition) {
		setParameter(condition);
		return dao.findByDeptCondition(pager, condition);
	}
	public void setParameter(ElectricMeterMonth condition){
		if(condition.getDpid()!=null){
			List<Department> listDepartment=departmentDao.findAllChildren(condition.getDpid());
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
	public List<ElectricMeterMonth> getParentAreaMonthElectricty(
			ElectricMeterMonth em) {
		em.setCategoryCode(electricCatagory);
		return dao.getParentAreaMonthElectricty(em);
	}

	@Override
	public List<ElectricMeterMonth> getChildrenAreaMonthElectricty(
			ElectricMeterMonth em) {
		em.setCategoryCode(electricCatagory);
		return dao.getChildrenAreaMonthElectricty(em);
	}

	@Override
	public List<ElectricMeterMonth> getChildrenDeptMonthElectricty(
			ElectricMeterMonth em) {
		return dao.getChildrenDeptMonthElectricty(em);
	}

	@Override
	public List<ElectricMeterMonth> getMonthTotal(ElectricMeterMonth em) {
		return dao.getMonthTotal(em);
	}

	@Override
	public List<ElectricMeterMonth> getParentDeptMonthElectricty(
			ElectricMeterMonth em) {
		return dao.getParentDeptMonthElectricty(em);
	}

	@Override
	public List<ElectricMeterMonth> getChildDeptMonthElectricty(
			ElectricMeterMonth em) {
		return dao.getChildDeptMonthElectricty(em);
	}



}
