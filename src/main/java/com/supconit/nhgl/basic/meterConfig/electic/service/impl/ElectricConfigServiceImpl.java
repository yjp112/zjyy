package com.supconit.nhgl.basic.meterConfig.electic.service.impl;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;
import hc.orm.AbstractBasicOrmService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.base.daos.GeoAreaDao;
import com.supconit.base.entities.GeoArea;
import com.supconit.common.utils.UtilTool;
import com.supconit.nhgl.analyse.electric.dept.dao.DepartmentDao;
import com.supconit.nhgl.analyse.electric.dept.entities.Department;
import com.supconit.nhgl.basic.meterConfig.electic.dao.ElectricConfigDao;
import com.supconit.nhgl.basic.meterConfig.electic.entities.ElectricConfig;
import com.supconit.nhgl.basic.meterConfig.electic.service.ElectricConfigService;

@Service
public class ElectricConfigServiceImpl extends AbstractBasicOrmService<ElectricConfig,Long> implements ElectricConfigService{

	@Autowired
	ElectricConfigDao deviceDao;
	@Autowired
	GeoAreaDao geoAreaDao;
	@Autowired
	DepartmentDao departmentDao;
	@Value("${electric_category}")
	private String electricCatagoryCode;
	

	@Override
	public Pageable<ElectricConfig> findByConditon(Pagination<ElectricConfig> pager,
			ElectricConfig condition) {
		setParameter(condition);
		condition.setCategoryCode(electricCatagoryCode);//水表的设备类别编码
		return deviceDao.findByCondition(pager, condition);
	}

	@Override
	public List<ElectricConfig> findById(Long id) {
		return deviceDao.findById(id);
	}
	public void setParameter(ElectricConfig condition){
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
        if(condition.getUseDepartmentId()!=null){
        	List<Department> listDepartments = departmentDao.findAllChildren(condition.getUseDepartmentId());
        	List<Long> lstDepartmentId = new ArrayList<Long>();
        	if(!UtilTool.isEmptyList(listDepartments)){
        		for(Department g:listDepartments){
        			lstDepartmentId.add(g.getId());
        		}
        	}
        	condition.setLstLocationId(lstDepartmentId);
        }	
	}
	@Override
	public ElectricConfig getById(Long arg0) {
		return deviceDao.getById(arg0);
	}

	@Override
	public void insert(ElectricConfig entity) {
		deviceDao.insert(entity);
	}

	@Override
	public void update(ElectricConfig entity) {
		deviceDao.update(entity);
	}

	@Override
	public void delete(ElectricConfig entity) {
		deviceDao.delete(entity);
	}
	@Override
	public Pageable<ElectricConfig> findByConditonPower(Pagination<ElectricConfig> pager,
			ElectricConfig condition) {
		setParameter(condition);
		condition.setBarcode(electricCatagoryCode);//表的设备类别编码
		return deviceDao.findByConditionPower(pager, condition);
	}
	@Override
	@Transactional
	public void update(List<ElectricConfig> lst) {
		if(!UtilTool.isEmptyList(lst)){
			for (ElectricConfig d:lst) {
				deviceDao.update(d);
			}   
		}
	}
}
