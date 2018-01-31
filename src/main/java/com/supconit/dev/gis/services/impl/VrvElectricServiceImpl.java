package com.supconit.dev.gis.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.dev.gis.daos.VrvElectricDao;
import com.supconit.dev.gis.entities.VrvElectric;
import com.supconit.dev.gis.services.VrvElectricService;
import com.supconit.hl.common.services.impl.AbstractBaseBusinessService;

import hc.base.domains.Pageable;

@Service
public class VrvElectricServiceImpl extends
        AbstractBaseBusinessService<VrvElectric, Long> implements VrvElectricService {
	
	@Autowired
	private VrvElectricDao vrvElectricDao;


	@Override
	public Pageable<VrvElectric> findByCondition(Pageable<VrvElectric> pager,
			VrvElectric condition) {
		return vrvElectricDao.findByCondition(pager, condition);
	}
	
	@Override
	public VrvElectric getById(Long id) {
		return null;
	}

	@Override
	public void insert(VrvElectric vrvElectric){

	}

	@Override
	public void update(VrvElectric vrvElectric){
	
	}
	
	@Override
	public void deleteById(Long id) {
		
	}
}
