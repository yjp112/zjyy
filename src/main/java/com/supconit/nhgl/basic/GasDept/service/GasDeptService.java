package com.supconit.nhgl.basic.GasDept.service;

import java.util.List;

import com.supconit.nhgl.basic.GasDept.entities.GasDept;

import hc.base.domains.Pageable;
import hc.orm.BasicOrmService;

public interface GasDeptService extends BasicOrmService<GasDept,Long>{
	Pageable<GasDept> findByCondition(Pageable<GasDept> pager,GasDept condition);
	
	List<GasDept> findDmByCondition(GasDept dm);
}
