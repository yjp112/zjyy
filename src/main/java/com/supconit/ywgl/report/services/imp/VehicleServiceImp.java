package com.supconit.ywgl.report.services.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.ywgl.report.daos.VehicleDao;
import com.supconit.ywgl.report.entities.Vehicle;
import com.supconit.ywgl.report.services.VehicleService;

import hc.base.domains.Pageable;

@Service
public class VehicleServiceImp extends AbstractBaseBusinessService<Vehicle, Long> implements VehicleService{
	@Autowired
	private VehicleDao		vehicleDao;	
	
	@Override
	public Vehicle getById(Long arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(Vehicle entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Vehicle entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Pageable<Vehicle> findByCondition(Pageable<Vehicle> pager,
			Vehicle condition) {
		// TODO Auto-generated method stub
		return vehicleDao.findByCondition(pager, condition); 
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}
	
	
}
