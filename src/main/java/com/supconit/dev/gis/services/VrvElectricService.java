package com.supconit.dev.gis.services;

import com.supconit.dev.gis.entities.VrvElectric;

import hc.base.domains.Pageable;
import hc.orm.BasicOrmService;

public interface VrvElectricService extends BasicOrmService<VrvElectric, Long> {

	Pageable<VrvElectric> findByCondition(Pageable<VrvElectric> pager, VrvElectric condition) throws Exception;
	
}
