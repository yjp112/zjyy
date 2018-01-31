package com.supconit.nhgl.query.collect.gas.service.impl;

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
import com.supconit.nhgl.query.collect.gas.dao.GasMeterMonthDao;
import com.supconit.nhgl.query.collect.gas.entities.GasMeterMonth;
import com.supconit.nhgl.query.collect.gas.service.GasMeterMonthService;

import hc.base.domains.Pageable;

@Service
public class GasMeterMonthServiceImpl implements GasMeterMonthService{

	@Autowired
	private GasMeterMonthDao GasMeterMonthDao;
	@Autowired
	private GeoAreaDao geoAreaDao;
	@Autowired
	private DepartmentDao departmentDao;
	@Value("${gas_category}")
	private String gasCatagory;
	@Override
	public GasMeterMonth findByConc(GasMeterMonth em) {
		if(em.getDeptId()!=null){
			em.setDeptIdlst(departmentDao.findAllChildrenIds(Long.valueOf(em.getDeptId())));
		}
		return GasMeterMonthDao.findByConc(em);
	}

	@Override
	public GasMeterMonth findByConp(GasMeterMonth em) {
		return GasMeterMonthDao.findByConp(em);
	}


	@Override
	public List<GasMeterMonth> findByArea(GasMeterMonth em) {
		return GasMeterMonthDao.findByArea(em);
	}

	@Override
	public List<GasMeterMonth> findByDeptAndFx(GasMeterMonth em) {
		return GasMeterMonthDao.findByDeptAndFx(em);
	}


	@Override
	public List<GasMeterMonth> findArea(GasMeterMonth em) {
		return GasMeterMonthDao.findArea(em);
	}

	@Override
	public List<GasMeterMonth> findMaxTime(GasMeterMonth em) {
		return GasMeterMonthDao.findMaxTime(em);
	}

	@Override
	public int save(List<GasMeterMonth> em) {
		return GasMeterMonthDao.save(em);
	}

	@Override
	public List<GasMeterMonth> getMonthGas(GasMeterMonth em) {
		return GasMeterMonthDao.getMonthGas(em);
	}
	public void setParameter(GasMeterMonth condition){
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
	public Pageable<GasMeterMonth> findByCondition(
			Pageable<GasMeterMonth> pager, GasMeterMonth condition) {
		setParameter(condition);
		return GasMeterMonthDao.findByCondition(pager, condition);
	}

	@Override
	public Pageable<GasMeterMonth> findByDeptCondition(
			Pageable<GasMeterMonth> pager, GasMeterMonth condition) {
		setParameter(condition);
		return GasMeterMonthDao.findByDeptCondition(pager, condition);
	}

	@Override
	public List<GasMeterMonth> getParentAreaMonthGas(
			GasMeterMonth em) {
		em.setCategoryCode(gasCatagory);
		return GasMeterMonthDao.getParentAreaMonthGas(em);
	}

	@Override
	public List<GasMeterMonth> getChildrenAreaMonthGas(
			GasMeterMonth em) {
		em.setCategoryCode(gasCatagory);
		return GasMeterMonthDao.getChildrenAreaMonthGas(em);
	}

	@Override
	public List<GasMeterMonth> getChildrenDeptMonthGas(
			GasMeterMonth em) {
		return GasMeterMonthDao.getChildrenDeptMonthGas(em);
	}

	@Override
	public List<GasMeterMonth> findByDept(GasMeterMonth em) {
		if(em.getDeptId()!=null){
			em.setDeptIdlst(departmentDao.findAllChildrenIds(Long.valueOf(em.getDeptId())));
		}
		return GasMeterMonthDao.findByDept(em);
	}

	@Override
	public List<GasMeterMonth> findDept(GasMeterMonth em) {
		return GasMeterMonthDao.findDept(em);
	}

	@Override
	public List<GasMeterMonth> getParentDeptMonthGas(GasMeterMonth em) {
		return GasMeterMonthDao.getParentDeptMonthGas(em);
	}

	@Override
	public List<GasMeterMonth> getChildDeptMonthGas(GasMeterMonth em) {
		return GasMeterMonthDao.getChildDeptMonthGas(em);
	}

}
