package com.supconit.emergency.services.impl;

import java.util.ArrayList;
import java.util.List;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.base.daos.GeoAreaDao;
import com.supconit.common.exceptions.BusinessDoneException;
import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.emergency.daos.EmergencyFacilityDao;
import com.supconit.emergency.entities.EmergencyFacility;
import com.supconit.emergency.services.EmergencyFacilityService;
@Service
public class EmergencyFacilityServiceImpl extends AbstractBaseBusinessService<EmergencyFacility, Long> implements EmergencyFacilityService{
	@Autowired
	private EmergencyFacilityDao		emergencyFacilityDao;
	@Autowired
	private GeoAreaDao geoAreaDao;
	@Override
	@Transactional
	public EmergencyFacility getById(Long id) {
		return emergencyFacilityDao.getById(id);
	}

	@Override
	@Transactional
	public void insert(EmergencyFacility entity) {
		checkToSave(entity);
		emergencyFacilityDao.insert(entity);
	}

	@Override
	@Transactional
	public void update(EmergencyFacility entity) {
		checkToSave(entity);
		emergencyFacilityDao.update(entity);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		emergencyFacilityDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Pageable<EmergencyFacility> findByCondition(
			Pagination<EmergencyFacility> pager,
			EmergencyFacility emergencyFacility) {
		if(emergencyFacility.getAreaId()!=null){
	          List<Long> ids= geoAreaDao.findChildIds(emergencyFacility.getAreaId());
	          emergencyFacility.setAreaId(null);
	          emergencyFacility.setAreaIds(ids);
	        }
		return emergencyFacilityDao.findByCondition(pager,emergencyFacility);
	}

	@Override
	public void deleteByIds(Long[] ids) {
		emergencyFacilityDao.deleteByIds(ids);
	}
	//Check that allows you to save
	  private void checkToSave(EmergencyFacility emergencyFacility){
			List<EmergencyFacility> list = new ArrayList<EmergencyFacility>();
			list = emergencyFacilityDao.findByFacilityCode(emergencyFacility.getFacilityCode());
			if (null != list && list.size() >= 1) {
				if (list.size() > 1) {
					throw new BusinessDoneException("设施编号[" + emergencyFacility.getFacilityCode()+ "]已经被占用。");
				} else {
					// list.size()==1
					if (emergencyFacility.getId() != null) {
						// update
						EmergencyFacility old = list.get(0);
						if (emergencyFacility.getId().longValue() == old.getId().longValue()) {
							// ok
						} else {
							throw new BusinessDoneException("设施编号["+ emergencyFacility.getFacilityCode() + "]已经被占用。");
						}
					} else {
						// insert
						throw new BusinessDoneException("设施编号["+ emergencyFacility.getFacilityCode() + "]已经被占用。");
					}

				}
			}
		  
		  
	  }
}
