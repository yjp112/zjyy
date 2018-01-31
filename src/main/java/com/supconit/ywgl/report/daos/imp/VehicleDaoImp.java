package com.supconit.ywgl.report.daos.imp;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.ywgl.report.daos.VehicleDao;
import com.supconit.ywgl.report.entities.Vehicle;

import hc.base.domains.Pageable;
@Repository
public class VehicleDaoImp extends AbstractBaseDao<Vehicle,Long> implements VehicleDao{

	private static final String	NAMESPACE = Vehicle.class.getName();
	@Override
	protected String getNamespace() {
		// TODO Auto-generated method stub 
		return NAMESPACE;
	}
	@Override
	public Pageable<Vehicle> findByCondition(Pageable<Vehicle> pager,
			Vehicle condition) {
		// TODO Auto-generated method stub
		return findByPager(pager, "findByConditions", "countByConditions", condition);
	}
}
