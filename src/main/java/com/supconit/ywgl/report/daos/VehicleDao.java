package com.supconit.ywgl.report.daos;

import com.supconit.common.daos.BaseDao;
import com.supconit.ywgl.report.entities.Vehicle;

import hc.base.domains.Pageable;


public interface VehicleDao extends BaseDao<Vehicle, Long>{
	
	Pageable<Vehicle> findByCondition(Pageable<Vehicle> pager, Vehicle condition);

}

