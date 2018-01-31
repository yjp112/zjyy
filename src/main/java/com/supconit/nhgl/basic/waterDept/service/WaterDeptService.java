package com.supconit.nhgl.basic.waterDept.service;

import java.util.List;

import com.supconit.nhgl.basic.waterDept.entities.WaterDept;

import hc.base.domains.Pageable;
import hc.orm.BasicOrmService;

public interface WaterDeptService extends BasicOrmService<WaterDept,Long>{
	Pageable<WaterDept> findByCondition(Pageable<WaterDept> pager,WaterDept condition);
	
	List<WaterDept> findDmByCondition(WaterDept dm);
}
