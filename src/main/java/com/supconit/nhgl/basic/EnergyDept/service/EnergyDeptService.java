package com.supconit.nhgl.basic.EnergyDept.service;

import java.util.List;

import com.supconit.nhgl.basic.EnergyDept.entities.EnergyDept;

import hc.base.domains.Pageable;
import hc.orm.BasicOrmService;

public interface EnergyDeptService extends BasicOrmService<EnergyDept,Long>{
	Pageable<EnergyDept> findByCondition(Pageable<EnergyDept> pager,EnergyDept condition);
	
	List<EnergyDept> findDmByCondition(EnergyDept dm);
}
