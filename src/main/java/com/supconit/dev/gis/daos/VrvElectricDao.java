package com.supconit.dev.gis.daos;

import com.supconit.dev.gis.entities.VrvElectric;

import hc.base.domains.Pageable;
import hc.orm.BasicDao;

public interface VrvElectricDao extends BasicDao<VrvElectric,Long>{
	
	Pageable<VrvElectric> findByCondition(Pageable<VrvElectric> page, VrvElectric condition);

}
