package com.supconit.nhgl.query.collect.energy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.supconit.base.daos.GeoAreaDao;
import com.supconit.base.entities.GeoArea;
import com.supconit.common.utils.UtilTool;
import com.supconit.nhgl.analyse.electric.dept.dao.DepartmentDao;
import com.supconit.nhgl.analyse.electric.dept.entities.Department;
import com.supconit.nhgl.query.collect.energy.dao.EnergyMeterMonthDao;
import com.supconit.nhgl.query.collect.energy.entities.EnergyMeterMonth;
import com.supconit.nhgl.query.collect.energy.service.EnergyMeterMonthService;

import hc.base.domains.Pageable;

@Service
public class EnergyMeterMonthServiceImpl implements EnergyMeterMonthService{

	@Autowired
	private EnergyMeterMonthDao energyMeterMonthDao;
	@Autowired
	private GeoAreaDao geoAreaDao;
	@Autowired
	private DepartmentDao departmentDao;
	@Override
	public EnergyMeterMonth findByConc(EnergyMeterMonth em) {
		if(em.getDeptId()!=null){
			em.setDeptIdlst(departmentDao.findAllChildrenIds(Long.valueOf(em.getDeptId())));
		}
		return energyMeterMonthDao.findByConc(em);
	}

	@Override
	public EnergyMeterMonth findByConp(EnergyMeterMonth em) {
		return energyMeterMonthDao.findByConp(em);
	}


	@Override
	public List<EnergyMeterMonth> findByArea(EnergyMeterMonth em) {
		return energyMeterMonthDao.findByArea(em);
	}

	@Override
	public List<EnergyMeterMonth> findByDeptAndFx(EnergyMeterMonth em) {
		return energyMeterMonthDao.findByDeptAndFx(em);
	}


	@Override
	public List<EnergyMeterMonth> findArea(EnergyMeterMonth em) {
		return energyMeterMonthDao.findArea(em);
	}

	@Override
	public List<EnergyMeterMonth> findMaxTime(EnergyMeterMonth em) {
		return energyMeterMonthDao.findMaxTime(em);
	}

	@Override
	public int save(List<EnergyMeterMonth> em) {
		return energyMeterMonthDao.save(em);
	}

	@Override
	public List<EnergyMeterMonth> getMonthEnergy(EnergyMeterMonth em) {
		return energyMeterMonthDao.getMonthEnergy(em);
	}
	public void setParameter(EnergyMeterMonth condition){
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
	public Pageable<EnergyMeterMonth> findByCondition(
			Pageable<EnergyMeterMonth> pager, EnergyMeterMonth condition) {
		setParameter(condition);
		return energyMeterMonthDao.findByCondition(pager, condition);
	}

	@Override
	public Pageable<EnergyMeterMonth> findByDeptCondition(
			Pageable<EnergyMeterMonth> pager, EnergyMeterMonth condition) {
		setParameter(condition);
		return energyMeterMonthDao.findByDeptCondition(pager, condition);
	}

	@Override
	public List<EnergyMeterMonth> getParentAreaMonthEnergy(
			EnergyMeterMonth em) {
		return energyMeterMonthDao.getParentAreaMonthEnergy(em);
	}

	@Override
	public List<EnergyMeterMonth> getChildrenAreaMonthEnergy(
			EnergyMeterMonth em) {
		return energyMeterMonthDao.getChildrenAreaMonthEnergy(em);
	}

	@Override
	public List<EnergyMeterMonth> getChildrenDeptMonthEnergy(
			EnergyMeterMonth em) {
		return energyMeterMonthDao.getChildrenDeptMonthEnergy(em);
	}

	@Override
	public List<EnergyMeterMonth> findDept(EnergyMeterMonth em) {
		return energyMeterMonthDao.findDept(em);
	}

	@Override
	public List<EnergyMeterMonth> findByDept(EnergyMeterMonth em) {
		if(em.getDeptId()!=null){
			em.setDeptIdlst(departmentDao.findAllChildrenIds(Long.valueOf(em.getDeptId())));
		}
		return energyMeterMonthDao.findByDept(em);
	}

	@Override
	public List<EnergyMeterMonth> getParentDeptMonthEnergy(EnergyMeterMonth em) {
		return energyMeterMonthDao.getParentDeptMonthEnergy(em);
	}

	@Override
	public List<EnergyMeterMonth> getChildDeptMonthEnergy(EnergyMeterMonth em) {
		return energyMeterMonthDao.getChildDeptMonthEnergy(em);
	}

}
